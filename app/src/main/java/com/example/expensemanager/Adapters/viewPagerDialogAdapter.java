package com.example.expensemanager.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.expensemanager.Views.Fragments.calender;
import com.example.expensemanager.Views.Fragments.daily;
import com.example.expensemanager.Views.Fragments.monthly;
import com.example.expensemanager.Views.Fragments.notes;

public class viewPagerDialogAdapter extends FragmentPagerAdapter {
    public viewPagerDialogAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

//    @NonNull
    @Override
    public Fragment getItem(int position) {
//        if(position==0){
//            return new daily();
//        }else{
//            return new monthly();
//        }
        switch(position){
            case 0: return new daily();
            case 1: return  new monthly();
            case 2: return new calender();
            case 3: return new notes();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

//    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        if(position==0){
//            return "Daily";
//        }else{
//            return "Monthly";
//        }
        switch (position){
        case 0: return "daily";
        case 1: return "monthly";
        case 2: return "calender";
        case 3: return "notes";
        default: return null;
      }
    }
}
