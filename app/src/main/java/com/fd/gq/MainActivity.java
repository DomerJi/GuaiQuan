package com.fd.gq;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.ArraySet;
import android.util.SparseArray;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fd.gq.base.BaseActivity;
import com.fd.gq.fragments.AmuseFragment;
import com.fd.gq.fragments.HomeFragment;
import com.fd.gq.fragments.MessageFragment;
import com.fd.gq.fragments.MyFragment;
import com.fd.gq.fragments.NearByFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ArrayList<Fragment> fragments;
    private ViewPager viewPager;
    private RadioGroup bottomBar;
    private TextView title_type2;
    private View title_type1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Toolbar toolbar  = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        bottomBar = (RadioGroup) findViewById(R.id.raidogroup_bottombar);
        bottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int size = group.getChildCount();
                for (int i = 0;i < size;i++){
                    if(group.getChildAt(i).getId()==checkedId){
                        viewPager.setCurrentItem(i,false);
                        setHomeTitle(i);
                        break;
                    }
                }
            }
        });
        initViewPager();
        initTitle();
    }

    private void initTitle() {
        title_type1 = findViewById(R.id.home_home);
        title_type2 = (TextView) findViewById(R.id.home_title);
    }

    private void initViewPager() {

        viewPager = (ViewPager) findViewById(R.id.viewPager_home);
        fragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        fragments.add(homeFragment);
        NearByFragment nearByFragment = new NearByFragment();
        fragments.add(nearByFragment);
        AmuseFragment amuseFragment = new AmuseFragment();
        fragments.add(amuseFragment);
        MessageFragment messageFragment = new MessageFragment();
        fragments.add(messageFragment);
        MyFragment myFragment = new MyFragment();
        fragments.add(myFragment);
        viewPager.setAdapter(new HomeAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(new PagerListener());
    }

    class HomeAdapter extends FragmentPagerAdapter{

        public HomeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    class PagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ((RadioButton)bottomBar.getChildAt(position)).setChecked(true);
            setHomeTitle(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void setHomeTitle(int position){

        if(position==0){
            title_type1.setVisibility(View.VISIBLE);
            title_type2.setVisibility(View.GONE);
        }else{
            title_type1.setVisibility(View.GONE);
            title_type2.setVisibility(View.VISIBLE);
            switch (position){
                case 1:
                    title_type2.setText(getString(R.string.title_nearby));
                    break;
                case 2:
                    title_type2.setText(getString(R.string.title_amuse));
                    break;
                case 3:
                    title_type2.setText(getString(R.string.title_message));
                    break;
                case 4:
                    title_type2.setText(getString(R.string.title_personal));
                    break;

            }
        }
    }


}
