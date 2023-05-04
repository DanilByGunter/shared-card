package com.project.shared_card.activity.main_screen.check.tabs.current;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.ModelConverter;
import com.project.shared_card.activity.main_screen.check.PopupMenu;
import com.project.shared_card.activity.main_screen.check.tabs.current.model.Product;
import com.project.shared_card.activity.database.ImplDB;
import com.project.shared_card.activity.database.entity.check.product.FullProduct;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class CurrentListFragment extends Fragment {
    Button buttonSort;
    RecyclerView list;
    PopupMenu popupMenu;
    ProductAdapter productAdapter;
    ImplDB db;
    SharedPreferences settings;
    String idGroup;
    SwipeRefreshLayout swipe;
    ItemTouchHelper itemTouchHelper;
    public CurrentListFragment() {
    }

    public static CurrentListFragment newInstance() {
        CurrentListFragment fragment = new CurrentListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) { }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        swipe.setOnRefreshListener(this::getCheck);
        db.product().getAll(Long.valueOf(idGroup)).observe(getViewLifecycleOwner(), new Observer<List<FullProduct>>() {
            @Override
            public void onChanged(List<FullProduct> fullProducts) {
                List<Product> products = ModelConverter.FromProductEntityToProductModel(fullProducts);
                productAdapter = new ProductAdapter(getContext(),products);
                list.setAdapter(productAdapter);
            }
        });
        itemTouchHelper.attachToRecyclerView(list);
        buttonSort.setOnClickListener(this::clickOnOpenSort);
    }

    void clickOnOpenSort(View v){
        popupMenu.popupMenu();
    }

    void init(View v){
        settings = getContext().getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        idGroup = settings.getString(getString(R.string.key_for_select_group_id),"no_id");
        list = v.findViewById(R.id.list_product);
        buttonSort = v.findViewById(R.id.button_sort);
        swipe = v.findViewById(R.id.swipe_current);
        popupMenu = new PopupMenu(getContext(),buttonSort);
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        db = new ImplDB(getContext());



    }
    void getCheck(){
        db.product().getAll(Long.valueOf(idGroup)).observe(this, new Observer<List<FullProduct>>() {
            @Override
            public void onChanged(List<FullProduct> fullProducts) {
                productAdapter.update( ModelConverter.FromProductEntityToProductModel(fullProducts));
                swipe.setRefreshing(false);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_list, container, false);
    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            switch(direction){
                case ItemTouchHelper.LEFT:
                    db.product().delete(productAdapter.checks.get(position).getEntity());
                    productAdapter.notifyItemRemoved(position);
                    break;
                case ItemTouchHelper.RIGHT:
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftActionIcon(R.drawable.baseline_restore_from_trash_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}