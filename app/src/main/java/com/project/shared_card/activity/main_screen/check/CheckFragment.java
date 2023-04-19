package com.project.shared_card.activity.main_screen.check;

import android.animation.ValueAnimator;
import android.app.Dialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.check.dialog.AdapterForSpinner;


public class CheckFragment extends Fragment {

    ViewPager2 viewPager;
    Dialog dialogCreateProduct;
    FloatingActionButton buttonAddProduct;
    Spinner category;
    Spinner metric;
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
        String[] metrics = {"пупа", "лупа", "залупа"};
        String[] categories = {"алкоголь","молочка","мясо"};
        dialogCreateProduct = new Dialog(getContext());
        dialogCreateProduct.setContentView(R.layout.dialog_create_product);
        dialogCreateProduct.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        metric = dialogCreateProduct.findViewById(R.id.dialog_metric);
        category = dialogCreateProduct.findViewById(R.id.dialog_text_category);
        AdapterForSpinner adapterForCategory = new AdapterForSpinner(getContext(),categories);
        AdapterForSpinner adapterForMetric = new AdapterForSpinner(getContext(),metrics);
        category.setAdapter(adapterForCategory);
        metric.setAdapter(adapterForMetric);
        viewPager = view.findViewById(R.id.check_pager);
        buttonAddProduct = view.findViewById(R.id.add_fab);
        FragmentStateAdapter adapter = new AdapterForPage(getActivity());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
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
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCreateProduct.show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check, container, false);
    }
}