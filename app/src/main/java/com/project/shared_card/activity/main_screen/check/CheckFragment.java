package com.project.shared_card.activity.main_screen.check;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.project.shared_card.activity.converter.ModelConverter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DateConverter;
import com.project.shared_card.activity.main_screen.Animation;
import com.project.shared_card.activity.main_screen.MainActivity;
import com.project.shared_card.activity.main_screen.check.dialog.AdapterForSpinner;
import com.project.shared_card.activity.main_screen.check.dialog.DialogAddProduct;
import com.project.shared_card.activity.main_screen.check.tabs.current.ProductAdapter;
import com.project.shared_card.activity.main_screen.check.tabs.target.TargetAdapter;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.check.product.FullProduct;
import com.project.shared_card.database.entity.check.product.ProductEntity;
import com.project.shared_card.database.entity.check.target.FullTarget;
import com.project.shared_card.database.entity.check.target.TargetEntity;

import java.util.List;


public class CheckFragment extends Fragment {

    public ViewPager2 viewPager;
    DialogAddProduct dialogAddProduct;
    public FloatingActionButton buttonAddProduct;
    ImplDB db;
    TabLayout tabLayout;
    FragmentStateAdapter adapter;
    SharedPreferences settings;
    int heightStartForNavigation;
    int heightStartForButton;
    boolean flagAnimation=true;


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

    void clickOnReadyToCreateProduct(View v) {
        if(dialogAddProduct.name.getText().toString().equals("") || dialogAddProduct.count.getText().toString().equals(""))
            return;
        RecyclerView pager = (RecyclerView) viewPager.getChildAt(0);
        View view = pager.getChildAt(viewPager.getCurrentItem());
        RecyclerView recyclerView;
        String groupId = settings.getString(getString(R.string.key_for_select_group_id), "no id");
        if(viewPager.getCurrentItem()==0) {
            recyclerView = view.findViewById(R.id.list_product);
            ProductEntity check = new ProductEntity();
            check.setProductName(dialogAddProduct.name.getText().toString());
            check.setProductCount(Integer.parseInt(dialogAddProduct.count.getText().toString()));
            check.setMetricId(dialogAddProduct.metric.getSelectedItemPosition() + 1);
            check.setCategoryId(dialogAddProduct.category.getSelectedItemPosition() + 1);
            check.setDateFirst(DateConverter.FromNowDateToLong());
            check.setGroupNameId(Long.parseLong(groupId));
            check.setStatus(0);
            if (groupId.equals(getString(R.string.me_id))) {
                check.setUserNameCreatorId(Long.parseLong(groupId));
            } else {
                String userId = settings.getString(getString(R.string.key_for_me_id_server), "no id");
                check.setUserNameCreatorId(Long.parseLong(userId));
            }
            db.product().add(check);
            db.product().getAllForCheck(Long.valueOf(groupId)).observe(this, new Observer<List<FullProduct>>() {
                @Override
                public void onChanged(List<FullProduct> fullProducts) {
                    ProductAdapter productAdapter= (ProductAdapter) recyclerView.getAdapter();
                    productAdapter.update(ModelConverter.FromProductEntityToProductModel(fullProducts));
                }
            });
        }
        else{
            recyclerView = view.findViewById(R.id.list_target);
            TargetEntity target = new TargetEntity();
            target.setTargetName(dialogAddProduct.name.getText().toString());
            target.setPrice(Integer.parseInt(dialogAddProduct.count.getText().toString()));
            target.setCurrencyId(dialogAddProduct.metric.getSelectedItemPosition() + 1);
            target.setCategoryId(dialogAddProduct.category.getSelectedItemPosition() + 1);
            target.setDateFirst(DateConverter.FromNowDateToLong());
            target.setGroupNameId(Long.parseLong(groupId));

            if (groupId.equals(getString(R.string.me_id))) {
                target.setUserNameCreatorId(Long.parseLong(groupId));
            } else {
                String userId = settings.getString(getString(R.string.key_for_me_id_server), "no id");
                target.setUserNameCreatorId(Long.parseLong(userId));
            }
            db.target().add(target);
            db.target().getAllForCheck(Long.valueOf(groupId)).observe(this, new Observer<List<FullTarget>>() {
                @Override
                public void onChanged(List<FullTarget> fullTargets) {
                    TargetAdapter targetAdapter= (TargetAdapter) recyclerView.getAdapter();
                    targetAdapter.update(ModelConverter.FromTargetEntityToTargetModel(fullTargets));

                }
            });
        }
        dialogAddProduct.name.setText("");
        dialogAddProduct.count.setText("");
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
            dialogAddProduct.label.setText(getString(R.string.dialog_add_product));
            dialogAddProduct.name.setHint(getString(R.string.dialog_text_hint_porduct));
            dialogAddProduct.count.setHint(getString(R.string.dialog_text_hint_count));
            //todo
            db.category_product().getAll().observe(this, new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    dialogAddProduct.category.setAdapter(new AdapterForSpinner(getContext(), strings));
                }
            });
            db.metric().getAll().observe(this, new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    dialogAddProduct.metric.setAdapter(new AdapterForSpinner(getContext(),strings));
                }
            });
        } else {
            dialogAddProduct.label.setText(getString(R.string.dialog_add_target));
            dialogAddProduct.name.setHint(getString(R.string.dialog_text_hint_target));
            dialogAddProduct.count.setHint(getString(R.string.dialog_text_hint_currency));
            db.category_target().getAll().observe(this, new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    dialogAddProduct.category.setAdapter(new AdapterForSpinner(getContext(), strings));
                }
            });
            db.currency().getAll().observe(this, new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    dialogAddProduct.metric.setAdapter(new AdapterForSpinner(getContext(),strings));
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
        View view = inflater.inflate(R.layout.fragment_check, container, false);
        init(view);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeCategory(position);
                settingForAnimationOfCheck();
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        db.metric().getAll().observe((LifecycleOwner) getContext(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                dialogAddProduct.metric.setAdapter(new AdapterForSpinner(getContext(), strings));
            }
        });

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, this::onConfigureTab);
        tabLayoutMediator.attach();
        buttonAddProduct.setOnClickListener(this::clickOnAddProduct);
        dialogAddProduct.ready.setOnClickListener(this::clickOnReadyToCreateProduct);

        return view;
    }

    public void settingForAnimationOfCheck(){

        RecyclerView recyclerView = (RecyclerView) viewPager.getChildAt(0);
        View viewProduct = recyclerView.getChildAt(0);
        View viewTarget = recyclerView.getChildAt(1);
        RecyclerView listProduct = viewProduct.findViewById(R.id.list_product);
        RecyclerView listTarget = viewTarget.findViewById(R.id.list_target);
        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(flagAnimation){
                    heightStartForNavigation = (int) getActivity().findViewById(R.id.bottom_navigation).getY();
                    heightStartForButton = (int) buttonAddProduct.getY();
                    flagAnimation =false;
                }
                if (!recyclerView.canScrollVertically(1) && recyclerView.canScrollVertically(-1)) {
                    Animation.animationDownOfNavigationView(getActivity().findViewById(R.id.bottom_navigation));
                    Animation.animationDownOfButton(buttonAddProduct);
                }
                if(!recyclerView.canScrollVertically(1) && !recyclerView.canScrollVertically(-1) ){
                    Animation.animationUpOfNavigationView(getActivity().findViewById(R.id.bottom_navigation),heightStartForNavigation);
                    Animation.animationUpOfButton(buttonAddProduct,heightStartForButton);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy<0){
                    Animation.animationUpOfNavigationView(getActivity().findViewById(R.id.bottom_navigation),heightStartForNavigation);
                    Animation.animationUpOfButton(buttonAddProduct,heightStartForButton);
                }
            }
        };
        listProduct.addOnScrollListener(scrollListener);
        listTarget.addOnScrollListener(scrollListener);
    }
}