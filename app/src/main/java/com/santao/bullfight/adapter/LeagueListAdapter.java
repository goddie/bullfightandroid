package com.santao.bullfight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.R;
import com.santao.bullfight.model.League;
import com.santao.bullfight.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by goddie on 16/3/8.
 */
public class LeagueListAdapter extends BaseRecyclerViewAdapter {

    private Context mContext;

    public LeagueListAdapter(Context context) {
        this.mContext = context;
        setArrayList(new ArrayList<>());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        League entity = (League) getArrayList().get(position);


        Picasso.with(mContext).load(HttpUtil.BASE_URL + entity.getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder)
                .into(itemViewHolder.icon);


        //未开始
        if(entity.getStatus()==1)
        {

            itemViewHolder.txtTop.setText("未开始");
            itemViewHolder.imgTop.setImageResource(R.mipmap.shared_icon_badge_active);
        }


        //已结束
        if(entity.getStatus()==2)
        {

            itemViewHolder.txtTop.setText("已结束");
            itemViewHolder.imgTop.setImageResource(R.mipmap.shared_icon_badge_inactive);
        }

        itemViewHolder.matchTitle.setText(entity.getName());
        itemViewHolder.matchArena.setText(entity.getArena().getName());
        itemViewHolder.itemView.setTag(entity);



        //Log.d("",league.getId().toString());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_league, parent, false);
        view.setOnClickListener(this);

        return new ItemViewHolder(view);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imgTop)
        ImageView imgTop;

        @Bind(R.id.imageView2)
        ImageView icon;

        @Bind(R.id.match_title)
        TextView matchTitle;

        @Bind(R.id.match_arena)
        TextView matchArena;

        @Bind(R.id.txtTop)
        TextView txtTop;


        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }


}
