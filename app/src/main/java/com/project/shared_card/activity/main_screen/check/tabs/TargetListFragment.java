package com.project.shared_card.activity.main_screen.check.tabs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.project.shared_card.R;

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
        buttonSort = view.findViewById(R.id.button_sort);
        buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu();
            }
        });
    }
    private void popupMenu(){
        Context wrapper = new ContextThemeWrapper(getContext(), R.style.popup_menu);
        PopupMenu popupMenu = new PopupMenu(wrapper, buttonSort);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.by_product:
                        Toast.makeText(getContext(),
                                menuItem.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.by_date:
                        Toast.makeText(getContext(),
                                menuItem.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.by_category:
                        Toast.makeText(getContext(),
                                menuItem.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.by_user:
                        Toast.makeText(getContext(),
                                menuItem.getTitle(),
                                Toast.LENGTH_SHORT).show();
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_target_list, container, false);
    }
}