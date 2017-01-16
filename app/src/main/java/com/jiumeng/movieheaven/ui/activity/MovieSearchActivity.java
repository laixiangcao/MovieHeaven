package com.jiumeng.movieheaven.ui.activity;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jiumeng.movieheaven.ui.base.BaseActivity;
import com.jiumeng.movieheaven.R;
import com.jiumeng.movieheaven.utils.TextUtils;
import com.jiumeng.movieheaven.utils.ToastUtils;

import butterknife.BindView;

public class MovieSearchActivity extends BaseActivity {

    public final static int FRAGMENT_TYPE_SEARCH=0;
    public final static int FRAGMENT_TYPE_SEARCH_RESULT=1;


    @BindView(R.id.et_search)
    EditText etSearch;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_search;
    }

    @Override
    protected void initViews() {
        etSearch.setFocusableInTouchMode(true);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String text = etSearch.getText().toString();
                    if (!TextUtils.isEmpty(text)) {
                        if (text.getBytes().length < 2) {
                            ToastUtils.showShort("搜索内容必须不能小于2个字节");
                        } else {
//                            setDefFragment(FRAGMENT_TYPE_SEARCH_RESULT,text);
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
//        setDefFragment(0,"");
    }

//    private void setDefFragment(int type,String searchResult) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        switch (type){
//            case FRAGMENT_TYPE_SEARCH:
//                SearchFragment searchFragment = new SearchFragment();
//                fragmentTransaction.replace(R.id.ll_content, searchFragment);
//                break;
//            case FRAGMENT_TYPE_SEARCH_RESULT:
//                SearchResultFragment searchResultFragment = new SearchResultFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString(SearchResultFragment.SEARCH_CONTENT, searchResult);
//                fragmentTransaction.replace(R.id.ll_content, searchResultFragment);
//                searchResultFragment.setArguments(bundle);
//                break;
//        }
//        fragmentTransaction.commit();
//    }



}
