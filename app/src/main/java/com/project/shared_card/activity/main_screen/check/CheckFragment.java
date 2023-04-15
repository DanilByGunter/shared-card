package com.project.shared_card.activity.main_screen.check;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.project.shared_card.R;


public class CheckFragment extends Fragment {

    ViewPager2 viewPager;

    public CheckFragment() {
    }

    public static CheckFragment newInstance() {
        CheckFragment fragment = new CheckFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.check_pager);
        FragmentStateAdapter adapter = new AdapterForPage(getActivity());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = view.findViewById(R.id.check_tab);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position==0)
                    tab.setText(getString(R.string.current_list));
                else
                    tab.setText(getString(R.string.target_list));
            }
        });
        tabLayoutMediator.attach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check, container, false);
    }
}