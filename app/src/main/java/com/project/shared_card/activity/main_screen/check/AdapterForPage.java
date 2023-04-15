package com.project.shared_card.activity.main_screen.check;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.project.shared_card.activity.main_screen.check.tabs.CurrentListFragment;
import com.project.shared_card.activity.main_screen.check.tabs.TargetListFragment;

public class AdapterForPage extends FragmentStateAdapter {
    public AdapterForPage(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return CurrentListFragment.newInstance();
            case 1:
                return TargetListFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
