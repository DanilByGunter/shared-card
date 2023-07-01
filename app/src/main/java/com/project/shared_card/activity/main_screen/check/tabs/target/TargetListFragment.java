package com.project.shared_card.activity.main_screen.check.tabs.target;

import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.ModelConverter;
import com.project.shared_card.activity.main_screen.PopupMenu;
import com.project.shared_card.activity.main_screen.check.callback.AdapterCallback;
import com.project.shared_card.activity.main_screen.check.callback.ButtonClickCallback;
import com.project.shared_card.activity.main_screen.check.callback.SwipeCallback;
import com.project.shared_card.activity.main_screen.check.dialog.DialogAddProductToHistory;
import com.project.shared_card.activity.main_screen.check.tabs.AdapterForRecyclerView;
import com.project.shared_card.database.entity.check.target.FullTarget;
import com.project.shared_card.database.entity.check.target.TargetEntity;
import com.project.shared_card.databinding.FragmentListBinding;

import java.util.List;
import java.util.stream.Collectors;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class TargetListFragment extends Fragment {
    PopupMenu popupMenu;
    AdapterForRecyclerView adapter;
    FragmentListBinding binding;
    ItemTouchHelper itemTouchHelper;
    TargetListViewModel viewModel;
    List<TargetEntity> targetEntityList;

    private final AdapterCallback adapterCallback = (v, position) -> {
        TargetEntity entity = targetEntityList.get(position);
        if (((CheckBox) v).isChecked()) {
            viewModel.updateTargetIsChecked(entity);
        } else {
            viewModel.updateTargetIsUnchecked(entity);
        }
    };

    private final ButtonClickCallback buttonClickCallback = () -> {
        popupMenu.openPopupMenu();
    };

    private final SwipeCallback swipeCallback = () -> {
        binding.swipe.setRefreshing(false);
    };
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            viewModel.setQuery(s);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_list, container, false);

        adapter = new AdapterForRecyclerView(adapterCallback);
        binding.list.setAdapter(adapter);

        binding.setButtonCallback(buttonClickCallback);
        binding.searchBar.inputLine.addTextChangedListener(textWatcher);

        binding.swipe.setOnRefreshListener(() -> swipeCallback.swipe());

        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.list);

        popupMenu = new PopupMenu(getContext(), binding.searchBar.buttonSort);
        popupMenu.popupMenu.setOnMenuItemClickListener(this::clickOnPopupMenu);

        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TargetListViewModel.class);

        getTarget();
    }

    void getTarget() {
        viewModel.getTarget().observe(getViewLifecycleOwner(), fullTargets -> {
            targetEntityList = fullTargets.stream().map(FullTarget::getTarget).collect(Collectors.toList());
            adapter.update(ModelConverter.FromTargetEntityToTargetModel(fullTargets));
        });
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
                    viewModel.deleteTarget(targetEntityList.get(position));
                    break;
                case ItemTouchHelper.RIGHT:
                    swipeRight(position);
                    break;
            }
        }

        void swipeRight(int position) {
            DialogAddProductToHistory dialod = DialogAddProductToHistory.newInstance(targetEntityList.get(position));
            dialod.show(getChildFragmentManager(), "dialog");
            dialod.getViewLifecycleOwnerLiveData().observe(getViewLifecycleOwner(), lifecycleOwner -> {
                if (lifecycleOwner == null) {
                    adapter.notifyItemChanged(position);
                }
            });
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