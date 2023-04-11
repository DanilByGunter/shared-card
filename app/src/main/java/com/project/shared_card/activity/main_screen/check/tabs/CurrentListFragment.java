package com.project.shared_card.activity.main_screen.check.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.shared_card.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentListFragment extends Fragment {


    public CurrentListFragment() {
    }

    public static CurrentListFragment newInstance() {
        CurrentListFragment fragment = new CurrentListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_list, container, false);
    }
}