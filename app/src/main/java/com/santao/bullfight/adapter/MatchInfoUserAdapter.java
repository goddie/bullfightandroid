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
import com.santao.bullfight.fragment.MatchInfoFragment;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MatchInfoUserAdapter extends BaseRecyclerViewAdapter {

    private Context mContext;

    public MatchInfoUserAdapter(Context context) {
        this.mContext = context;
        setArrayList(new ArrayList<>());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        ArrayList<Object> arr = (ArrayList<Object>)getArrayList().get(position);

        User host = (User) arr.get(0);

        itemViewHolder.txt1.setText(host.getNickname());

        if(!HttpUtil.isNullOrEmpty(host.getAvatar()))
        {
            Picasso.with(mContext).load(HttpUtil.BASE_URL + host.getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder).into(itemViewHolder.img1);
        }else
        {
            Picasso.with(mContext).load(R.mipmap.holder).transform(new CircleTransform())
                    .into(itemViewHolder.img1);
        }


        if(arr.size()>1&&arr.get(1)!=null)
        {
            User guest = (User) arr.get(1);

            itemViewHolder.txt2.setText(guest.getNickname());

            if(!HttpUtil.isNullOrEmpty(host.getAvatar()))
            {
                Picasso.with(mContext).load(HttpUtil.BASE_URL + guest.getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder).into(itemViewHolder.img2);
            }else
            {
                Picasso.with(mContext).load(R.mipmap.holder).transform(new CircleTransform())
                        .into(itemViewHolder.img2);
            }
        }


        itemViewHolder.itemView.setTag(arr);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {

            final View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_user_data, parent, false);
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
