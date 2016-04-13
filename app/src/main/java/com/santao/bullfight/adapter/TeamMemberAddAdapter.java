package com.santao.bullfight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.santao.bullfight.R;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.event.MemberEvent;
import com.santao.bullfight.event.TeamEvent;
import com.santao.bullfight.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by goddie on 16/4/11.
 */
public class TeamMemberAddAdapter extends BaseRecyclerViewAdapter {

    private Context mContext;



    public TeamMemberAddAdapter(Context context) {
        this.mContext = context;
        setArrayList(new ArrayList<>());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        User entity = (User) getArrayList().get(position);


        Picasso.with(mContext).load(HttpUtil.BASE_URL + entity.getAvatar()).placeholder(R.mipmap.holder)
                .into(itemViewHolder.img);

        itemViewHolder.name.setText(entity.getNickname());
        itemViewHolder.position.setText(entity.getPosition());
        itemViewHolder.btn1.setText("邀请入队");
        itemViewHolder.btn1.setTag(entity);


        itemViewHolder.itemView.setTag(entity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_team_member, parent, false);
        view.setOnClickListener(this);

        Button b = (Button)view.findViewById(R.id.btn1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberEvent event = new MemberEvent(MemberEvent.MEMBER_JOIN);
                event.setData(v.getTag());
                EventBus.getDefault().post(event);
            }
        });

        return new ItemViewHolder(view);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img)
        ImageView img;

        @Bind(R.id.name)
        TextView name;

        @Bind(R.id.position)
        TextView position;

        @Bind(R.id.btn1)
        Button btn1;


        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}

