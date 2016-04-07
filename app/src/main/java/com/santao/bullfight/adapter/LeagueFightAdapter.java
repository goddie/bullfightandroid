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
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LeagueFightAdapter extends BaseRecyclerViewAdapter {

    private Context mContext;

    public LeagueFightAdapter(Context context) {
        this.mContext = context;
        setArrayList(new ArrayList<>());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        MatchFight entity = (MatchFight) getArrayList().get(position);


        if(entity.getHost()!=null)
        {
            Picasso.with(mContext).load(HttpUtil.BASE_URL + entity.getHost().getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder)
                    .into(itemViewHolder.img1);
            itemViewHolder.team1.setText(entity.getHost().getName());
        }else
        {
            Picasso.with(mContext).load(R.mipmap.holder).transform(new CircleTransform())
                    .into(itemViewHolder.img1);
        }

        if(entity.getGuest()!=null)
        {
            Picasso.with(mContext).load(HttpUtil.BASE_URL + entity.getGuest().getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder)
                    .into(itemViewHolder.img2);
            itemViewHolder.team2.setText(entity.getGuest().getName());

        }else
        {
            Picasso.with(mContext).load(R.mipmap.holder).transform(new CircleTransform())
                    .into(itemViewHolder.img2);
        }





        itemViewHolder.title.setText(entity.getArena().getAddress());
        itemViewHolder.date.setText(HttpUtil.getDate(entity.getStart()));

        itemViewHolder.itemView.setTag(entity.getId().toString());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_leaguefight, parent, false);
        view.setOnClickListener(this);

        return new ItemViewHolder(view);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img1)
        ImageView img1;

        @Bind(R.id.img2)
        ImageView img2;

        @Bind(R.id.title)
        TextView title;

        @Bind(R.id.txtDate)
        TextView date;


        @Bind(R.id.txtTeam1)
        TextView team1;


        @Bind(R.id.txtTeam2)
        TextView team2;



        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
