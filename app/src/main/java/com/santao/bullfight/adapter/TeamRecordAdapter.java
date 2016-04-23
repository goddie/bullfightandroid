package com.santao.bullfight.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.santao.bullfight.R;
import com.santao.bullfight.activity.TeamDetailActivity;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.event.TeamEvent;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.model.Team;
import com.santao.bullfight.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class TeamRecordAdapter extends BaseRecyclerViewAdapter {


    private Context mContext;

    public TeamRecordAdapter(Context context) {
        this.mContext = context;
        setArrayList(new ArrayList<Object>());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        MatchFight entity = (MatchFight) getArrayList().get(position);


        if(!HttpUtil.isNullOrEmpty(entity.getHost().getAvatar()))
        {
            Picasso.with(mContext).load(HttpUtil.BASE_URL + entity.getHost().getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder).into(itemViewHolder.img1);
        }else
        {
            Picasso.with(mContext).load(R.mipmap.holder).transform(new CircleTransform())
                    .into(itemViewHolder.img1);
        }





        if(null!=entity.getGuest())
        {
            if(!HttpUtil.isNullOrEmpty(entity.getGuest().getAvatar()))
            {
                Picasso.with(mContext).load(HttpUtil.BASE_URL + entity.getGuest().getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder).into(itemViewHolder.img2);
            }else
            {
                Picasso.with(mContext).load(R.mipmap.holder).transform(new CircleTransform())
                        .into(itemViewHolder.img2);
            }

            itemViewHolder.txtTeam2.setText(entity.getGuest().getName().toString());
            itemViewHolder.img2.setTag(entity.getGuest());


        }else
        {

            itemViewHolder.txtTeam2.setText("");
            itemViewHolder.img2.setTag(null);
        }


        if(entity.getArena()!=null)
        {
            itemViewHolder.txtTitle.setText(entity.getArena().getName().toString());
        }else
        {
            itemViewHolder.txtTitle.setText("");
        }




        //未接招
        if(entity.getStatus()==0)
        {
            itemViewHolder.txtTop.setText("未接招");
            itemViewHolder.imgTop.setImageResource(R.mipmap.shared_icon_badge_unknow);
            //itemViewHolder.txtScore.setVisibility(View.GONE);
            //itemViewHolder.txtTeam2.setVisibility(View.GONE);

            Picasso.with(mContext).load(R.mipmap.feed_team_unknown).transform(new CircleTransform())
                    .into(itemViewHolder.img2);

        }

        if(entity.getStatus()==1)//未开始
        {

            itemViewHolder.txtTop.setText("未开始");
            itemViewHolder.imgTop.setImageResource(R.mipmap.shared_icon_badge_active);
            // itemViewHolder.txtScore.setVisibility(View.VISIBLE);
            //itemViewHolder.txtTeam2.setVisibility(View.VISIBLE);


        }

        if(entity.getStatus()==2)//已结束
        {

            itemViewHolder.txtTop.setText("已结束");
            itemViewHolder.imgTop.setImageResource(R.mipmap.shared_icon_badge_inactive);
            itemViewHolder.txtScore.setVisibility(View.VISIBLE);
            itemViewHolder.txtTeam2.setVisibility(View.VISIBLE);
        }

        itemViewHolder.txtScore.setText(String.valueOf((int) entity.getHostScore())+":"+String.valueOf((int)entity.getGuestScore()));
        itemViewHolder.txtDate.setText( HttpUtil.getDate(entity.getStart()) );

        itemViewHolder.img1.setTag(entity.getHost());
        itemViewHolder.txtTeam1.setText(entity.getHost().getName().toString());

        itemViewHolder.txtNo1.setText(String.valueOf(entity.getTeamSize()));
        itemViewHolder.txtNo2.setText(String.valueOf(entity.getTeamSize()));

        itemViewHolder.itemView.setTag(entity);

        //Log.d("",league.getId().toString());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_matchfight, parent, false);
        view.setOnClickListener(this);


        view.setBackgroundColor(mContext.getResources().getColor(R.color.colorAppBgLight));

        final ImageView img1 = (ImageView)view.findViewById(R.id.img1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TeamEvent event = new TeamEvent(TeamEvent.TEAM_DETAIL);
                event.setData(img1.getTag());
                EventBus.getDefault().post(event);
            }
        });


        final ImageView img2 = (ImageView)view.findViewById(R.id.img2);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TeamEvent event = new TeamEvent(TeamEvent.TEAM_DETAIL);
                event.setData(img2.getTag());
                EventBus.getDefault().post(event);
            }
        });



        return new ItemViewHolder(view);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.txtTeam1)
        TextView txtTeam1;


        @Bind(R.id.txtTeam2)
        TextView txtTeam2;


        @Bind(R.id.txtTitle)
        TextView txtTitle;

        @Bind(R.id.txtDate)
        TextView txtDate;

        @Bind(R.id.txtScore)
        TextView txtScore;

        @Bind(R.id.txtTop)
        TextView txtTop;

        @Bind(R.id.txtNo1)
        TextView txtNo1;

        @Bind(R.id.txtNo2)
        TextView txtNo2;

        @Bind(R.id.imgTop)
        ImageView imgTop;

        @Bind(R.id.img1)
        ImageView img1;

        @Bind(R.id.img2)
        ImageView img2;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }


}
