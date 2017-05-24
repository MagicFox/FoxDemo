package com.example.fox.http;

import android.os.Looper;
import android.text.TextUtils;

import com.example.fox.common.Constant;
import com.example.fox.http.callback.FileDownloadCallback;
import com.example.fox.http.callback.FileDownloadTask;
import com.example.fox.http.callback.ResponseCallBack;
import com.example.fox.http.convert.StringConverterFactory;
import com.example.fox.model.DataResult;
import com.example.fox.utils.JsonUtil;
import com.example.fox.utils.LogUtil;
import com.example.fox.utils.RequestParamsUtils;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *
 */
public class HttpUtils {

    private RetrofitManager retrofitUtils;

    private HttpUtils(){
        retrofitUtils = RetrofitManager.getInstance();
    }

    private static class HttpUtilsHolder{
        static HttpUtils instance = new HttpUtils();
    }

    public static HttpUtils getInstance() {
        return HttpUtilsHolder.instance;
    }

    public void download(String url, File target) {
        download(url, target, null);
    }

    /**
     * download file
     * @param url
     * @param target saved file
     * @param callback
     */
    public void download(String url, File target, FileDownloadCallback callback) {
        if (!TextUtils.isEmpty(url) && target != null) {
            FileDownloadTask task = new FileDownloadTask(url, target, callback);
            task.execute();
        }
    }

    /**
     * post
     * @param url
     * @param map
     * @param callback
     */
    public <T> void httpPost(String url, T map, final ResponseCallBack callback) {
        if(callback != null) {
            callback.onStart();
        }
        if(Constant.isEasy){
        } else {
            Observable<String> observable = retrofitUtils.createApi(RequestHttpApi.class, StringConverterFactory.create()).httpPost(url, map);
            handleResponse(observable,callback);
        }
    }

    /**
     * http post request(can post file)
     *
     * @param url http url
     * @param map  request Parameter
     * @param callback  Result CallBack
     **/
    public void httpPostFile(String url, Map<String, Object> map,final ResponseCallBack callback) {
        if(callback != null){
            callback.onStart();
        }
        Observable<String> observable = retrofitUtils.createApi(RequestHttpApi.class,StringConverterFactory.create()).httpPostOrFile(url, RequestParamsUtils.postFileParams(map));
        handleResponse(observable,callback);
    }

    public <T> void handleResponse(final Observable<String> observable, final ResponseCallBack<T> callBack){
        if(callBack == null || observable == null)return;
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Action1<String>() {
//                    @Override
//                    public void call(String result) {
//                        LogUtil.e(Looper.myLooper() == Looper.getMainLooper());
//                    }
//                })
                .observeOn(Schedulers.io()).map(new Func1<String, T>() {
                    @Override
                    public T call(String result) {
                        return JsonUtil.fromJson(result, callBack.getType2());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<T>() {

                    @Override
                    protected void onError(String errorMsg) {
                        if(callBack != null) {
                            callBack.onFailure(errorMsg);
                        }
                    }

                    @Override
                    public void onNext(T data) {
                        if(data == null){
                            callBack.onFailure("数据为空");
                            return;
                        }
                        DataResult<T> result = (DataResult<T>)data;
                        if(result == null){
                            callBack.onFailure("数据为空");
                            return;
                        }

                        if ((TextUtils.equals(result.code, "RUN_ERROR")
                                || TextUtils.equals(result.code, "BIZ_ERROR")
                                || TextUtils.equals(result.code, "CHECK_ERROR"))) {
                            callBack.onFailure(result.message);
                            return;
                        }
                        callBack.onSuccess(result);
                    }
                });
    }
}
