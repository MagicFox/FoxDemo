package com.example.fox.http;

import com.example.fox.BuildConfig;
import com.example.fox.utils.LogUtil;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 *
 * @param <T>
 */
public abstract class MySubscriber<T> extends Subscriber<T> {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;


    @Override
    public void onError(Throwable e) {
        if(e == null)return;
        Throwable throwable = e;
        //获取最根源的异常
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }
        if (e instanceof UnknownHostException) {
            onError("无法连接到服务器，请检查网络！");
        } else if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            LogUtil.e("httpErrorCode : " + httpException.code());
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                    onError("权限错误");         //权限错误，需要实现
                    break;
                case NOT_FOUND:
                    onError("访问的链接不存在");
                    break;
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                    onError("无法连接到服务器，请检查网络！");
                    break;
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    onError(BuildConfig.DEBUG ? "服务器内部错误" : "无法连接到服务器，请检查网络！");
                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException) {
            onError("数据解析错误");//均视为解析错误
        } else {
            onError("无法连接到服务器，请检查网络！");//未知错误
        }
    }

    /**
     * 错误回调
     */
    protected abstract void onError(String errorMsg);

    @Override
    public void onCompleted() {

    }

}
