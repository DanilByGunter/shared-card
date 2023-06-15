package com.project.shared_card.activity.main_screen.check;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.Animation;
import com.project.shared_card.activity.main_screen.check.dialog.DialogAddProduct;
import com.project.shared_card.databinding.FragmentCheckBinding;


public class CheckFragment extends Fragment {
    int heightStartForNavigation;
    int heightStartForButton;
    boolean flagAnimation = true;
    FragmentCheckBinding binding;

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


    void clickOnAddProduct(View v) {
        DialogAddProduct dialog = DialogAddProduct.newInstance(binding.checkPager.getCurrentItem());
        dialog.show(getChildFragmentManager(), "dialog");
    }

    private void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        if (position == 0)
            tab.setText(getString(R.string.current_list));
        else
            tab.setText(getString(R.string.target_list));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_check, container, false);

        binding.checkPager.setAdapter(new AdapterForPage(getActivity()));
        binding.checkPager.setOffscreenPageLimit(2);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.checkTab, binding.checkPager, this::onConfigureTab);
        tabLayoutMediator.attach();
        binding.addFab.setOnClickListener(this::clickOnAddProduct);

        return binding.getRoot();
    }

    public void settingForAnimationOfCheck() {

        RecyclerView recyclerView = (RecyclerView) binding.checkPager.getChildAt(0);
        View viewProduct = recyclerView.getChildAt(0);
        View viewTarget = recyclerView.getChildAt(1);
        RecyclerView listProduct = viewProduct.findViewById(R.id.list);
        RecyclerView listTarget = viewTarget.findViewById(R.id.list);
        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (flagAnimation) {
                    heightStartForNavigation = (int) getActivity().findViewById(R.id.bottom_navigation).getY();
                    heightStartForButton = (int) binding.addFab.getY();
                    flagAnimation = false;
                }
                if (!recyclerView.canScrollVertically(1) && recyclerView.canScrollVertically(-1)) {
                    Animation.animationDownOfNavigationView(getActivity().findViewById(R.id.bottom_navigation));
                    Animation.animationDownOfButton(binding.addFab);
                }
                if (!recyclerView.canScrollVertically(1) && !recyclerView.canScrollVertically(-1)) {
                    Animation.animationUpOfNavigationView(getActivity().findViewById(R.id.bottom_navigation), heightStartForNavigation);
                    Animation.animationUpOfButton(binding.addFab, heightStartForButton);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0) {
                    Animation.animationUpOfNavigationView(getActivity().findViewById(R.id.bottom_navigation), heightStartForNavigation);
                    Animation.animationUpOfButton(binding.addFab, heightStartForButton);
                }
            }
        };
        listProduct.addOnScrollListener(scrollListener);
        listTarget.addOnScrollListener(scrollListener);
    }
}