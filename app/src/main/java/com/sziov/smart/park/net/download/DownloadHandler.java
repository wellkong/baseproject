package com.sziov.smart.park.net.download;

import android.os.AsyncTask;

import com.sziov.smart.park.net.RestCreator;
import com.sziov.smart.park.net.callback.IError;
import com.sziov.smart.park.net.callback.IFailure;
import com.sziov.smart.park.net.callback.IProgress;
import com.sziov.smart.park.net.callback.IRequest;
import com.sziov.smart.park.net.callback.ISuccess;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 下载处理
 */
public class DownloadHandler {
    private final String URL;
    private static final Map<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final IProgress PROGRESS;

    public DownloadHandler(String url,
                           IRequest request,
                           String download_dir,
                           String extension,
                           String name,
                           ISuccess success,
                           IFailure failure,
                           IError error,
                           IProgress progress) {
        URL = url;
        REQUEST = request;
        DOWNLOAD_DIR = download_dir;
        EXTENSION = extension;
        NAME = name;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
        PROGRESS = progress;
    }

    public final void handleDownload() {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        RestCreator.getRestService().download(URL, PARAMS).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    final ResponseBody responseBody = response.body();
                    final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS, PROGRESS);
                    //根据SaveFileTask任务的参数顺序传参数
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, responseBody, NAME);
                    //这里一定要注意判断，否则文件下载不全
                    if (task.isCancelled()) {
                        if (REQUEST != null) {
                            REQUEST.onRequestEnd();
                        }
                    }
                } else {
                    if (ERROR != null) {
                        ERROR.onError(response.code(), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (FAILURE != null) {
                    FAILURE.onFailure();
                }
            }
        });
    }
}
