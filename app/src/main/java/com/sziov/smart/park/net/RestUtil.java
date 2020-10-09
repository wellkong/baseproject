package com.sziov.smart.park.net;

import com.sziov.smart.park.app.AppConfigEntrance;
import com.sziov.smart.park.net.exception.HttpErrorHandler;
import com.sziov.smart.park.net.exception.ServerException;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @ProjectName: SmartPark
 * @Package: com.sziov.smart.park.net
 * @Author: willkong
 * @CreateDate: 2020/9/29 14:40
 * @Description: 网络处理工具类
 */
public class RestUtil {
    public static <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable =
                        upstream.subscribeOn(Schedulers.io())
                                .unsubscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .map(serverErrorHandler())
                                .onErrorResumeNext(new HttpErrorHandler(AppConfigEntrance.getApplicationContext()));
                observable.subscribe(observer);
                return observable;
            }
        };
    }

    /**
     * 服务器响应失败处理
     *
     * @param <T>
     * @return
     */
    private static <T> Function<T, T> serverErrorHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(T response) throws Exception {
                //response中code码不为0 出现错误
                JSONObject jsonObject = new JSONObject((String) response);
                int code = (int) jsonObject.get(ServerResult.CODE);
                String message = (String) jsonObject.get(ServerResult.MESSAGE);
                if (code != ServerResult.SERVER_SUCCESS_CODE) {
                    throw new ServerException(code, message);
                }
                return response;
            }
        };
    }
}
