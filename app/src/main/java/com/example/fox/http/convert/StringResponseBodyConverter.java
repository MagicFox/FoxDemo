package com.example.fox.http.convert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by magicfox on 2017/5/5.
 */

public class StringResponseBodyConverter implements Converter<ResponseBody, Object>{

    @Override
    public Object convert(ResponseBody value) throws IOException {
        if(value == null || value.byteStream() == null)return "";
        BufferedReader r = new BufferedReader(new InputStreamReader(value.byteStream()));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }
}
