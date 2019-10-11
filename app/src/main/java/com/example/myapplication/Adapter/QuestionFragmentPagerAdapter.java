package com.example.myapplication.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class QuestionFragmentPagerAdapter extends FragmentPagerAdapter {
    public QuestionFragmentPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position){
        //return SurveyFragment.newInstance("aaaa", 2, "a");
        return null;
    }

    @Override
    public int getCount(){
        return 60;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return "ページ" + (position + 1);
    }
 }
