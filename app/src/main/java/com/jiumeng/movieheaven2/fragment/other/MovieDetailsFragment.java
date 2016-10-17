package com.jiumeng.movieheaven2.fragment.other;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.engine.DownloadManager;
import com.jiumeng.movieheaven2.engine.GlideRoundTransform;
import com.jiumeng.movieheaven2.entity.MovieEntity;
import com.jiumeng.movieheaven2.fragment.base.BaseLoadFragment;
import com.jiumeng.movieheaven2.network.MyStringCallback;
import com.jiumeng.movieheaven2.network.NetWorkApi;
import com.jiumeng.movieheaven2.provider.ProcessData;
import com.jiumeng.movieheaven2.utils.MyTextUtils;
import com.jiumeng.movieheaven2.utils.UIUtils;
import com.jiumeng.movieheaven2.views.LoadingPage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.jiumeng.movieheaven2.R.id.iv_img;
import static java.lang.System.load;


/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class MovieDetailsFragment extends BaseLoadFragment implements View.OnClickListener {

    @BindView(iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_years)
    TextView tvYears;
    @BindView(R.id.tv_subtitle)
    TextView tvSubtitle;
    @BindView(R.id.tv_play_time)
    TextView tvPlayTime;
    @BindView(R.id.tv_language)
    TextView tvLanguage;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_ibdm)
    TextView tvIbdm;
    @BindView(R.id.tv_update_time)
    TextView tvUpdateTime;
    @BindView(R.id.tv_file_size)
    TextView tvFileSize;
    @BindView(R.id.ll_file_size)
    LinearLayout llFileSize;
    @BindView(R.id.tv_country)
    TextView tvCountry;
    @BindView(R.id.tv_director)
    TextView tvDirector;
    @BindView(R.id.tv_starring)
    TextView tvStarring;
    @BindView(R.id.tv_introduction)
    TextView tvIntroduction;
    @BindView(R.id.iv_img2)
    ImageView ivImg2;
    @BindView(R.id.iv_fav)
    ImageView ivFav;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    private MovieEntity mMovieDetail;

    @Override
    protected View onCreateSuccessView() {
        View mRootView = mLayoutInflater.inflate(R.layout.fragment_movie_details, null);
        ButterKnife.bind(this, mRootView);
        showMovieDetail();
        return mRootView;
    }

    @Override
    protected void initPageData(boolean isFirstLoad) {
        mMovieDetail = (MovieEntity) getArguments().getSerializable("movie");
        loadMovieDetail();
    }

    /**
     * 显示电影详情界面
     */
    private void loadMovieDetail() {
        NetWorkApi.getMovieDetailInfo(mMovieDetail.url, this, new MyStringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                //网络
                loadDataComplete(LoadingPage.ResultState.STATE_NONETWORK);
            }

            @Override
            public void onResponse(String response, int id) {
                mMovieDetail = ProcessData.parseMovieDetails(response, mMovieDetail, false);
                if (mMovieDetail!=null){
                    loadDataComplete(LoadingPage.ResultState.STATE_SUCCESS);
                }else {
                    loadDataComplete(LoadingPage.ResultState.STATE_ERROR);
                }

            }
        });
    }

    private void showMovieDetail() {
        Glide.with(getContext()).load(mMovieDetail.jpgList.get(0)).transform(new GlideRoundTransform(getContext(),5)).placeholder(R.drawable.default_movie_image).into(ivImg);
        Glide.with(getContext()).load(mMovieDetail.jpgList.get(1)).fitCenter().error(R.drawable.default_movie_image2).into(ivImg2);
        tvTitle.setText(mMovieDetail.minName);
        tvYears.setText("年代："+ mMovieDetail.years);
        tvSubtitle.setText("字幕："+mMovieDetail.subtitle);
        tvPlayTime.setText("片长："+ mMovieDetail.play_time);
        tvLanguage.setText("语言："+ mMovieDetail.language);
        tvCategory.setText("类别："+ mMovieDetail.category);
        tvIbdm.setText("评分："+ mMovieDetail.grade);
        tvUpdateTime.setText("更新日期："+ mMovieDetail.updatetime);
        if (MyTextUtils.isEmpty(mMovieDetail.filesize)){
            llFileSize.setVisibility(View.GONE);
        }else {
            tvFileSize.setText("文件大小："+mMovieDetail.filesize);
        }
        tvCountry.setText("国家：　"+ mMovieDetail.country);
        tvDirector.setText(mMovieDetail.director);
        tvStarring.setText(mMovieDetail.starring);
        tvIntroduction.setText(mMovieDetail.introduction);
    }


    @OnClick({R.id.iv_fav, R.id.iv_share, R.id.iv_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fav:
                break;
            case R.id.iv_share:
                //showShare();
                break;
            case R.id.iv_download:
                new DownloadManager(getContext()).startXunlei(mMovieDetail.downlist.get(0));
                break;
        }
    }


//    private void showShare() {
//        ShareSDK.initSDK(UIUtils.getContext());
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle("电影天堂");
//        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://oaydggmwi.bkt.clouddn.com/com.jiumeng.movieheaven.apk");
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText("您的好友分享了一部大片给你："+mMovieDetail.minName);
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://oaydggmwi.bkt.clouddn.com/com.jiumeng.movieheaven.apk");
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://oaydggmwi.bkt.clouddn.com/com.jiumeng.movieheaven.apk");
//
//        // 启动分享GUI
//        oks.show(UIUtils.getContext());
//    }


}
