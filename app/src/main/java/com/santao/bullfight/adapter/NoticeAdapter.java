package com.santao.bullfight.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.santao.bullfight.R;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.Message;
import com.santao.bullfight.model.Team;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NoticeAdapter extends  BaseRecyclerViewAdapter{

    private Context mContext;

    public NoticeAdapter(Context context) {
        this.mContext = context;
        setArrayList(new ArrayList<>());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Message entity = (Message) getArrayList().get(position);

        if (entity.getStatus()==1)
        {
            itemViewHolder.img1.setVisibility(View.VISIBLE);
        }else
        {
            itemViewHolder.img1.setVisibility(View.INVISIBLE);
        }

        itemViewHolder.txt1.setText(entity.getTitle());
        itemViewHolder.txt2.setText(HttpUtil.getDate(entity.getCreatedDate()));


        itemViewHolder.itemView.setTag(entity);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {

            final View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_notice, parent, false);
            view.setOnClickListener(this);
            return new ItemViewHolder(view);

        } else if (viewType == TYPE_FOOTER) {

            final View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_loadmore, parent, false);

            setFooter(view);

            return new FootViewHolder(getFooter());
        }

        return null;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img1)
        ImageView img1;

        @Bind(R.id.img2)
        ImageView img2;

        @Bind(R.id.txt1)
        TextView txt1;

        @Bind(R.id.txt2)
        TextView txt2;


        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
