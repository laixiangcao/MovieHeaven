package com.jiumeng.movieheaven2.fragment.tabhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.activity.BlankActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * * tabhost标签中的 我的信息页面
 * Created by jiumeng on 2016/9/24.
 */
public class MyInfoFragment extends Fragment {
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.my_msg)
    LinearLayout myMsg;
    @BindView(R.id.my_fav)
    LinearLayout myFav;
    @BindView(R.id.recommend)
    LinearLayout recommend;
    @BindView(R.id.feedback)
    LinearLayout feedback;
    @BindView(R.id.setting)
    LinearLayout setting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_information, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.profile_image, R.id.my_msg, R.id.my_fav, R.id.recommend, R.id.feedback, R.id.setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                Intent intent = new Intent(getActivity(), BlankActivity.class);
                intent.putExtra("fragmentType",BlankActivity.FRAGMENT_TYPE_LOGIN);
                startActivity(intent);
                break;
            case R.id.my_msg:
                break;
            case R.id.my_fav:
                break;
            case R.id.recommend:
                break;
            case R.id.feedback:
                break;
            case R.id.setting:
                Intent mIntent = new Intent(getActivity(), BlankActivity.class);
                mIntent.putExtra("fragmentType",BlankActivity.FRAGMENT_TYPE_SETTING);
                startActivity(mIntent);
                break;
        }
    }
}
