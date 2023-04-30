package com.project.shared_card.activity.main_screen.check;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DateConverter;
import com.project.shared_card.activity.main_screen.check.dialog.AdapterForSpinner;
import com.project.shared_card.activity.main_screen.check.dialog.DialogAddProduct;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.check.CheckEntity;

import java.util.List;


public class CheckFragment extends Fragment {

    ViewPager2 viewPager;
    DialogAddProduct dialogAddProduct;
    FloatingActionButton buttonAddProduct;
    ImplDB db;
    TabLayout tabLayout;
    FragmentStateAdapter adapter;
    SharedPreferences settings;

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

        init(view);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeCategory(position);
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        db.getMetricsRepository().getAll().observe((LifecycleOwner) getContext(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                dialogAddProduct.metric.setAdapter(new AdapterForSpinner(getContext(), strings));
            }
        });

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, this::onConfigureTab);
        tabLayoutMediator.attach();
        buttonAddProduct.setOnClickListener(this::clickOnAddProduct);
        dialogAddProduct.ready.setOnClickListener(this::clickOnReadyToCreateProduct);
    }

    void clickOnReadyToCreateProduct(View v) {
        CheckEntity check = new CheckEntity();
        check.setProductName(dialogAddProduct.name.getText().toString());
        check.setProductCount(Integer.parseInt(dialogAddProduct.count.getText().toString()));
        check.setMetricId(dialogAddProduct.metric.getSelectedItemPosition()+1);
        check.setCategoryId(dialogAddProduct.category.getSelectedItemPosition()+1);
        check.setDateFirst(DateConverter.FromNowDateToLong());

        String groupId = settings.getString(getString(R.string.key_for_select_group_id), "no id");
        check.setGroupNameId(Long.parseLong(groupId));

        if (groupId.equals(getString(R.string.me_id))) {
            check.setUserNameCreatorId(Long.parseLong(groupId));
        } else {
            String userId = settings.getString(getString(R.string.key_for_me_id_server), "no id");
            check.setUserNameCreatorId(Long.parseLong(userId));
        }
        db.getCheckRepository().addCheck(check);

        dialogAddProduct.dialog.dismiss();
    }

    void clickOnAddProduct(View v) {
        dialogAddProduct.dialog.show();
    }

    private void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        if (position == 0)
            tab.setText(getString(R.string.current_list));
        else
            tab.setText(getString(R.string.target_list));
    }

    void changeCategory(int position) {
        if (position == 0) {
            db.getCategoriesRepository().getforPorduct().observe((LifecycleOwner) getContext(), new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    dialogAddProduct.category.setAdapter(new AdapterForSpinner(getContext(), strings));
                }
            });
        } else {
            db.getCategoriesRepository().getForTarget().observe((LifecycleOwner) getContext(), new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    dialogAddProduct.category.setAdapter(new AdapterForSpinner(getContext(), strings));
                }
            });
        }
    }

    void init(View v) {
        settings = getContext().getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        dialogAddProduct = new DialogAddProduct(getContext());

        viewPager = v.findViewById(R.id.check_pager);
        buttonAddProduct = v.findViewById(R.id.add_fab);
        tabLayout = v.findViewById(R.id.check_tab);

        adapter = new AdapterForPage(getActivity());

        db = new ImplDB(getContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check, container, false);
    }
}