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
import com.santao.bullfight.model.Article;
import com.santao.bullfight.model.Commet;
import com.santao.bullfight.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by goddie on 16/4/13.
 */
public class CommetListAdapter extends  BaseRecyclerViewAdapter {
    private Context mContext;

    private int style;

    public CommetListAdapter(Context context) {
        this.mContext = context;
        setArrayList(new ArrayList<>());
    }


    public CommetListAdapter(Context context,int style) {
        this.mContext = context;
        setArrayList(new ArrayList<>());
        this.style = style;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Commet entity = (Commet) getArrayList().get(position);


        if(!HttpUtil.isNullOrEmpty( entity.getFrom().getAvatar()))
        {
            Picasso.with(mContext).load(HttpUtil.BASE_URL + entity.getFrom().getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder)
                    .into(itemViewHolder.img1);
        }


        itemViewHolder.txt1.setText(entity.getFrom().getNickname());
        itemViewHolder.txt2.setText(HttpUtil.getDate(entity.getCreatedDate()));
        itemViewHolder.txt3.setText(entity.getContent());
        itemViewHolder.itemView.setTag(entity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {

            final View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_commet, parent, false);
            view.setOnClickListener(this);

            if(style==1)
            {
                view.setBackground(mContext.getResources().getDrawable(R.color.colorAppBgLight));
            }

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

        @Bind(R.id.txt1)
        TextView txt1;

        @Bind(R.id.txt2)
        TextView txt2;

        @Bind(R.id.txt3)
        TextView txt3;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
