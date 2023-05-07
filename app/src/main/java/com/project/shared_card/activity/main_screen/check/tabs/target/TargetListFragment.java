package com.project.shared_card.activity.main_screen.check.tabs.target;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.ModelConverter;
import com.project.shared_card.activity.main_screen.check.PopupMenu;
import com.project.shared_card.activity.main_screen.check.tabs.target.model.Target;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.check.target.FullTarget;

import java.util.List;

public class TargetListFragment extends Fragment {
    Button buttonSort;
    RecyclerView list;
    PopupMenu popupMenu;
    TargetAdapter adapter;
    ImplDB db;
    SharedPreferences settings;
    String idGroup;
    SwipeRefreshLayout swipe;
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
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        swipe.setOnRefreshListener(this::getCheck);
        list.setAdapter(adapter);
        buttonSort.setOnClickListener(this::clickOnOpenSort);
        db.target().getAll(Long.valueOf(idGroup)).observe(getViewLifecycleOwner(), new Observer<List<FullTarget>>() {
            @Override
            public void onChanged(List<FullTarget> fullTargets) {
                List<Target> targets = ModelConverter.FromTargetEntityToTargetModel(fullTargets);
                adapter = new TargetAdapter(getContext(),targets,Long.valueOf(idGroup));
                list.setAdapter(adapter);
            }
        });
    }
    void clickOnOpenSort(View v){
        popupMenu.popupMenu();
    }
    void init(View v){
        settings = getContext().getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        idGroup = settings.getString(getString(R.string.key_for_select_group_id),"no_id");
        list = v.findViewById(R.id.list_target);
        buttonSort = v.findViewById(R.id.button_sort);
        swipe = v.findViewById(R.id.swipe_target);
        popupMenu = new PopupMenu(getContext(),buttonSort);

        db = new ImplDB(getContext());
    }
    void getCheck(){
        db.target().getAll(Long.valueOf(idGroup)).observe(getViewLifecycleOwner(), new Observer<List<FullTarget>>() {
            @Override
            public void onChanged(List<FullTarget> fullTargets) {
                adapter.update( ModelConverter.FromTargetEntityToTargetModel(fullTargets));
                swipe.setRefreshing(false);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_target_list, container, false);
    }
}