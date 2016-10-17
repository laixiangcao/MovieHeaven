package com.jiumeng.movieheaven2.engine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.jiumeng.movieheaven2.network.NetWorkApi;
import com.jiumeng.movieheaven2.utils.MyTextUtils;
import com.jiumeng.movieheaven2.utils.PrefUtils;
import com.jiumeng.movieheaven2.utils.UIUtils;
import java.io.File;
import java.util.List;

/**
 * Created by jiumeng on 2016/10/15.
 */

public class DownloadManager {

    private final Context mContext;

    public DownloadManager(Context context) {
        this.mContext = context;
    }


    /**
     * 启动迅雷下载
     * @param url 需要下载的链接
     */
    public void startXunlei(String url) {
        if (MyTextUtils.isEmpty(url)){
            UIUtils.showToast("下载链接不存在");
            return ;
        }
//        //检测是否已安装迅雷软件
//        if (isInstallXunlei()) {
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            intent.addCategory("android.intent.category.DEFAULT");
//            mContext.startActivity(intent);
//        } else {
//            //显示对话框 通知用户是否下载迅雷软件
//            showInstallDia();
//        }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addCategory("android.intent.category.DEFAULT");
            mContext.startActivity(intent);
    }

    /**
     * 用于下载迅雷
     */
    private void DownloadXunlei() {

        String str = "/Download/APK/手机迅雷.apk";
        String fileName = Environment.getExternalStorageDirectory() + str;
        File file = new File(fileName);
        boolean isExists = file.exists();

        //情况一：上次下载完成后 却没有完成安装
        //直接安装即可
        if (isExists) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            mContext.startActivity(intent);

            //情况二：从未下载过
            // 调用系统的下载管理器 下载并安装
        } else {
            //获取系统的下载管理器
            android.app.DownloadManager downloadManager = (android.app.DownloadManager) UIUtils.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            //设置下载连接
            Uri uri = Uri.parse(NetWorkApi.MYHOST+"/xunlei.apk");

            //请求下载
            android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(uri);

            //设置允许使用的网络类型，这里是移动网络和wifi都可以
            request.setAllowedNetworkTypes(android.app.DownloadManager.Request.NETWORK_MOBILE | android.app.DownloadManager.Request.NETWORK_WIFI);

            // 设置下载路径和文件名
            request.setDestinationInExternalPublicDir("Download", "xunlei.apk");
            request.setDescription("手机迅雷下载");
            request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setMimeType("application/vnd.android.package-archive");

            // 设置为可被媒体扫描器找到
            request.allowScanningByMediaScanner();
            // 设置为可见和可管理
            request.setVisibleInDownloadsUi(true);
            long refernece = downloadManager.enqueue(request);
            // 把当前下载的ID保存起来
            PrefUtils.putLong("downloadID", refernece);
        }
    }

    private void showInstallDia() {
        AlertDialog.Builder normalDia = new AlertDialog.Builder(mContext);
//        normalDia.setIcon(R.drawable.ic_launcher);
        normalDia.setTitle("通知：");
        normalDia.setMessage("　　系统检测到您手机未安装\"手机迅雷软件\",将无法为您提供下载服务.\r\n\r\n  是否为您下载手机迅雷软件？");

        normalDia.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DownloadXunlei();
            }
        });
        normalDia.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "很遗憾未能帮你下载该电影", Toast.LENGTH_SHORT).show();
            }
        });
        normalDia.create().show();
    }

    /**
     * 判断是否安装了手机迅雷软件
     */
    private boolean isInstallXunlei() {
        String pkgName = "com.xunlei.downloadprovider";
//        PackageInfo packageInfo;
//        try {
//            packageInfo = UIUtils.getContext().getPackageManager().getPackageInfo(pkgName, 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            packageInfo = null;
//            e.printStackTrace();
//        }
//        return packageInfo != null;

        final PackageManager packageManager = mContext.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for ( int i = 0; i < pinfo.size(); i++ )
        {
            if(pinfo.get(i).packageName.equalsIgnoreCase(pkgName))
                return true;
        }
        return false;
    }
}
