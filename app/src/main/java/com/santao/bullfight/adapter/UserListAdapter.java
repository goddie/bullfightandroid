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
import com.santao.bullfight.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by goddie on 16/3/14.
 */
public class UserListAdapter  extends BaseRecyclerViewAdapter{

    private Context mContext;

    private int type = 0;

    public UserListAdapter(Context context) {
        this.mContext = context;
        setArrayList(new ArrayList<>());
    }

    public UserListAdapter(Context context,int type) {
        this.mContext = context;
        setArrayList(new ArrayList<>());
        this.type = type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        User entity = (User) getArrayList().get(position);


        Picasso.with(mContext).load(HttpUtil.BASE_URL + entity.getAvatar()).placeholder(R.mipmap.holder)
                .into(itemViewHolder.img);

        itemViewHolder.name.setText(entity.getNickname());
        itemViewHolder.position.setText(entity.getPosition());

        if((int)entity.getHeight()!=0)
        {
            itemViewHolder.height.setText("身高: " + (int)entity.getHeight() + " cm");
        }

        if((int)entity.getHeight()!=0)
        {
            itemViewHolder.weight.setText("体重: "+ (int)entity.getWeight()+" kg");
        }

        itemViewHolder.itemView.setTag(entity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_user, parent, false);
        view.setOnClickListener(this);

        if(type==1)
        {
            view.setBackground(mContext.getResources().getDrawable(R.color.colorAppBgLight));
        }

        return new ItemViewHolder(view);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img)
        ImageView img;

        @Bind(R.id.name)
        TextView name;

        @Bind(R.id.position)
        TextView position;

        @Bind(R.id.weight)
        TextView weight;

        @Bind(R.id.height)
        TextView height;


        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
