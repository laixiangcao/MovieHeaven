package com.jiumeng.movieheaven2.fragment.tabhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.activity.BlankActivity;
import com.jiumeng.movieheaven2.engine.UserManager;
import com.jiumeng.movieheaven2.entity.UserEntity;
import com.jiumeng.movieheaven2.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
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
    @BindView(R.id.userName)
    TextView userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_information, null);
        ButterKnife.bind(this, rootView);
        setUserInfo();
        return rootView;
    }

    private void setUserInfo() {
        UserManager userManager = UserManager.getInstance();
        if (userManager.getLoginStatus()) {
            UserEntity user = userManager.getCurrentUser();
            userName.setText(user.getUsername());
        } else {
            userName.setText("点击头像登入");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
    }

    @OnClick({R.id.profile_image, R.id.my_msg, R.id.my_fav, R.id.recommend, R.id.feedback, R.id.setting, R.id.edit_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                userLogin();
                break;
            case R.id.my_msg:
                break;
            case R.id.my_fav:
                break;
            case R.id.recommend:
                recommend();
                break;
            case R.id.feedback:
                Intent intent = new Intent(getActivity(), BlankActivity.class);
                intent.putExtra("fragmentType", BlankActivity.FRAGMENT_TYPE_FEEDBACK);
                getContext().startActivity(intent);
                break;
            case R.id.setting:
                Intent mIntent = new Intent(getActivity(), BlankActivity.class);
                mIntent.putExtra("fragmentType", BlankActivity.FRAGMENT_TYPE_SETTING);
                getContext().startActivity(mIntent);
                break;
            case R.id.edit_user:
                editUser();
                break;
        }
    }

    private void recommend() {
        String apkDownUrl="http://oaydggmwi.bkt.clouddn.com/com.jiumeng.movieheaven.apk";
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("电影天堂");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(apkDownUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("这个软件不错哟不错喔："+"\n下载链接："+apkDownUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(apkDownUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("这个软件不错哟");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(apkDownUrl);

        // 启动分享GUI
        oks.show(UIUtils.getContext());
    }

    private void editUser() {
        if (UserManager.getInstance().getLoginStatus()) {
            Intent mIntent = new Intent(getActivity(), BlankActivity.class);
            mIntent.putExtra("fragmentType", BlankActivity.FRAGMENT_TYPE_USER_INFO);
            getContext().startActivity(mIntent);
        } else {
            UIUtils.showToast("未登录状态");
            userLogin();
        }
    }

    private void userLogin() {
        if (!UserManager.getInstance().getLoginStatus()) {
            Intent intent = new Intent(getActivity(), BlankActivity.class);
            intent.putExtra("fragmentType", BlankActivity.FRAGMENT_TYPE_LOGIN);
            getContext().startActivity(intent);
        } else {
            UIUtils.showToast("您已登入");
        }

    }
}
