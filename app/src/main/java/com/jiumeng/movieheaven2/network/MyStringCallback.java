package com.jiumeng.movieheaven2.network;

import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jiumeng on 2016/10/9.
 */

public abstract class MyStringCallback extends Callback<String>
{

    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        return new String(response.body().bytes(), "GBK");
    }

}
