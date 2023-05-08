package com.project.shared_card.activity.main_screen.story;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.ModelConverter;
import com.project.shared_card.activity.main_screen.check.PopupMenu;
import com.project.shared_card.activity.main_screen.story.model.History;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.check.product.FullProduct;
import com.project.shared_card.database.entity.check.target.FullTarget;

import java.util.ArrayList;
import java.util.List;


public class StoryFragment extends Fragment {
    RecyclerView recyclerView;
    SharedPreferences settings;
    Button buttonSort;
    Adapter adapter;
    ImplDB db;
    long groupId;
    List<History> histories;
    com.project.shared_card.activity.main_screen.check.PopupMenu popupMenu;
    public StoryFragment() {
    }

    public static StoryFragment newInstance() {
        StoryFragment fragment = new StoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        recyclerView.setAdapter(adapter);


        buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.popupMenu();
            }
        });
    }

    void init(View view){
        settings = getContext().getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        groupId = Long.parseLong(settings.getString(getString(R.string.key_for_select_group_id),"no_id"));
        recyclerView = view.findViewById(R.id.list_story);
        buttonSort = view.findViewById(R.id.button_sort);
        popupMenu = new PopupMenu(getContext(),buttonSort);
        db = new ImplDB(getContext());
        histories = new ArrayList<>();
        db.product().getAllForHistory(groupId).observe(getViewLifecycleOwner(), new Observer<List<FullProduct>>() {
            @Override
            public void onChanged(List<FullProduct> fullProducts) {
                for (FullProduct fullProduct: fullProducts) {
                    histories.add(ModelConverter.FromProductEntityToHistory(fullProduct));
                }
                adapter.notifyDataSetChanged();
            }
        });
        db.target().getAllForHistory(groupId).observe(getViewLifecycleOwner(), new Observer<List<FullTarget>>() {
            @Override
            public void onChanged(List<FullTarget> fullTargets) {
                for (FullTarget fullTarget: fullTargets) {
                    histories.add(ModelConverter.FromTargetEntityToHistory(fullTarget));
                }
            }
        });
        adapter = new Adapter(getContext(),histories);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_story, container, false);
    }
}