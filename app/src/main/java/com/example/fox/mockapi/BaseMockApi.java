package com.example.fox.mockapi;

/**
 * Created by magicfox on 2017/5/19.
 */

import android.text.TextUtils;
import android.util.Log;

import com.example.fox.BuildConfig;
import com.example.fox.http.callback.ResponseCallBack;
import com.example.fox.model.DataResult;
import com.example.fox.utils.JsonUtil;
import com.example.fox.utils.LogUtil;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Base class for kinds of MockDataApi.Implements some common logic.
 */
public class BaseMockApi {
    protected int mCallCount;
    private IMockApiStrategy.Response mResponse = new IMockApiStrategy.Response();
    private IMockApiStrategy mMockApiStrategy;
    public IMockApiStrategy.Response onResponse(){
        if(mMockApiStrategy == null){
            mMockApiStrategy = getMockApiStrategy();
        }
        if(mMockApiStrategy!=null){
            mMockApiStrategy.onResponse(mCallCount++,mResponse);
        }
        return mResponse;
    }

    private IMockApiStrategy getMockApiStrategy(){
        return new WheelApiStrategy();
    }

    protected void giveErrorResult(final ResponseCallBack<?> callback, IMockApiStrategy.Response response){
        Action1<Object> onNext = null;

        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                callback.onStart();
            }
        });

        switch (response.state) {
            case IMockApiStrategy.Response.STATE_NETWORK_ERROR:
                onNext = new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        callback.onFailure("mock network error.");
                    }
                };

                break;
            case IMockApiStrategy.Response.STATE_SERVER_ERROR:
                onNext = new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        callback.onFailure("mock server error.");
                    }
                };
                break;
        }

        if (onNext != null) {
            Observable.just(10086)
                    .delay(response.delayMillis, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onNext);
        }

    }


    /**
     * Give success response, Have data.
     * @param dataMethod
     * @param callback
     * @param response
     * @param <T>
     */
    public <T> void giveSuccessResult(final Func0<T> dataMethod, final ResponseCallBack<T> callback, final IMockApiStrategy.Response response) {
        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                Observable.create(new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        Log.d("MOCK", "onNext Thread = " + Thread.currentThread().getName());
                        subscriber.onNext(dataMethod.call());
                        subscriber.onCompleted();
                    }
                }).
                        delay(response.delayMillis, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ApiSubcriber(callback));
            }
        });
    }

    /**
     * Helper class to wrap the DataApiCallback.
     * @param <T>
     */
    private static class ApiSubcriber<T> extends Subscriber<String> {
        //对应HTTP的状态码
        private static final int UNAUTHORIZED = 401;
        private static final int FORBIDDEN = 403;
        private static final int NOT_FOUND = 404;
        private static final int REQUEST_TIMEOUT = 408;
        private static final int INTERNAL_SERVER_ERROR = 500;
        private static final int BAD_GATEWAY = 502;
        private static final int SERVICE_UNAVAILABLE = 503;
        private static final int GATEWAY_TIMEOUT = 504;

        private ResponseCallBack<T> callback;

        public ApiSubcriber(ResponseCallBack<T> callback) {
            this.callback = callback;
        }

        @Override
        public void onStart() {
            callback.onStart();
        }

        @Override
        public void onCompleted() {}

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
                callback.onFailure("无法连接到服务器，请检查网络！");
            } else if (e instanceof HttpException) {             //HTTP错误
                HttpException httpException = (HttpException) e;
                LogUtil.e("httpErrorCode : " + httpException.code());
                switch (httpException.code()) {
                    case UNAUTHORIZED:
                    case FORBIDDEN:
                        callback.onFailure("权限错误");         //权限错误，需要实现
                        break;
                    case NOT_FOUND:
                        callback.onFailure("访问的链接不存在");
                        break;
                    case REQUEST_TIMEOUT:
                    case GATEWAY_TIMEOUT:
                        callback.onFailure("无法连接到服务器，请检查网络！");
                        break;
                    case INTERNAL_SERVER_ERROR:
                    case BAD_GATEWAY:
                    case SERVICE_UNAVAILABLE:
                    default:
                        callback.onFailure(BuildConfig.DEBUG ? "服务器内部错误" : "无法连接到服务器，请检查网络！");
                        break;
                }
            } else if (e instanceof JsonParseException
                    || e instanceof JSONException) {
                callback.onFailure("数据解析错误");//均视为解析错误
            } else {
                callback.onFailure("无法连接到服务器，请检查网络！");//未知错误
            }
        }


        @Override
        public void onNext(String data) {
            if (data == null)
                return;

            DataResult result;
            try {
                result = JsonUtil.fromJsonObject(data, callback.getType());
            } catch (JsonSyntaxException e) {
                result = JsonUtil.fromJsonArray(data, callback.getType());
            }

            if ((TextUtils.equals(result.code, "RUN_ERROR")
                    || TextUtils.equals(result.code, "BIZ_ERROR")
                    || TextUtils.equals(result.code, "CHECK_ERROR"))) {
                callback.onFailure(result.message);
                return;
            }
            callback.onSuccess(result);
        }
    }
}
