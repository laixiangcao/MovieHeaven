package com.jiumeng.movieheaven2.provider;

import android.util.Log;

import com.jiumeng.movieheaven2.entity.MovieEntity;
import com.jiumeng.movieheaven2.utils.UIUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jiumeng.movieheaven2.utils.UIUtils.textToMD5;

/**
 * Created by jiumeng on 2016/9/28.
 */

public class ProcessData {
    /**
     * 获取分类电影的页数
     *
     * @param text 该分类的链接
     * @return 页数 失败返回-1
     */
    public static int getPages(String text) {
        int index1 = text.lastIndexOf("页</option></select>");
        int index2 = text.lastIndexOf("第", index1);
        if (index1 != -1 && index2 != -1) {
            String page = text.substring(index2 + 1, index1).trim();
            return Integer.parseInt(page);
        }
        return -1;
    }

    /**
     * 解析页面简略电影           url id name minNmae 日期 类型 评分
     * @param text 网页文本
     * @return 电影集合
     */
    public static ArrayList<MovieEntity> parsePageData(String text) {
        String contents;
        // 截取网页主要文本文本
        ArrayList<MovieEntity> movieList;
        int index1 = text.indexOf("co_content8");
        int index2 = text.indexOf("尾页");
        if (index1 != -1 && index2 != -1) {
            contents = text.substring(index1, index2);
            movieList = new ArrayList<>();
        } else {
            return null;
        }
        String[] split = contents.split("</table><table");

        for (String s : split) {
            String content = s.replaceAll("\r\n", "").replaceAll(" ", "");
            MovieEntity movieDao = new MovieEntity();
            // 获取url连接
            int index3 = content.indexOf("<ahref=\"");

            int index4 = content.indexOf(".html", index3);
            if (index3 != -1 && index4 != -1) {
                movieDao.url = "http://www.dy2018.com/" + content.substring(index3 + 9, index4 + 5);
                movieDao.id = textToMD5(movieDao.url);
            } else {
                movieDao.url = "";
                movieDao.id = "";
            }

            // 获取title
            int index5 = content.indexOf("title");
            int index6 = content.indexOf("\">", index5);
            if (index5 != -1 && index6 != -1) {
                movieDao.name = content.substring(index5 + 7, index6);
            } else {
                movieDao.name = "";
            }

            // 获取日期
            int index7 = content.indexOf("日期");

            if (index7 != -1) {
                movieDao.updatetime = content.substring(index7 + 3, index7 + 13);
            } else {
                movieDao.updatetime = "";
            }
            // 获取评分
            int index8 = content.indexOf("评分");
            if (index8 != -1) {
                movieDao.grade = content.substring(index8 + 3, index8 + 6).replace("</", "");
            } else {
                movieDao.grade = "";
            }

            // 获取片名
            int index9 = movieDao.name.indexOf("《");
            int index10 = movieDao.name.indexOf("》", index9);
            if (index9 != -1 && index10 != -1) {
                movieDao.minName = movieDao.name.substring(index9 + 1, index10);
            } else {
                movieDao.minName = "";
            }

            // 获取类型
            int index13 = content.indexOf("类型");
            if (index13==-1){
                index13=content.indexOf("类　　别");
                if (index13!=-1){
                    index13+=2;
                }
            }
            int index14 = content.indexOf("◎", index13);
            if (index13 != -1 && index14 != -1) {
                movieDao.category = content.substring(index13 + 3, index14);
            } else {
                movieDao.category = "";
            }
            movieList.add(movieDao);
        }
        return movieList;
    }




    //海报图 详情图 下载链接 片长 导演 主演 文件大小 简介 年代  国家  语言 字幕
    //
    // 截取网页主要文本文本
    public static MovieEntity parseMovieDetails(String text, MovieEntity movie, boolean isLoadAll) {
        try {
            String contents;
            int index1 = text.indexOf("title_all");
            int index2 = text.indexOf("</script>", index1);
            if (index1 != -1 && index2 != -1) {
                contents = text.substring(index1, index2).replaceAll("　", "")
                        .replaceAll("</strong></span>", "").replaceAll("&ldquo;", "\"")
                        .replaceAll("<span style=\"COLOR: red\"><strong>", "").replaceAll("&rdquo;", "\"")
                        .replaceAll("&mdash;", "—").replaceAll("&middot;", "·")
                        .replaceAll("&hellip;", "...").replaceAll("&nbsp;", " ").replaceAll("<br />", "\n")
                        .replaceAll("</p>", "").replaceAll("<p>", "").replaceAll("</span>", "")
                        .replace("<span style=\"color:rgb(0,0,0);\">", "")
                        .replace("<span style=\"font-size: 14px;\">", "")
                        .replace("<span style=\"color: #000000\">", "")
                        .replace("<span style=\"color: rgb(0, 0, 0);\">", "")
                        .replaceAll("&bull;", "•");
            } else {
                return null;
            }
            //获取图片
            String teg2 = "src=\"http+://[^\\s]+(jpg|png)";
            Pattern p2 = Pattern.compile(teg2, Pattern.MULTILINE);
            Matcher m2 = p2.matcher(contents);
            movie.jpgList = new ArrayList<String>();
            while (m2.find()) {
                movie.jpgList.add(m2.group().replace("src=\"", ""));
            }
            //获取电影的下载地址
            String teg = "(>ftp://)+[^\\s]+(rmvb|mkv|mp4)";
            Pattern p = Pattern.compile(teg, Pattern.MULTILINE);
            Matcher m = p.matcher(contents);
            movie.downlist = new ArrayList<String>();
            while (m.find()) {
                movie.downlist.add(m.group().replace(">", ""));
            }


            //年代
            int idx01 = contents.indexOf("年代");
            int idx02 = contents.indexOf("◎", idx01);
            if (idx01 == -1 || idx02 == -1) {
                movie.years = "";
            } else {
                movie.years = contents.substring(idx01 + 2, idx02);
            }

            // 获取国家
            int idx03 = contents.indexOf("国家");
            if (idx03 == -1) {
                idx03 = contents.indexOf("国别");
            }
            int idx04 = -1;
            if (idx03 != -1) idx04 = contents.indexOf("◎", idx03);
            if (idx03 != -1 && idx04 != -1) {
                movie.country = contents.substring(idx03 + 2, idx04);
            } else {
                movie.country = "";
            }

            //语言
            int idx05 = contents.indexOf("语言");
            int idx06 = contents.indexOf("◎", idx05);
            if (idx05 == -1 || idx06 == -1) {
                movie.language = "";
            } else {
                movie.language = contents.substring(idx05 + 2, idx06);
            }

            //字幕
            int idx07 = contents.indexOf("字幕");
            int idx08 = contents.indexOf("◎", idx07);
            if (idx07 == -1 || idx08 == -1) {
                movie.subtitle = "";
            } else {
                movie.subtitle = contents.substring(idx07 + 2, idx08);
            }


            //文件大小
            int mIndex3 = contents.indexOf("文件大小");
            int mIndex4 = contents.indexOf("◎", mIndex3);
            if (mIndex3 == -1 || mIndex4 == -1) {
                movie.filesize = "";
            } else {
                movie.filesize = contents.substring(mIndex3 + 4, mIndex4);
            }
            //片长
            int mIndex5 = contents.indexOf("片长");
            int mIndex6 = contents.indexOf("◎", mIndex5);
            if (mIndex5 == -1 || mIndex6 == -1) {
                movie.play_time = "";
            } else {
                movie.play_time = contents.substring(mIndex5 + 2, mIndex6);
            }

            //导演
            int mIndex7 = contents.indexOf("导演", mIndex5);
            int mIndex8 = contents.indexOf("◎", mIndex7);
            if (mIndex7 == -1 || mIndex8 == -1) {
                movie.director = "";
            } else {
                movie.director = contents.substring(mIndex7 + 2, mIndex8);
            }
            //主演
            int mIndex9 = contents.indexOf("主演");
            int mIndex10 = contents.indexOf("◎", mIndex9);
            if (mIndex9 == -1 || mIndex10 == -1) {
                movie.starring = "";
            } else {
                movie.starring = contents.substring(mIndex9 + 2, mIndex10).replaceAll("  ", "");
            }
            //简介
            int mIndex11 = contents.indexOf("简介");
            int mIndex12 = contents.indexOf("◎", mIndex11);
            if (mIndex12 == -1) {
                mIndex12 = contents.indexOf("<img", mIndex11);
            }
            if (mIndex11 == -1 || mIndex12 == -1) {
                movie.introduction = "";
            } else {
                movie.introduction = contents.substring(mIndex11 + 2, mIndex12);
            }

            // url id name minNmae 日期 类型 评分
            if (isLoadAll){
                // 获取title
                int index5 = contents.indexOf("<h1>");
                int index6 = contents.indexOf("</h1>", index5);
                if (index5 != -1 && index6 != -1) {
                    movie.name = contents.substring(index5 + 4, index6);
                } else {
                    movie.name = "";
                }

                // 获取电影发布时间
                int index7 = contents.indexOf("发布时间");
                if(index5!=-1){
                    movie.updatetime = contents.substring(index7 + 5, index7 + 15);
                }else{
                    movie.updatetime ="";
                }
                // 获取评分
                int index8 = contents.indexOf("◎豆瓣评分");
                if (index8 == -1) {
                    index8 = contents.indexOf("◎IMDB评分");
                }
                if (index8 == -1) {
                    index8 = contents.indexOf("◎IMDb评分");
                }
                if (index8 == -1) {
                    index8 = contents.indexOf("◎IMDB分数");
                }
                int index8_1=contents.indexOf("/",index8);
                if (index8 != -1) {
                    movie.grade = contents.substring(index8 + 5, index8_1).replace("评分", "").replace("Ratings", "");
                    if(movie.grade.length()>4)movie.grade="未知";
                } else {
                    movie.grade = "未知";
                }

                // 获取片名
                int index9 = movie.name.indexOf("《");
                int index10 = movie.name.indexOf("》", index9);
                if (index9 != -1 && index10 != -1) {
                    movie.minName = movie.name.substring(index9 + 1, index10);
                } else {
                    movie.minName = "";
                }

                // 获取类型
                int index13 = contents.indexOf("类型");
                if (index13 == -1) {
                    index13 = contents.indexOf("分类");
                }
                if (index13 == -1) {
                    index13 = contents.indexOf("类别");
                }
                int index14 = -1;
                if (index13 != -1) {
                    index14 = contents.indexOf("◎", index13);
                }

                if (index13 != -1 && index14 != -1) {
                    movie.category = contents.substring(index13 + 2, index14);
                } else {
                    movie.category = "";
                }
                //id
                movie.id= UIUtils.textToMD5(movie.url);

            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Process Data Exception","[Moive Detail Obtain Exception]"+movie.url);
        }
        return movie;
    }
}
