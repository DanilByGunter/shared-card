package com.project.shared_card.activity.main_screen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.project.shared_card.R;
import com.project.shared_card.activity.BasicApp;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.activity.main_screen.check.CheckFragment;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;
import com.project.shared_card.databinding.ActivityMainBinding;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    NavController navController;
    ActivityMainBinding binding;
    MainActivityViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        navController =((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container)).getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigation,navController);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        getGroup();
    }

    void getGroup(){
        viewModel.getGroup().observe(this, groupName -> {
            binding.mainImageGroup.setImageBitmap(DbBitmapUtility.getImage(groupName.getPhoto()));
            binding.mainNameGroup.setText(groupName.getName());
        });
    }

}