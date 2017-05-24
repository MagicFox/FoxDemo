package com.example.fox.http;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by magicfox on 2017/4/27.
 */

public interface RequestHttpApi {

    @GET
    Observable<String> httpGetString(@Url String url);

    /**
     * also file,and Form
     * */
    @Multipart
    @POST
    Observable<String> httpPostOrFile(@Url String url, @PartMap() Map<String, RequestBody> map);

    /**
     * only json
     * */
    @POST
    Observable<String> httpPost(@Url String url, @Body Object map);

    @GET
    Observable<String> httpGet(@Url String url);
}
