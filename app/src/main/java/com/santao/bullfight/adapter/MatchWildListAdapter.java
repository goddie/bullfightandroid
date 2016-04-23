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
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MatchWildListAdapter extends BaseRecyclerViewAdapter {

    private Context mContext;

    public MatchWildListAdapter(Context context) {
        this.mContext = context;
        setArrayList(new ArrayList<Object>());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        MatchFight entity = (MatchFight) getArrayList().get(position);


        if(entity==null)
        {
            return;
        }

        itemViewHolder.txtTitle.setText("野球娱乐场");

        if(entity.getArena()!=null)
        {
            itemViewHolder.txtArena.setText(entity.getArena().getName().toString());
        }

        itemViewHolder.txtDate.setText( HttpUtil.getDate(entity.getStart()) );


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


        itemViewHolder.itemView.setTag(entity);

        //Log.d("",league.getId().toString());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_matchwild, parent, false);
        view.setOnClickListener(this);

        return new ItemViewHolder(view);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {




        @Bind(R.id.match_title)
        TextView txtTitle;

        @Bind(R.id.match_date)
        TextView txtDate;

        @Bind(R.id.match_arena)
        TextView txtArena;

        @Bind(R.id.txtTop)
        TextView txtTop;


        @Bind(R.id.imgTop)
        ImageView imgTop;



        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
