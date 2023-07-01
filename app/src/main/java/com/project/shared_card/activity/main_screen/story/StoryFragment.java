package com.project.shared_card.activity.main_screen.story;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.shared_card.R;
import com.project.shared_card.activity.converter.ModelConverter;
import com.project.shared_card.activity.main_screen.Animation;
import com.project.shared_card.activity.main_screen.PopupMenu;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.check.product.FullProduct;
import com.project.shared_card.database.entity.story.model.History;
import com.project.shared_card.databinding.FragmentStoryBinding;

import java.util.List;


public class StoryFragment extends Fragment {
    PopupMenu popupMenu;
    Adapter adapter;
    FragmentStoryBinding binding;
    StoryFragmentViewModel viewModel;
    int heightStartForNavigation;

    public StoryFragment() {
    }
    void clickOnButtonSort(View v){
        popupMenu.openPopupMenu();
    }

    public boolean clickOnPopupMenu(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.by_product:
                adapter.sorted(1);
                return true;
            case R.id.by_date:
                adapter.sorted(2);
                return true;
            case R.id.by_category:
                adapter.sorted(3);
                return true;
            case R.id.by_user:
                adapter.sorted(4);
                return true;
            default:
                return false;
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            viewModel.setQuery(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_story, container, false);

        adapter = new Adapter();
        binding.listStory.setAdapter(adapter);

        popupMenu = new PopupMenu(getContext(),binding.searchBar.buttonSort);
        popupMenu.popupMenu.setOnMenuItemClickListener(this::clickOnPopupMenu);

        binding.searchBar.buttonSort.setOnClickListener(this::clickOnButtonSort);
        binding.searchBar.inputLine.addTextChangedListener(textWatcher);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel =new ViewModelProvider(this).get(StoryFragmentViewModel.class);

        getHistory();
    }
    void getHistory(){
        viewModel.getHistory().observe(getViewLifecycleOwner(), histories -> {
            adapter.setHistories(histories);
            settingForAnimationOfStory();
        });
    }
    void settingForAnimationOfStory(){
        heightStartForNavigation = (int) getActivity().findViewById(R.id.bottom_navigation).getY();
        binding.listStory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && recyclerView.canScrollVertically(-1) ) {
                    Animation.animationDownOfNavigationView(getActivity().findViewById(R.id.bottom_navigation));
                }
                if(!recyclerView.canScrollVertically(1) && !recyclerView.canScrollVertically(-1) ){
                    Animation.animationUpOfNavigationView(getActivity().findViewById(R.id.bottom_navigation),heightStartForNavigation);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy<0){
                    Animation.animationUpOfNavigationView(getActivity().findViewById(R.id.bottom_navigation),heightStartForNavigation);
                }
            }
        });
    }
}