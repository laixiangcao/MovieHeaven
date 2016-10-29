package com.jiumeng.movieheaven2.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jiumeng.movieheaven2.R;
import com.jiumeng.movieheaven2.fragment.other.SearchFragment;
import com.jiumeng.movieheaven2.fragment.other.SearchResultFragment;
import com.jiumeng.movieheaven2.utils.MyTextUtils;
import com.jiumeng.movieheaven2.utils.UIUtils;

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
                    if (!MyTextUtils.isEmpty(text)) {
                        if (text.getBytes().length < 2) {
                            UIUtils.showToast("搜索内容必须不能小于2个字节");
                        } else {
                            setDefFragment(FRAGMENT_TYPE_SEARCH_RESULT,text);
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        setDefFragment(0,"");
    }

    private void setDefFragment(int type,String searchResult) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (type){
            case FRAGMENT_TYPE_SEARCH:
                SearchFragment searchFragment = new SearchFragment();
                fragmentTransaction.replace(R.id.ll_content, searchFragment);
                break;
            case FRAGMENT_TYPE_SEARCH_RESULT:
                SearchResultFragment searchResultFragment = new SearchResultFragment();
                Bundle bundle = new Bundle();
                bundle.putString(SearchResultFragment.SEARCH_CONTENT, searchResult);
                fragmentTransaction.replace(R.id.ll_content, searchResultFragment);
                searchResultFragment.setArguments(bundle);
                break;
        }
        fragmentTransaction.commit();
    }



}
