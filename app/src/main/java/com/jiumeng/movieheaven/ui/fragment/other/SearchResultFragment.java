//package com.jiumeng.movieheaven2.ui.fragment.other;
//
//import android.view.View;
//import android.widget.TextView;
//
//import com.jiumeng.movieheaven2.R;
//import com.jiumeng.movieheaven2.ui.fragment.base.BaseLoadFragment;
//import com.jiumeng.movieheaven2.provider.ProcessData;
//import com.jiumeng.movieheaven2.utils.MyTextUtils;
//import com.jiumeng.movieheaven2.utils.UIUtils;
//import com.jiumeng.movieheaven2.ui.views.LoadingPage;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;
//import com.zzhoujay.richtext.RichText;
//import com.zzhoujay.richtext.callback.OnURLClickListener;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import okhttp3.Call;
//
///**
// * Created by jiumeng on 2016/10/29.
// */
//public class SearchResultFragment extends BaseLoadFragment {
//
//    @BindView(R.id.tv_search_result)
//    TextView tvSearchResult;
//
//
//    public static final String SEARCH_CONTENT = "SearchContent";
//    private String keyword;
//
//    @Override
//    protected View onCreateSuccessView() {
//
//        View view = mLayoutInflater.inflate(R.layout.fragment_search_result, null);
//        ButterKnife.bind(this, view);
//
//        return view;
//    }
//
//    @Override
//    protected void initPageData(boolean isFirstLoad) {
//        keyword = getArguments().getString(SEARCH_CONTENT);
//        System.out.println("aaa:"+keyword);
//
//        try {
//            System.out.println("aaa:"+URLEncoder.encode(keyword,"UTF-8"));
//            OkHttpUtils
//                    .get()
//                    .addParams("kwtype", "0")
//                    .addParams("keyword", keyword)
//                    .addParams("searchtype.x", "48")
//                    .addParams("searchtype.y", "21")
//                    .addParams("searchtype", "%E5%BD%B1%E8%A7%86%E6%90%9C%E7%B4%A2")
//                    .url("http://so.piaohua.com:8909/plus/search.php?")
//                    .build()
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//                            UIUtils.showToast(e.getLocalizedMessage());
//                        }
//
//                        @Override
//                        public void onResponse(String response, int id) {
//                            if (!MyTextUtils.isEmpty(response)) {
//                                parseSearchResult(response);
//                            } else {
//                                loadDataComplete(LoadingPage.ResultState.STATE_ERROR);
//                            }
//
//                        }
//                    });
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void parseSearchResult(String response) {
//        String searchResult = ProcessData.getSearchResult(response);
//        loadDataComplete(LoadingPage.ResultState.STATE_SUCCESS);
//        RichText.fromHtml(searchResult).noImage(true).autoFix(false).urlClick(new OnURLClickListener() {
//            @Override
//            public boolean urlClicked(String url) {
//                UIUtils.showToast(url);
//                return true;
//            }
//        }).into(tvSearchResult);
////        tvSearchResult.setText(searchResult);
//    }
//
//}
