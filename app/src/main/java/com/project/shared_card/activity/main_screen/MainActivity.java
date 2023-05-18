package com.project.shared_card.activity.main_screen;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.check.CheckFragment;
import com.project.shared_card.activity.main_screen.group.GroupFragment;
import com.project.shared_card.activity.main_screen.statistic.StatisticsFragment;
import com.project.shared_card.activity.main_screen.story.StoryFragment;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;


public class MainActivity extends AppCompatActivity {
    SharedPreferences settings;
    BottomNavigationView navigationView;
    TextView nameGroup;
    ImageView imageGroup;
    Long idGroup;
    private StoryFragment storyFragment = new StoryFragment();
    private StatisticsFragment statisticsFragment = new StatisticsFragment();
    private GroupFragment groupFragment;
    private CheckFragment checkFragment = new CheckFragment();
    private String groupPath;
    int heightStartForNavigation;
    int heightStartForButton;
    int heightEndForNavigation;
    Boolean First = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstraintLayout constraintLayout = (ConstraintLayout) this.getLayoutInflater().inflate(R.layout.activity_main, null);
        constraintLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(First) {
                    heightStartForNavigation = (int) navigationView.getY();
                    heightStartForButton = (int) checkFragment.buttonAddProduct.getY();
                    First =false;
                    settingForAnimationOfCheck();
                }
            }
        });
        setContentView(constraintLayout);
        init();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.show_fragment, checkFragment)
                .commit();

        navigationView.setSelectedItemId(R.id.menu_check);
        navigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
//        navigationView.setSelectedItemId(R.id.menu_check);

    }

    @Override
    protected void onStart() {
        super.onStart();
        ImplDB db = new ImplDB(this);
        db.group_name().getGroupById(idGroup).observe(this, new Observer<GroupNameEntity>() {
            @Override
            public void onChanged(GroupNameEntity entity) {
                nameGroup.setText(entity.getName());
                imageGroup.setImageURI(Uri.parse(groupPath));
            }
        });


    }

    void init(){
        settings = getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        nameGroup = findViewById(R.id.main_name_group);
        imageGroup = findViewById(R.id.main_image_group);
        idGroup = Long.valueOf(settings.getString(getString(R.string.key_for_select_group_id), "XD"));
        groupPath = getFilesDir() + "/group/" + idGroup;
        groupFragment = new GroupFragment(findViewById(R.id.toolbar));
        navigationView = findViewById(R.id.bottom_navigation);
        heightEndForNavigation = 0;//-navigationView.getHeight();
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_group:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.show_fragment, groupFragment)
                        .commit();
                return true;
            case R.id.menu_check:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.show_fragment, checkFragment)
                        .commit();
                settingForAnimationOfCheck();
                return true;
            case R.id.menu_statistic:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.show_fragment, statisticsFragment)
                        .commit();
                return true;
            case R.id.manu_story:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.show_fragment, storyFragment)
                        .commit();
                storyFragment.liveData.observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean){
                            settingForAnimationOfStory();
                        }
                    }
                });
                return true;
        }
        return false;
    }
    void settingForAnimationOfCheck(){
        RecyclerView recyclerView = (RecyclerView) checkFragment.viewPager.getChildAt(0);
        View viewProduct = recyclerView.getChildAt(0);
        View viewTarget = recyclerView.getChildAt(1);
        RecyclerView listProduct = viewProduct.findViewById(R.id.list_product);
        RecyclerView listTarget = viewTarget.findViewById(R.id.list_target);
        listProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && recyclerView.canScrollVertically(-1)) {
                    animationDownOfNavigationView();
                    animationDownOfButton();
                }
                if(!recyclerView.canScrollVertically(1) && !recyclerView.canScrollVertically(-1) ){
                    animationUpOfNavigationView();
                    animationUpOfButton();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy<0){
                    animationUpOfNavigationView();
                    animationUpOfButton();
                }
            }
        });
        listTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && recyclerView.canScrollVertically(-1) ) {
                    animationDownOfNavigationView();
                    animationDownOfButton();
                }
                if(!recyclerView.canScrollVertically(1) && !recyclerView.canScrollVertically(-1) ){
                    animationUpOfNavigationView();
                    animationUpOfButton();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy<0){
                    animationUpOfNavigationView();
                    animationUpOfButton();
                }
            }
        });
    }
    void settingForAnimationOfStory(){
        storyFragment.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && recyclerView.canScrollVertically(-1) ) {
                    animationDownOfNavigationView();
                }
                if(!recyclerView.canScrollVertically(1) && !recyclerView.canScrollVertically(-1) ){
                    animationUpOfNavigationView();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy<0){
                    animationUpOfNavigationView();
                }
            }
        });
    }

    void animationDownOfNavigationView(){
        ValueAnimator animatorForNavigationView = ValueAnimator.ofInt((int) navigationView.getY(),2200);
        animatorForNavigationView.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                navigationView.setY(value);
                navigationView.requestLayout();
            }
        });
        animatorForNavigationView.setDuration(500);
        animatorForNavigationView.start();
    }
    void animationUpOfNavigationView(){
        ValueAnimator animatorForNavigationView = ValueAnimator.ofInt((int) navigationView.getY(), heightStartForNavigation);
        animatorForNavigationView.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                navigationView.setY(value);
                navigationView.requestLayout();
            }
        });
        animatorForNavigationView.setDuration(500);
        animatorForNavigationView.start();
    }
    void animationDownOfButton(){
        ValueAnimator animatorForNavigationView = ValueAnimator.ofInt((int) checkFragment.buttonAddProduct.getY(),1850);
        animatorForNavigationView.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                checkFragment.buttonAddProduct.setY(value);
                checkFragment.buttonAddProduct.requestLayout();
            }
        });
        animatorForNavigationView.setDuration(500);
        animatorForNavigationView.start();
    }
    void animationUpOfButton(){
        ValueAnimator animatorForNavigationView = ValueAnimator.ofInt((int) checkFragment.buttonAddProduct.getY(), heightStartForButton);
        animatorForNavigationView.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                checkFragment.buttonAddProduct.setY(value);
                checkFragment.buttonAddProduct.requestLayout();
            }
        });
        animatorForNavigationView.setDuration(500);
        animatorForNavigationView.start();
    }
}