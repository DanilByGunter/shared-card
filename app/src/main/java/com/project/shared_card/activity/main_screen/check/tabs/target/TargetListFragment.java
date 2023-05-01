package com.project.shared_card.activity.main_screen.check.tabs.target;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.check.PopupMenu;

public class TargetListFragment extends Fragment {
    Button buttonSort;
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
        Button buttonSort = view.findViewById(R.id.button_sort);
        RecyclerView list = view.findViewById(R.id.list_product);
        TargetAdapter targetAdapter = new TargetAdapter(getContext());
        list.setAdapter(targetAdapter);
        PopupMenu popupMenu = new PopupMenu(getContext(),buttonSort);
        buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.popupMenu();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_target_list, container, false);
    }
}