package com.santao.bullfight.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.santao.bullfight.R;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;
import com.santao.bullfight.widget.OnRecyclerViewItemLongClickListener;

import java.util.ArrayList;


public class BaseRecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,View.OnLongClickListener {

    private ArrayList<Object> arrayList;
    private View header;
    private View footer;

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_ITEM = 2;
    public static final int TYPE_FOOTER = 3;
    private int headerFlag = 0;
    private int footerFlag = 0;

    public BaseRecyclerViewAdapter() {
        setArrayList(new ArrayList<Object>());
    }

    public BaseRecyclerViewAdapter(View header) {
        setArrayList(new ArrayList<Object>());
        this.setHeader(header);

    }

    public Object getItem(int position) {
        return arrayList.get(position - getHeaderFlag() - getFooterFlag());
    }

    private OnRecyclerViewItemClickListener onItemClickListener = null;

    private OnRecyclerViewItemLongClickListener onItemLongClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener)
    {
        this.onItemLongClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return getArrayList().size() + getHeaderFlag() + getFooterFlag();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v,  v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (onItemLongClickListener != null) {
            onItemLongClickListener.onItemLongClick(v,  v.getTag());
        }
        return false;
    }



    @Override
    public int getItemViewType(int position) {
        if (getHeaderFlag() == 1 && position == 0) {
            return TYPE_HEADER;
        }
        if (getFooterFlag() == 1 && position == getItemCount()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }


    public void addArrayList(ArrayList<Object> list) {
        this.getArrayList().addAll(list);
        notifyDataSetChanged();
        //notifyItemRemoved(getItemCount());
    }

    public ArrayList<Object> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Object> arrayList) {
        this.arrayList = arrayList;
    }

    public View getHeader() {
        return header;
    }

    public void setHeader(View header) {
        this.header = header;
        setHeaderFlag(1);
    }

    public View getFooter() {
        return footer;
    }

    public void setFooter(View footer) {
        this.footer = footer;
        setFooterFlag(1);
    }

    public int getHeaderFlag() {
        return headerFlag;
    }

    public void setHeaderFlag(int headerFlag) {
        this.headerFlag = headerFlag;
    }

    public int getFooterFlag() {
        return footerFlag;
    }

    public void setFooterFlag(int footerFlag) {
        this.footerFlag = footerFlag;
    }


    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }

    public void clear() {
        this.getArrayList().clear();
        notifyDataSetChanged();
    }
}
