package com.fd.gq.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fd.gq.R;
import com.fd.gq.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by admin on 2016/12/6.
 */

public class HomeFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,null);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        XRecyclerView xRecyclerView = (XRecyclerView)getView().findViewById(R.id.recyclerview_home);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(manager);

    }
}
