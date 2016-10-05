//package com.jiumeng.movieheaven2.provider;
//
//import com.jiumeng.movieheaven2.bean.MovieDao;
//import com.jiumeng.movieheaven2.network.NetWorkApi;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//
//import java.util.List;
//
//import cz.msebera.android.httpclient.Header;
//
//import static android.R.attr.type;
//
///**
// * Created by jiumeng on 2016/9/29.
// */
//
//public class DataProvider {
//    //加载初试化数据
//    public List<MovieDao> initData(int type) {
//        //判断时候存在缓存数据
//        List<MovieDao> data = DataTools.getCacheData(type);
//        if (data != null) {
//            if (!DataTools.isCacheInvalid(type)) {
//                //存在缓存 但是缓存未失效
//                return data;
//
//            } else {
//                //存在缓存 但是缓存已失效 加载网络数据
//                //不存在缓存 加载网络数据
//                return null;
//            }
//        }
//    }
//        //加载更多数据
//        public List<MovieDao> loadMoreData ( int type){
//
//        }
//        //刷新数据
//        public List<MovieDao> refreshData ( int type){
//            //根据类型 去加载数据
//        }
//
//    public void loadNetData(int type, int page, AsyncHttpResponseHandler handler) {
//        NetWorkApi.getPageInfoFromNet(type, page, handler);
//    }
//}
