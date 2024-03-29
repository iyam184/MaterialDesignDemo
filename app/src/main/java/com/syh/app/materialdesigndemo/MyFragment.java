package com.syh.app.materialdesigndemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syh.app.materialdesigndemo.adapter.MyRecyclerViewAdapter;
import com.syh.app.materialdesigndemo.adapter.MyStaggeredViewAdapter;
import com.syh.app.materialdesigndemo.util.SnackbarUtil;

/**
 * Created by hasee on 2018/2/23.
 */

public class MyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MyRecyclerViewAdapter.OnItemClickListener, MyStaggeredViewAdapter.OnItemClickListener {

    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyRecyclerViewAdapter mRecyclerViewAdapter;
    private MyStaggeredViewAdapter mStaggeredAdapter;

    private static final int VERTICAL_LIST = 0;
    private static final int HORIZONTAL_LIST = 1;
    private static final int VERTICAL_GRID = 2;
    private static final int HORIZONTAL_GRID = 3;
    private static final int STAGGERED_GRID = 4;

    private static final int SPAN_COUNT = 2;
    private int flag = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSwipeRefreshLayout = mView.findViewById(R.id.id_swipeRefreshLayout);
        mRecyclerView = mView.findViewById(R.id.id_recyclerView);

        flag = (int) getArguments().get("flag");

        configRecyclerView();

        // 刷新时，指示器旋转后变化的颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    private void configRecyclerView() {
        switch (flag) {
            case VERTICAL_LIST:
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                break;
            case VERTICAL_GRID:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_LIST:
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                break;
            case HORIZONTAL_GRID:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.HORIZONTAL, false);
                break;
            case STAGGERED_GRID:
                mLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
                break;
        }


        if (flag != STAGGERED_GRID) {
            mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity());
            mRecyclerViewAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
        } else {
            mStaggeredAdapter = new MyStaggeredViewAdapter(getActivity());
            mStaggeredAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mStaggeredAdapter);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onRefresh() {
// 刷新时模拟数据的变化
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                int temp = (int) (Math.random() * 10);
                if (flag != STAGGERED_GRID) {
                    mRecyclerViewAdapter.mDatas.add(0, "new" + temp);
                    mRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    mStaggeredAdapter.mDatas.add(0, "new" + temp);
                    mStaggeredAdapter.mHeights.add(0, (int) (Math.random() * 300) + 200);
                    mStaggeredAdapter.notifyDataSetChanged();
                }
            }
        }, 1000);
    }

    @Override
    public void onItemClick(View view, int position, String contentMsg) {
        SnackbarUtil.show(view,contentMsg+" onItemClick "+position,0);
    }

    @Override
    public void onItemLongClick(View view, int position, String contentMsg) {
        SnackbarUtil.show(view,contentMsg+" onItemLongClick "+position,0);
    }
}
