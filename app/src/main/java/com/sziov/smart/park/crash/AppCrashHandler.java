package com.sziov.smart.park.crash;

import android.content.Context;
import android.util.Log;

import java.io.File;


/**
 * @ClassName: AppCrashHandler
 * @Author: willkong
 * @Description: 异常捕获，上传服务器
 * @CreateDate: 2020/7/24 23:39
 */
public class AppCrashHandler extends AppCrashLog{


    private static AppCrashHandler mCrashHandler = null;

    private AppCrashHandler(){};

    public static AppCrashHandler getInstance() {

        if(mCrashHandler == null) {
            mCrashHandler = new AppCrashHandler();
        }
        return mCrashHandler;
    }
    @Override
    public void initParams(Context context) {
        // TODO Auto-generated method stub
        Log.i("************", "initParams");
        AppCrashLog.CACHE_LOG = context.getExternalFilesDir(null)+File.separator+"log";
        Log.i("************", "path:"+AppCrashLog.CACHE_LOG);
    }

    @Override
    public void sendCrashLogToServer(File file) {
        // TODO Auto-generated method stub

        Log.i("************", "sendCrashLogToServer");
    }

}
