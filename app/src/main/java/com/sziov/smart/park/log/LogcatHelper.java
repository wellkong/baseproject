package com.sziov.smart.park.log;

import android.content.Context;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * @ClassName: LogcatHelper
 * @Author: willkong
 * @Description: 保存控制台日志到本地
 * @CreateDate: 2020/7/26 0:06
 */
public class LogcatHelper {
    private static final String TAG = "LogcatHelper";
    private static LogcatHelper INSTANCE = null;
    private static String PATH_LOGCAT;
    private LogDumper mLogDumper = null;
    private int mPId;

    /**
     * 初始化目录
     */
    private void init(Context context) {
//        if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
//            PATH_LOGCAT = Environment.getExternalStorageDirectory()
//                    .getAbsolutePath() + File.separator + "interprenter";
//        } else {// 如果SD卡不存在，就保存到本应用的目录下
            PATH_LOGCAT = context.getExternalFilesDir(null)+File.separator+"log";
//        }
        File file = new File(PATH_LOGCAT);
        if (!file.exists()) {
            file.mkdirs();
            Logger.i("创建log文件夹");
        }
        Logger.i(PATH_LOGCAT);
    }

    public static LogcatHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LogcatHelper(context);
        }
        return INSTANCE;
    }

    private LogcatHelper(Context context) {
        init(context);
        mPId = android.os.Process.myPid();
    }

    public void start() {
        if (mLogDumper == null)
            mLogDumper = new LogDumper(String.valueOf(mPId), PATH_LOGCAT);
        mLogDumper.start();
    }

    public void stop() {
        if (mLogDumper != null) {
            mLogDumper.stopLogs();
            mLogDumper = null;
        }
    }

    private class LogDumper extends Thread {
        private Process logcatProc;
        private BufferedReader mReader = null;
        private boolean mRunning = true;
        String cmds = null;
        private String mPID;
        private FileOutputStream out = null;

        public LogDumper(String pid, String dir) {
            mPID = pid;
            try {
                out = new FileOutputStream(new File(dir, "log_"
                        + getFileName() + ".log"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            /**
             *
             * 日志等级：*:v , *:d , *:w , *:e , *:f , *:s
             *
             * 显示当前mPID程序的 E和W等级的日志.
             *
             * */
            // cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
            // cmds = "logcat  | grep \"(" + mPID + ")\"";//打印所有日志信息
            // cmds = "logcat -s way";//打印标签过滤信息
            cmds = "logcat *:e *:i | grep \"(" + mPID + ")\"";

            /**
             * 日志数量的管理
             * 默认值存在10
             */
            logLimitCount(10);
        }

        public void stopLogs() {
            mRunning = false;
        }

        @Override
        public void run() {
            try {
                logcatProc = Runtime.getRuntime().exec(cmds);
                mReader = new BufferedReader(new InputStreamReader(
                        logcatProc.getInputStream()), 1024);
                String line = null;
                while (mRunning && (line = mReader.readLine()) != null) {
                    if (!mRunning) {
                        break;
                    }
                    if (line.length() == 0) {
                        continue;
                    }
                    if (out != null && line.contains(mPID)) {
                        out.write((line + "\n").getBytes());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (logcatProc != null) {
                    logcatProc.destroy();
                    logcatProc = null;
                }
                if (mReader != null) {
                    try {
                        mReader.close();
                        mReader = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out = null;
                }

            }

        }
    }

    /**
     * 处理日志数量
     *
     * @param count
     */
    private void logLimitCount(int count) {
        try {
            File file = new File(PATH_LOGCAT);
            if (file != null && file.isDirectory()) {
                //过滤文件类型文件
                File[] files = file.listFiles(new FileLogFilter());
                if (files != null && files.length > 10) {
                    //排序
                    Arrays.sort(files, comparator);
                    for (int i = 0; i < files.length - count; i++) {
                        files[i].delete();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "logLimitCount - " + e.getMessage());
        }
    }

    /**
     * 排序
     * 由小到大
     */
    private Comparator<File> comparator = new Comparator<File>() {
        @Override
        public int compare(File lhs, File rhs) {
            if (lhs.lastModified() > rhs.lastModified())
                return 1;
            if (lhs.lastModified() < rhs.lastModified())
                return -1;
            return 0;
        }
    };

    /**
     * 过滤文件
     */
    private class FileLogFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            if (pathname != null && pathname.getName().endsWith(".log")) {
                return true;
            }
            return false;
        }
    }

    public String getFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;// 2012年10月03日 23:41:31
    }
}
