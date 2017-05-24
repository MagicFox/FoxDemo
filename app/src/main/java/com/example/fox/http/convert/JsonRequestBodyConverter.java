package com.example.fox.http.convert;


import com.example.fox.utils.JsonUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by magicfox on 2017/5/5.
 */

public class JsonRequestBodyConverter implements Converter<Object, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(Object value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, JsonUtil.toJson(value));
    }

}
