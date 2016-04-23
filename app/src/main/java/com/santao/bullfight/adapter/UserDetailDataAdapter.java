package com.santao.bullfight.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.santao.bullfight.R;
import com.santao.bullfight.fragment.MatchInfoFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserDetailDataAdapter extends BaseRecyclerViewAdapter {

    private Context mContext;

    public UserDetailDataAdapter(Context context) {
        this.mContext = context;
        setArrayList(new ArrayList<Object>());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        ArrayList<String> entity = (ArrayList<String>) getArrayList().get(position);


        itemViewHolder.txt1.setText(entity.get(0));
        itemViewHolder.txt2.setText(entity.get(1));

        itemViewHolder.itemView.setTag(entity);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {

            final View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_user_detail_data, parent, false);
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
