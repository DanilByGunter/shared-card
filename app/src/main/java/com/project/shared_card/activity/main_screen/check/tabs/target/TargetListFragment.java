package com.project.shared_card.activity.main_screen.check.tabs.target;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DateConverter;
import com.project.shared_card.activity.converter.ModelConverter;
import com.project.shared_card.activity.main_screen.check.CheckFragment;
import com.project.shared_card.activity.main_screen.check.dialog.AdapterForSpinner;
import com.project.shared_card.activity.main_screen.check.dialog.DialogAddProductToHistory;
import com.project.shared_card.activity.main_screen.check.tabs.target.model.Target;
import com.project.shared_card.activity.main_screen.PopupMenu;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.check.target.FullTarget;
import com.project.shared_card.database.entity.check.target.TargetEntity;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class TargetListFragment extends Fragment {
    Button buttonSort;
    EditText searchBar;
    RecyclerView list;
    PopupMenu popupMenu;
    TargetAdapter adapter;
    ImplDB db;
    SharedPreferences settings;
    String idGroup;
    SwipeRefreshLayout swipe;
    ItemTouchHelper itemTouchHelper;
    DialogAddProductToHistory dialog;
    AdapterForSpinner adapterForSpinner;
    Target targetRight;
    int positionRight;

    public TargetListFragment() {

    }


    public static TargetListFragment newInstance() {
        TargetListFragment fragment = new TargetListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public boolean clickOnPopupMenu(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.by_product:
                adapter.sorted(1);
                return true;
            case R.id.by_date:
                adapter.sorted(2);
                return true;
            case R.id.by_category:
                adapter.sorted(3);
                return true;
            case R.id.by_user:
                adapter.sorted(4);
                return true;
            default:
                return false;
        }
    }

    void clickOnDialogReady(View v) {
        if (dialog.price.getText().toString().equals(""))
            return;
        TargetEntity target = dialog.targetEntity;
        target.setStatus(2);
        target.setDateLast(DateConverter.FromNowDateToLong());
        target.setPrice(Integer.parseInt(dialog.price.getText().toString()));
        target.setShopId(dialog.shop.getSelectedItemPosition() + 1);
        target.setCurrencyId(dialog.currency.getSelectedItemPosition() + 1);
        if (!settings.getString(getString(R.string.key_for_me_id_server), "no_id").equals("no_id")) {
            target.setUserNameBuyerId(Long.parseLong(settings.getString(getString(R.string.key_for_me_id_server), "no_id")));
        } else {
            target.setUserNameBuyerId(Long.parseLong(getString(R.string.me_id)));
        }
        db.target().update(target);
        dialog.price.setText("");
        dialog.dialog.dismiss();
    }

    void clickOnOpenSort(View v) {
        popupMenu.openPopupMenu();
    }

    void init(View v) {
        settings = getContext().getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        idGroup = settings.getString(getString(R.string.key_for_select_group_id), "no_id");
        searchBar = v.findViewById(R.id.input_line);
        list = v.findViewById(R.id.list_target);
        buttonSort = v.findViewById(R.id.button_sort);
        swipe = v.findViewById(R.id.swipe_target);
        popupMenu = new PopupMenu(getContext(), buttonSort);
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        db = new ImplDB(getContext());
        dialog = new DialogAddProductToHistory(getContext());
        db.currency().getAll().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                adapterForSpinner = new AdapterForSpinner(getContext(), strings);
                dialog.currency.setAdapter(adapterForSpinner);
            }
        });
        db.shop_target().getAll().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                adapterForSpinner = new AdapterForSpinner(getContext(), strings);
                dialog.shop.setAdapter(adapterForSpinner);
            }
        });
    }

    void getCheck() {
        db.target().getAllForCheck(Long.valueOf(idGroup)).observe(getViewLifecycleOwner(), new Observer<List<FullTarget>>() {
            @Override
            public void onChanged(List<FullTarget> fullTargets) {
                adapter.update(ModelConverter.FromTargetEntityToTargetModel(fullTargets));
                swipe.setRefreshing(false);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_target_list, container, false);
        init(view);
        swipe.setOnRefreshListener(this::getCheck);
        swipe.setColorSchemeResources(R.color.dark_green);
        buttonSort.setOnClickListener(this::clickOnOpenSort);
        db.target().getAllForCheck(Long.valueOf(idGroup)).observe(getViewLifecycleOwner(), new Observer<List<FullTarget>>() {
            @Override
            public void onChanged(List<FullTarget> fullTargets) {
                List<Target> targets = ModelConverter.FromTargetEntityToTargetModel(fullTargets);
                adapter = new TargetAdapter(getContext(), targets);
                list.setAdapter(adapter);
            }
        });
        itemTouchHelper.attachToRecyclerView(list);
        dialog.product.setSelected(true);
        dialog.ready.setOnClickListener(this::clickOnDialogReady);
        dialog.dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                adapter.checks.add(positionRight, targetRight);
                adapter.notifyDataSetChanged();
            }
        });
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((TargetAdapter) list.getAdapter()).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        popupMenu.popupMenu.setOnMenuItemClickListener(this::clickOnPopupMenu);
        return view;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    db.target().delete(adapter.checks.get(position).getEntity());
                    adapter.notifyItemRemoved(position);
                    break;
                case ItemTouchHelper.RIGHT:
                    dialog.targetEntity = adapter.checks.get(position).getEntity();
                    dialog.product.setText(adapter.checks.get(position).getName());
                    dialog.dialog.show();
                    targetRight = adapter.checks.get(position);
                    positionRight = position;
                    adapter.checks.remove(position);
                    adapter.notifyItemRemoved(position);
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftActionIcon(R.drawable.baseline_restore_from_trash_24)
                    .addSwipeRightActionIcon(R.drawable.icon_story_vector)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}