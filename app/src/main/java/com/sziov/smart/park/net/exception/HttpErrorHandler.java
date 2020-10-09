package com.sziov.smart.park.net.exception;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @ProjectName: SmartPark
 * @Package: com.sziov.smart.park.net.exception
 * @Author: willkong
 * @CreateDate: 2020/9/29 14:43
 * @Description: 异常处理类
 */
public class HttpErrorHandler<T> implements Function<Throwable, Observable<T>> {
    private Context context;

    public HttpErrorHandler(Context context) {
        this.context = context;
    }

    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(handlerError(context, throwable));
    }

    /**
     * 服务器响应错误处理
     * @param context 可以用于跳转Activity等操作
     */
    private ResponeThrowable handlerError(Context context, Throwable throwable) {
        ResponeThrowable responeThrowable = RetrofitException.retrofitException(throwable);
        // 此处可以通过判断错误代码来实现根据不同的错误代码做出相应的反应
        switch (responeThrowable.code) {
            case -1:
                // 跳转到登陆页面
                break;
            case RetrofitException.ERROR.UNKNOWN:
            case RetrofitException.ERROR.PARSE_ERROR:
            case RetrofitException.ERROR.NETWORD_ERROR:
            case RetrofitException.ERROR.HTTP_ERROR:
            case RetrofitException.ERROR.SSL_ERROR:
            default:
                break;
        }
        return responeThrowable;
    }
}
