package com.project.shared_card.activity.main_screen.check;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.project.shared_card.activity.main_screen.check.tabs.current.CurrentListFragment;
import com.project.shared_card.activity.main_screen.check.tabs.target.TargetListFragment;

public class AdapterForPage extends FragmentStateAdapter {
    public AdapterForPage(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CurrentListFragment();
            case 1:
                return new TargetListFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
