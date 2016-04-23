package com.santao.bullfight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santao.bullfight.R;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.event.TeamEvent;
import com.santao.bullfight.event.UserEvent;
import com.santao.bullfight.model.MatchDataUser;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class MatchInfoUserFinishAdapter extends BaseRecyclerViewAdapter {

    private Context mContext;

    public MatchInfoUserFinishAdapter(Context context) {
        this.mContext = context;
        setArrayList(new ArrayList<Object>());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        ArrayList<Object> arr = (ArrayList<Object>)getArrayList().get(position);


        DecimalFormat decimalFormat=new DecimalFormat("0");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        if(arr.get(0)!=null)
        {
            itemViewHolder.left.setVisibility(View.VISIBLE);

            MatchDataUser entity = (MatchDataUser) arr.get(0);

            itemViewHolder.name1.setText(entity.getUser().getNickname());

            if(!HttpUtil.isNullOrEmpty(entity.getUser().getAvatar()))
            {
                Picasso.with(mContext).load(HttpUtil.BASE_URL + entity.getUser().getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder).into(itemViewHolder.img1);
            }else
            {
                Picasso.with(mContext).load(R.mipmap.holder).transform(new CircleTransform())
                        .into(itemViewHolder.img1);
            }


            itemViewHolder.img1.setTag(entity.getUser());


            itemViewHolder.txt11.setText(decimalFormat.format(entity.getScoring())+"");
            itemViewHolder.txt12.setText(decimalFormat.format(entity.getGoal())+"/"+decimalFormat.format(entity.getShot()));
            itemViewHolder.txt21.setText(decimalFormat.format(entity.getThreeGoal())+"/"+decimalFormat.format(entity.getThreeShot()));
            itemViewHolder.txt22.setText(decimalFormat.format(entity.getFreeGoal())+"/"+decimalFormat.format(entity.getFree()));
            itemViewHolder.txt31.setText(decimalFormat.format(entity.getRebound())+"");
            itemViewHolder.txt32.setText(decimalFormat.format(entity.getAssist())+"");
            itemViewHolder.txt41.setText(decimalFormat.format(entity.getBlock())+"");
            itemViewHolder.txt42.setText(decimalFormat.format(entity.getSteal())+"");
            itemViewHolder.txt51.setText(decimalFormat.format(entity.getFoul())+"");
            itemViewHolder.txt52.setText(decimalFormat.format(entity.getTurnover())+"");


        }else
        {
            itemViewHolder.left.setVisibility(View.INVISIBLE);
        }

//        else
//        {
//            itemViewHolder.txt11.setText("");
//            itemViewHolder.txt12.setText("");
//            itemViewHolder.txt21.setText("");
//            itemViewHolder.txt22.setText("");
//            itemViewHolder.txt31.setText("");
//            itemViewHolder.txt32.setText("");
//            itemViewHolder.txt41.setText("");
//            itemViewHolder.txt42.setText("");
//            itemViewHolder.txt51.setText("");
//            itemViewHolder.txt52.setText("");
//
//            itemViewHolder.img1.setImageBitmap(null);
//            itemViewHolder.name1.setText("");
//
//
//            itemViewHolder.lb1.setText("");
//            itemViewHolder.lb2.setText("");
//            itemViewHolder.lb3.setText("");
//            itemViewHolder.lb4.setText("");
//            itemViewHolder.lb5.setText("");
//            itemViewHolder.lb6.setText("");
//            itemViewHolder.lb7.setText("");
//            itemViewHolder.lb8.setText("");
//            itemViewHolder.lb9.setText("");
//            itemViewHolder.lb10.setText("");
//
//
//        }


        if(arr.get(1)!=null)
        {

            itemViewHolder.right.setVisibility(View.VISIBLE);

            MatchDataUser entity = (MatchDataUser) arr.get(1);

            itemViewHolder.name2.setText(entity.getUser().getNickname());

            if(!HttpUtil.isNullOrEmpty(entity.getUser().getAvatar()))
            {
                Picasso.with(mContext).load(HttpUtil.BASE_URL + entity.getUser().getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder).into(itemViewHolder.img2);
            }else
            {
                Picasso.with(mContext).load(R.mipmap.holder).transform(new CircleTransform())
                        .into(itemViewHolder.img2);
            }

            itemViewHolder.img2.setTag(entity.getUser());

            itemViewHolder.txt13.setText(decimalFormat.format(entity.getScoring())+"");
            itemViewHolder.txt14.setText(decimalFormat.format(entity.getGoal())+"/"+decimalFormat.format(entity.getShot()));
            itemViewHolder.txt23.setText(decimalFormat.format(entity.getThreeGoal())+"/"+decimalFormat.format(entity.getThreeShot()));
            itemViewHolder.txt24.setText(decimalFormat.format(entity.getFreeGoal())+"/"+decimalFormat.format(entity.getFree()));
            itemViewHolder.txt33.setText(decimalFormat.format(entity.getRebound())+"");
            itemViewHolder.txt34.setText(decimalFormat.format(entity.getAssist())+"");
            itemViewHolder.txt43.setText(decimalFormat.format(entity.getBlock())+"");
            itemViewHolder.txt44.setText(decimalFormat.format(entity.getSteal())+"");
            itemViewHolder.txt53.setText(decimalFormat.format(entity.getFoul())+"");
            itemViewHolder.txt54.setText(decimalFormat.format(entity.getTurnover()) + "");



        }else
        {
            itemViewHolder.right.setVisibility(View.INVISIBLE);
        }

//        else
//        {
//            itemViewHolder.txt13.setText("");
//            itemViewHolder.txt14.setText("");
//            itemViewHolder.txt23.setText("");
//            itemViewHolder.txt24.setText("");
//            itemViewHolder.txt33.setText("");
//            itemViewHolder.txt34.setText("");
//            itemViewHolder.txt43.setText("");
//            itemViewHolder.txt44.setText("");
//            itemViewHolder.txt53.setText("");
//            itemViewHolder.txt54.setText("");
//
//            itemViewHolder.img2.setImageBitmap(null);
//            itemViewHolder.name2.setText("");
//
//            itemViewHolder.lc1.setText("");
//            itemViewHolder.lc2.setText("");
//            itemViewHolder.lc3.setText("");
//            itemViewHolder.lc4.setText("");
//            itemViewHolder.lc5.setText("");
//            itemViewHolder.lc6.setText("");
//            itemViewHolder.lc7.setText("");
//            itemViewHolder.lc8.setText("");
//            itemViewHolder.lc9.setText("");
//            itemViewHolder.lc10.setText("");
//
//
//
//        }


        itemViewHolder.itemView.setTag(arr);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {

            final View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_user_data_finish, parent, false);
            view.setOnClickListener(this);

            final ImageView img1 = (ImageView)view.findViewById(R.id.img1);
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    UserEvent event = new UserEvent(UserEvent.USER_DETAIL);
                    event.setData(img1.getTag());
                    EventBus.getDefault().post(event);
                }
            });


            final ImageView img2 = (ImageView)view.findViewById(R.id.img2);
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    UserEvent event = new UserEvent(UserEvent.USER_DETAIL);
                    event.setData(img2.getTag());
                    EventBus.getDefault().post(event);
                }
            });


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

        @Bind(R.id.name1)
        TextView name1;

        @Bind(R.id.name2)
        TextView name2;


        @Bind(R.id.txt11)
        TextView txt11;

        @Bind(R.id.txt12)
        TextView txt12;

        @Bind(R.id.txt13)
        TextView txt13;

        @Bind(R.id.txt14)
        TextView txt14;


        @Bind(R.id.txt21)
        TextView txt21;

        @Bind(R.id.txt22)
        TextView txt22;

        @Bind(R.id.txt23)
        TextView txt23;

        @Bind(R.id.txt24)
        TextView txt24;


        @Bind(R.id.txt31)
        TextView txt31;

        @Bind(R.id.txt32)
        TextView txt32;

        @Bind(R.id.txt33)
        TextView txt33;

        @Bind(R.id.txt34)
        TextView txt34;


        @Bind(R.id.txt41)
        TextView txt41;

        @Bind(R.id.txt42)
        TextView txt42;

        @Bind(R.id.txt43)
        TextView txt43;

        @Bind(R.id.txt44)
        TextView txt44;


        @Bind(R.id.txt51)
        TextView txt51;

        @Bind(R.id.txt52)
        TextView txt52;

        @Bind(R.id.txt53)
        TextView txt53;

        @Bind(R.id.txt54)
        TextView txt54;

        @Bind(R.id.left)
        LinearLayout left;

        @Bind(R.id.right)
        LinearLayout right;


        @Bind(R.id.lb1)
        TextView lb1;
        @Bind(R.id.lb2)
        TextView lb2;
        @Bind(R.id.lb3)
        TextView lb3;
        @Bind(R.id.lb4)
        TextView lb4;
        @Bind(R.id.lb5)
        TextView lb5;
        @Bind(R.id.lb6)
        TextView lb6;
        @Bind(R.id.lb7)
        TextView lb7;
        @Bind(R.id.lb8)
        TextView lb8;
        @Bind(R.id.lb9)
        TextView lb9;
        @Bind(R.id.lb10)
        TextView lb10;


        @Bind(R.id.lc1)
        TextView lc1;
        @Bind(R.id.lc2)
        TextView lc2;
        @Bind(R.id.lc3)
        TextView lc3;
        @Bind(R.id.lc4)
        TextView lc4;
        @Bind(R.id.lc5)
        TextView lc5;
        @Bind(R.id.lc6)
        TextView lc6;
        @Bind(R.id.lc7)
        TextView lc7;
        @Bind(R.id.lc8)
        TextView lc8;
        @Bind(R.id.lc9)
        TextView lc9;
        @Bind(R.id.lc10)
        TextView lc10;



        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
