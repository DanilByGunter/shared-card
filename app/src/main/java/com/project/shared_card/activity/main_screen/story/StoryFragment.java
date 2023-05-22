package com.project.shared_card.activity.main_screen.story;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.PopupMenu;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.story.model.History;

import java.util.List;


public class StoryFragment extends Fragment {
    public RecyclerView recyclerView;
    public MutableLiveData<Boolean> liveData = new MutableLiveData<>();
    SharedPreferences settings;
    Button buttonSort;
    EditText searchBar;
    Adapter adapter;
    ImplDB db;
    long groupId;
    PopupMenu popupMenu;
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
        popupMenu.popupMenu.setOnMenuItemClickListener(this::clickOnPopupMenu);

        buttonSort.setOnClickListener(this::clickOnButtonSort);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });
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

    void init(View view){
        settings = getContext().getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        groupId = Long.parseLong(settings.getString(getString(R.string.key_for_select_group_id),"no_id"));
        searchBar = view.findViewById(R.id.input_line);
        recyclerView = view.findViewById(R.id.list_story);
        buttonSort = view.findViewById(R.id.button_sort);
        popupMenu = new PopupMenu(getContext(),buttonSort);
        db = new ImplDB(getContext());
        db.story().getAll(groupId).observe(getViewLifecycleOwner(), new Observer<List<com.project.shared_card.database.entity.story.model.History>>() {
            @Override
            public void onChanged(List<History> histories) {
                adapter = new Adapter(getContext(),histories);
                recyclerView.setAdapter(adapter);
                liveData.postValue(true);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_story, container, false);
    }
}