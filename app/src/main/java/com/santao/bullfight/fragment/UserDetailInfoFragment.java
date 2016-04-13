package com.santao.bullfight.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.santao.bullfight.R;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailInfoFragment extends BaseFragment {



    @Bind(R.id.txt1)
    TextView txt1;

    @Bind(R.id.txt2)
    TextView txt2;

    @Bind(R.id.txt3)
    TextView txt3;

    @Bind(R.id.txt4)
    TextView txt4;

    @Bind(R.id.img1)
    ImageView img1;

    private User user ;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_user_detail_info;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        Bundle bundle =getActivity().getIntent().getExtras();

        if (bundle != null && bundle.containsKey("user")) {
            user = (User)bundle.getSerializable("user");
        }


        if(user.getHeight()>0)
        {
            txt1.setText(user.getHeight()+" cm");
        }

        if(user.getWeight()>0)
        {
            txt2.setText(user.getWeight()+" kg");
        }


        txt3.setText(HttpUtil.getDate(user.getBirthday()));
        txt4.setText(user.getPosition());


        if(!HttpUtil.isNullOrEmpty(user.getAvatar()))
        {
            Picasso.with(getActivity()).load(HttpUtil.BASE_URL + user.getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder).into(img1);
        }else
        {
            Picasso.with(getActivity()).load(R.mipmap.holder).transform(new CircleTransform())
                    .into(img1);
        }

    }
}
