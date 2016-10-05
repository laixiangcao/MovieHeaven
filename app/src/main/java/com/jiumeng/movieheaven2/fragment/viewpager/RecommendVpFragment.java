package com.jiumeng.movieheaven2.fragment.viewpager;

import com.jiumeng.movieheaven2.bean.MovieDao;
import com.jiumeng.movieheaven2.bean.MultipleItem;
import com.jiumeng.movieheaven2.fragment.impl.Impl2MultipleLayoutFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * * 推荐页面的viewpager的 推荐电影频道页面
 * Created by jiumeng on 2016/9/27.
 */
public class RecommendVpFragment extends Impl2MultipleLayoutFragment {

    @Override
    protected int getMovieType() {
        return getArguments().getInt("movieType");
    }

    @Override
    public List<MultipleItem> setMultipeItem(ArrayList<MovieDao> data) {
        return null;
    }

    @Override
    public List<MultipleItem> setMultipeItem2(ArrayList<ArrayList<MovieDao>> data) {
        List<MultipleItem> initData = new ArrayList<>();
        for (ArrayList<MovieDao> list : data) {
            initData.add(new MultipleItem(MultipleItem.RECOMMEND,1,list));
        }
        return initData;
    }

//    public void getStringFormNet() {
//        ThreadManager.getThreadManager().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL("http://www.dy2018.com/");
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setConnectTimeout(6000);
//                    conn.setReadTimeout(6000);
//                    if (conn.getResponseCode() == 200) {
//                        InputStream is = conn.getInputStream();
//                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                        byte[] buf = new byte[1024];
//                        int len;
//                        while ((len = is.read(buf)) != -1) {
//                            bos.write(buf, 0, len);
//                        }
//                        String result = new String(bos.toByteArray(), "GBK");
//                        ArrayList<ArrayList<String>> homeUrlList = ProcessHomeUrl.getHomeUrl(result);
//                        if (homeUrlList!=null){
//                            System.out.println("aaa:----"+homeUrlList.size());
//                            for (String s : homeUrlList.get(0)) {
//                                System.out.println("aaa:----"+s);
//                            }
//                        }
//                        bos.close();
//                        is.close();
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//    }
}