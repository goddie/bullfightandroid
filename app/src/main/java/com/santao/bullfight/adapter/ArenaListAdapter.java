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
import com.santao.bullfight.model.Arena;
import com.santao.bullfight.model.Team;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by goddie on 16/4/1.
 */
public class ArenaListAdapter extends  BaseRecyclerViewAdapter {
    private Context mContext;

    public ArenaListAdapter(Context context) {
        this.mContext = context;
        setArrayList(new ArrayList<Object>());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Arena entity = (Arena) getArrayList().get(position);

        itemViewHolder.txtName.setText(entity.getName().toString());

        itemViewHolder.itemView.setTag(entity);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {

            final View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_arena, parent, false);
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

        @Bind(R.id.txtName)
        TextView txtName;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
