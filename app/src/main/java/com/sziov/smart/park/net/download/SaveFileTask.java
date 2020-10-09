package com.sziov.smart.park.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.sziov.smart.park.app.AppConfigEntrance;
import com.sziov.smart.park.net.callback.IProgress;
import com.sziov.smart.park.net.callback.IRequest;
import com.sziov.smart.park.net.callback.ISuccess;
import com.sziov.smart.park.utils.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

// 类中参数为3种泛型类型
// 整体作用：控制AsyncTask子类执行线程任务时各个阶段的返回类型
// 具体说明：
// a. Params：开始异步任务执行时传入的参数类型，对应excute（）中传递的参数
// b. Progress：异步任务执行过程中，返回下载进度值的类型
// c. Result：异步任务执行完成后，返回的结果类型，与doInBackground()的返回值类型保持一致
// 注：
// a. 使用时并不是所有类型都被使用
// b. 若无被使用，可用java.lang.Void类型代替
// c. 若有不同业务，需额外再写1个AsyncTask的子类

/**
 * 异步任务
 * 保存文件任务
 */
public class SaveFileTask extends AsyncTask<Object, Integer, File> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IProgress PROGRESS;

    public SaveFileTask(IRequest request, ISuccess success, IProgress progress) {
        REQUEST = request;
        SUCCESS = success;
        PROGRESS = progress;
    }

    // 方法1：onPreExecute（）
    // 作用：执行 线程任务前的操作
    @Override
    protected void onPreExecute() {
//        text.setText("加载中");
        // 执行前显示提示
    }

    // 方法2：doInBackground（）
    // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
    // 此处通过计算从而模拟“加载进度”的情况
    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream is = body.byteStream();
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        if (name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    // 方法4：onPostExecute（）
    // 作用：接收线程任务执行结果、将执行结果显示到UI组件
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        autoInstallApk(file);
    }

    // 方法3：onProgressUpdate（）
    // 作用：在主线程 显示线程任务执行的进度
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (PROGRESS != null) {
            PROGRESS.onProgress(values[0]);
        }
    }

    // 方法5：onCancelled()
    // 作用：将异步任务设置为：取消状态
    @Override
    protected void onCancelled() {
        if (PROGRESS != null) {
            PROGRESS.onProgress(0);
        }
    }

    /**
     * 自动执行安装apk
     *
     * @param file
     */
    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            AppConfigEntrance.getApplicationContext().startActivity(install);
        }
    }
}
