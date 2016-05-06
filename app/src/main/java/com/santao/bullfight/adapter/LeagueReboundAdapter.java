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
import com.santao.bullfight.event.UserEvent;
import com.santao.bullfight.model.DataUser;
import com.santao.bullfight.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class LeagueReboundAdapter extends BaseRecyclerViewAdapter {
    private Context mContext;

    public LeagueReboundAdapter(Context context) {
        this.mContext = context;
        setArrayList(new ArrayList<Object>());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        DataUser entity = (DataUser) getArrayList().get(position);


        if(entity==null)
        {
            return;
        }

        if(!HttpUtil.isNullOrEmpty(entity.getUser().getAvatar()))
        {
            Picasso.with(mContext).load(HttpUtil.BASE_URL +entity.getUser().getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder).into(itemViewHolder.img1);
        }else
        {
            Picasso.with(mContext).load(R.mipmap.holder).transform(new CircleTransform())
                    .into(itemViewHolder.img1);
        }

        itemViewHolder.txt2.setText(String.valueOf(entity.getPlays()));
        itemViewHolder.txt3.setText(String.valueOf(entity.getRebound()));
        itemViewHolder.txt4.setText(String.valueOf(entity.getAvgrebound()));
        itemViewHolder.txt5.setText(entity.getUser().getNickname());
        itemViewHolder.itemView.setTag(entity);

        //Log.d("",league.getId().toString());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_league_score, parent, false);
        view.setOnClickListener(this);


//        final ImageView img1 = (ImageView)view.findViewById(R.id.img1);
//        img1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                UserEvent event = new UserEvent(UserEvent.USER_DETAIL);
//                event.setData(img1.getTag());
//                EventBus.getDefault().post(event);
//            }
//        });

        return new ItemViewHolder(view);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.img1)
        ImageView img1;

        @Bind(R.id.txt2)
        TextView txt2;

        @Bind(R.id.txt3)
        TextView txt3;

        @Bind(R.id.txt4)
        TextView txt4;

        @Bind(R.id.txt5)
        TextView txt5;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
