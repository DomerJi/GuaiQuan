package com.fd.gq.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fd.gq.R;
import com.fd.gq.base.BaseFragment;
import com.fd.gq.util.Images;
import com.fd.gq.widget.CarouselViewPager;

/**
 * Created by admin on 2016/12/6.
 */

public class NearByFragment extends BaseFragment {

    private CarouselViewPager carouselViewPager;
    private CarouselViewPager carouselViewPager1;
    private CarouselViewPager carouselViewPager2;
    private CarouselViewPager carouselViewPager3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nearby,null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        carouselViewPager = (CarouselViewPager) getView().findViewById(R.id.carouseViewpager);
        carouselViewPager.setDates(Images.imageUrls);

        carouselViewPager1 = (CarouselViewPager) getView().findViewById(R.id.carouseViewpager1);
        carouselViewPager1.setDates(Images.imageUrls);

        carouselViewPager2 = (CarouselViewPager) getView().findViewById(R.id.carouseViewpager2);
        carouselViewPager2.setDates(Images.imageUrls);

        carouselViewPager3 = (CarouselViewPager) getView().findViewById(R.id.carouseViewpager3);
        carouselViewPager3.setDates(Images.imageUrls);
    }

    @Override
    public void onResume() {
        super.onResume();
        carouselViewPager.onResume();
        carouselViewPager1.onResume();
        carouselViewPager2.onResume();
        carouselViewPager3.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        carouselViewPager.onPause();
//        carouselViewPager1.onPause();
//        carouselViewPager2.onPause();
//        carouselViewPager3.onPause();
    }
}
