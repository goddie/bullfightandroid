package com.santao.bullfight.adapter;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.santao.bullfight.R;
import com.santao.bullfight.fragment.MatchInfoFragment;
import com.santao.bullfight.model.Arena;
import com.santao.bullfight.model.DataUser;
import com.santao.bullfight.model.MatchFight;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MatchInfoAdapter extends BaseRecyclerViewAdapter {

    private Context mContext;

    public MatchInfoAdapter(Context context) {
        this.mContext = context;
        setArrayList(new ArrayList<>());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        MatchInfoFragment.MatchInfoData entity = (MatchInfoFragment.MatchInfoData) getArrayList().get(position);

        itemViewHolder.img1.setImageResource(entity.getImgId());
        itemViewHolder.txt1.setText(entity.getText());


        itemViewHolder.itemView.setTag(entity);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {

            final View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_simple_data, parent, false);
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

        @Bind(R.id.txt1)
        TextView txt1;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }



}
