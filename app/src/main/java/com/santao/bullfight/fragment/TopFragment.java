package com.santao.bullfight.fragment;


import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.santao.bullfight.R;

import butterknife.Bind;


public class TopFragment extends BaseFragment {


    public  interface  TopListener
    {

        void onTopFinish();
        void onTopRightClick();
        void onTopLeftClick();
    }


    @Bind(R.id.txtTitle)
    TextView txtTitle;

    @Bind(R.id.txtLeft)
    TextView txtLeft;

    @Bind(R.id.txtRight)
    TextView txtRight;

    @Bind(R.id.imgLeft)
    ImageView imgLeft;

    @Bind(R.id.imgRight)
    ImageView imgRight;

    private TopListener topListener;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_top;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        //Log.d("", "TopFragment initAllMembersView:");
        try {
            topListener=(TopListener)getActivity();
            topListener.onTopFinish();
        }catch (Exception e)
        {
            Log.e(getString(R.string.app_name),e.getMessage());
        }
    }



    public void setTitle(String title)
    {

        txtTitle.setText(title);
    }

    public void setTxtRight(String txt)
    {
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText(txt);
        txtRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(topListener!=null)
                {
                    topListener.onTopRightClick();

                }

            }
        });
    }


}