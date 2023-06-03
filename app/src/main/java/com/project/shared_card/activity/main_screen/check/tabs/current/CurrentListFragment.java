package com.project.shared_card.activity.main_screen.check.tabs.current;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
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
import android.widget.Filter;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DateConverter;
import com.project.shared_card.activity.converter.ModelConverter;
import com.project.shared_card.activity.main_screen.check.dialog.AdapterForSpinner;
import com.project.shared_card.activity.main_screen.check.dialog.DialogAddProductToHistory;
import com.project.shared_card.activity.main_screen.check.tabs.current.model.Product;
import com.project.shared_card.activity.main_screen.PopupMenu;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.check.product.FullProduct;
import com.project.shared_card.database.entity.check.product.ProductEntity;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class CurrentListFragment extends Fragment {
    Button buttonSort;
    RecyclerView list;
    EditText searchBar;
    PopupMenu popupMenu;
    ProductAdapter productAdapter;
    ImplDB db;
    SharedPreferences settings;
    String idGroup;
    SwipeRefreshLayout swipe;
    ItemTouchHelper itemTouchHelper;
    AdapterForSpinner adapterForSpinner;
    DialogAddProductToHistory dialog;
    Product productRight;
    int positionRight;

    public CurrentListFragment() {
    }

    public static CurrentListFragment newInstance() {
        CurrentListFragment fragment = new CurrentListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    public boolean clickOnPopupMenu(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.by_product:
                productAdapter.sorted(1);
                return true;
            case R.id.by_date:
                productAdapter.sorted(2);
                return true;
            case R.id.by_category:
                productAdapter.sorted(3);
                return true;
            case R.id.by_user:
                productAdapter.sorted(4);
                return true;
            default:
                return false;
        }
    }

    void clickOnDialogReady(View v) {
        if(dialog.price.getText().toString().equals(""))
            return;
        ProductEntity product = dialog.productEntity;
        product.setStatus(2);
        product.setDateLast(DateConverter.FromNowDateToLong());
        product.setPrice(Integer.parseInt(dialog.price.getText().toString()));
        product.setShopId(dialog.shop.getSelectedItemPosition() + 1);
        product.setCurrencyId(dialog.currency.getSelectedItemPosition() + 1);
        if (!settings.getString(getString(R.string.key_for_me_id_server), "no_id").equals("no_id")) {
            product.setUserNameBuyerId(Long.parseLong(settings.getString(getString(R.string.key_for_me_id_server), "no_id")));
        } else {
            product.setUserNameBuyerId(Long.parseLong(getString(R.string.me_id)));
        }
        db.product().update(product);
        dialog.price.setText("");
        dialog.dialog.dismiss();

    }

    void clickOnOpenSort(View v) {
        popupMenu.openPopupMenu();
    }

    void init(View v) {
        settings = getContext().getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        searchBar = v.findViewById(R.id.input_line);
        idGroup = settings.getString(getString(R.string.key_for_select_group_id), "no_id");
        list = v.findViewById(R.id.list_product);
        buttonSort = v.findViewById(R.id.button_sort);
        swipe = v.findViewById(R.id.swipe_current);
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
        db.shop_product().getAll().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                adapterForSpinner = new AdapterForSpinner(getContext(), strings);
                dialog.shop.setAdapter(adapterForSpinner);
            }
        });


    }

    void getCheck() {
        db.product().getAllForCheck(Long.valueOf(idGroup)).observe(this, new Observer<List<FullProduct>>() {
            @Override
            public void onChanged(List<FullProduct> fullProducts) {
                productAdapter.update(ModelConverter.FromProductEntityToProductModel(fullProducts));
                swipe.setRefreshing(false);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_current_list, container, false);
                init(view);
        swipe.setOnRefreshListener(this::getCheck);
        swipe.setColorSchemeResources(R.color.dark_green);
        db.product().getAllForCheck(Long.valueOf(idGroup)).observe(getViewLifecycleOwner(), new Observer<List<FullProduct>>() {
            @Override
            public void onChanged(List<FullProduct> fullProducts) {
                List<Product> products = ModelConverter.FromProductEntityToProductModel(fullProducts);
                productAdapter = new ProductAdapter(getContext(), products);
                list.setAdapter(productAdapter);
            }
        });
        itemTouchHelper.attachToRecyclerView(list);
        buttonSort.setOnClickListener(this::clickOnOpenSort);
        dialog.product.setSelected(true);
        dialog.dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                productAdapter.checks.add(positionRight,productRight);
                productAdapter.notifyDataSetChanged();
            }
        });
        dialog.ready.setOnClickListener(this::clickOnDialogReady);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productAdapter.getFilter().filter(s);
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
                    db.product().delete(productAdapter.checks.get(position).getEntity());
                    productAdapter.notifyItemRemoved(position);
                    break;
                case ItemTouchHelper.RIGHT:
                    dialog.productEntity = productAdapter.checks.get(position).getEntity();
                    dialog.product.setText(productAdapter.checks.get(position).getName());
                    dialog.dialog.show();
                    productRight =productAdapter.checks.get(position);
                    positionRight =position;
                    productAdapter.checks.remove(position);
                    productAdapter.notifyItemRemoved(position);
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