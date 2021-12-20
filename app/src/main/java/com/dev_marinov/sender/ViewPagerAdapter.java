package com.dev_marinov.sender;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

//    public ViewPagerAdapter(@NonNull Fragment fragment) {
//        super(fragment);
//    }
//
//    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
//        super(fragmentManager, lifecycle);
//    }
//
//    public void setAdapter(ViewPagerAdapter viewPagerAdapter) {
//    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new FragmentPageOne();
            default:
                return new FragmentPageTwo();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }



}
