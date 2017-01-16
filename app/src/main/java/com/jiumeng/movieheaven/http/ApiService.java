package com.jiumeng.movieheaven.http;


/**
 * Created by jiumeng on 2016/11/21.
 */

public interface ApiService {


//    String BASE_URL = "http://www.dy2018.com";//主连接
    String BASE_SERVICE_URL = "http://oh6mozvo5.bkt.clouddn.com";//服务器主连接
    String JPG_SUFFIX = "?imageView2/2/w/308/h/210/interlace/1/q/100";//image缩略图后缀
    String APK_DOWNLOAD_URL = "/com.jiumeng.movieheaven.apk";//apk下载链接
//    long MOVIE_CACHE_VALID_TIME = 6 * 60 * 60 * 1000;//缓存有效时间
//
//    //获取最新电影页面信息
//    @GET("/{category}/index{page}.html")
//    Observable<ResponseBody> getPagesData(@Path("category") String category, @Path("page") String page);
//
//    //获取首页电影页面信息
//    @GET("http://www.dy2018.com/")
//    Observable<ResponseBody> getHomeData();
//
//    //获取指定页面电影详情
//    @GET("{movieUrl}")
//    Observable<ResponseBody> getMovieDetail(@Path("movieUrl") String movieUrl);
//
//    //加载一个完整的链接
//    @GET
//    Observable<ResponseBody> loadString(@Url String url);


    /**
     * 剧情
     */
     int MOVIE_TYPE_SCENARIO = 0;
    /**
     * 喜剧
     */
     int MOVIE_TYPE_COMEDY = 1;
    /**
     * 动作
     */
     int MOVIE_TYPE_ACTION = 2;
    /**
     * 爱情
     */
     int MOVIE_TYPE_AFFECTION = 3;
    /**
     * 科幻
     */
     int MOVIE_TYPE_SCIENCE_FICTION = 4;
    /**
     * 动画
     */
     int MOVIE_TYPE_ANIMATION = 5;
    /**
     * 悬疑
     */
     int MOVIE_TYPE_SUSPENSE = 6;
    /**
     * 惊悚
     */
     int MOVIE_TYPE_PANIC = 7;
    /**
     * 恐怖
     */
     int MOVIE_TYPE_TERROR = 8;
    /**
     * 战争
     */
     int MOVIE_TYPE_WAR = 9;
    /**
     * 犯罪
     */
     int MOVIE_TYPE_CRIME = 10;


    /**
     * 最新电影类型
     */
     int MOVIE_TYPE_NEWEST = 11;
    /**
     * 最热电影类型
     */
     int MOVIE_TYPE_HOTTEST = 12;
    /**
     * 经典电影类型
     */
     int MOVIE_TYPE_CLASSICS = 13;

    /**
     * 经典电影类型
     */
    int MOVIE_TYPE_RECOMMEND = 14;


}
