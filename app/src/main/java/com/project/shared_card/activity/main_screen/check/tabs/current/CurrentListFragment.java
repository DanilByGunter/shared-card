package com.project.shared_card.activity.main_screen.check.tabs.current;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
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
import com.project.shared_card.database.entity.check.product.FullProduct;
import com.project.shared_card.database.entity.check.product.ProductEntity;
import com.project.shared_card.databinding.FragmentListBinding;

import java.util.List;
import java.util.stream.Collectors;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class CurrentListFragment extends Fragment {


    PopupMenu popupMenu;
    AdapterForRecyclerView productAdapter;
    ItemTouchHelper itemTouchHelper;
    private FragmentListBinding binding;
    private CurrentListViewModel viewModel;
    private List<ProductEntity> productEntityList;

    private final ButtonClickCallback buttonClickCallback = () -> {
        popupMenu.openPopupMenu();
    };

    private final AdapterCallback adapterCallback = (v, position) -> {
        ProductEntity entity = productEntityList.get(position);
        if (((CheckBox) v).isChecked()) {
            viewModel.updateProductIsChecked(entity);
        } else {
            viewModel.updateProductIsUnchecked(entity);
        }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_list, container, false);
        binding.swipe.setOnRefreshListener(() -> swipeCallback.swipe());

        binding.setButtonCallback(buttonClickCallback);
        binding.searchBar.inputLine.addTextChangedListener(textWatcher);

        productAdapter = new AdapterForRecyclerView(adapterCallback);
        binding.list.setAdapter(productAdapter);

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
        viewModel = new ViewModelProvider(this).get(CurrentListViewModel.class);

        getProduct();
    }

    void getProduct() {
        viewModel.getProducts().observe(getViewLifecycleOwner(), fullProducts -> {
            productEntityList = fullProducts.stream().map(FullProduct::getProduct).collect(Collectors.toList());
            productAdapter.update(ModelConverter.FromProductEntityToProductModel(fullProducts));
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
                    viewModel.deleteProduct(productEntityList.get(position));
                    break;
                case ItemTouchHelper.RIGHT:
                    swipeRight(position);
                    break;
            }
        }

        void swipeRight(int position) {
            DialogAddProductToHistory dialod = DialogAddProductToHistory.newInstance(productEntityList.get(position));
            dialod.show(getChildFragmentManager(), "dialog");
            dialod.getViewLifecycleOwnerLiveData().observe(getViewLifecycleOwner(), lifecycleOwner -> {
                    if(lifecycleOwner==null) {
                        productAdapter.notifyItemChanged(position);
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