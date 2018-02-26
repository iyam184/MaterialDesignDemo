package com.syh.app.materialdesigndemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syh.app.materialdesigndemo.R;

import java.util.ArrayList;
import java.util.List;

public class MyStaggeredViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    public Context mContext;
    public List<String> mDatas;
    public List<Integer> mHeights;//item高度
    public LayoutInflater mLayoutInflater;//布局引入器

    public MyStaggeredViewAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mDatas = new ArrayList<>();
        this.mHeights = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++) {
            mDatas.add((char) i + "");
        }
        for (int i = 0; i < mDatas.size(); i++) {
            mHeights.add((int) (Math.random() * 300) + 200);
        }
    }

    /**
     * 创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_recyclerview,parent,false);
        MyRecyclerViewAdapter.ViewHolder viewHolder = new MyRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    /**
     * 绑定ViewHolder,初始化item数据样式等。
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position,mDatas.get(position));
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position,mDatas.get(position));
                    return true;
                }
            });
        }

        ViewGroup.LayoutParams mLayoutParams = holder.mTextView.getLayoutParams();
        mLayoutParams.height = mHeights.get(position);
        holder.mTextView.setLayoutParams(mLayoutParams);
        holder.mTextView.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position,String contentMsg);

        void onItemLongClick(View view, int position,String contentMsg);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


}
