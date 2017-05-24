package com.example.fox.utils;


import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by magicfox on 2017/4/27.
 */

public class RequestParamsUtils {
    /**
     * 区分post上传普通参数还是文件 (文本类型和文件类型,可传json)
     * 多文件，使用数组:File[]
     * @param params 参数map
     * @return bodyParams 请求参数
     */
    public static HashMap<String, RequestBody> postFileParams(Map<String, Object> params) {
        HashMap<String, RequestBody> bodyParams = new HashMap<>();
        Iterator<String> iterator = params.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = params.get(key);
            if (value instanceof String) {
                LogUtil.d("______request parameter key="+key,",value="+value);
                bodyParams.put(key, RequestBody.create(MediaType.parse("text/plain"), (String) value));
            } else if (value instanceof File) {//单个文件
                LogUtil.d("______request file key="+key,"name="+((File) value).getName());
//                bodyParams.put(key, RequestBody.create(MediaType.parse("image/*"), (File) value));
                bodyParams.put(key+"\"; filename=\""+((File) value).getName()+"", RequestBody.create(MediaType.parse("image/*"), (File) value));
            } else if(value instanceof File[]){//多文件
                File[] files = (File[]) value;
                if(!GenericUtil.isEmpty(files)){
                    for(File f:files){
                        if(f == null || !f.exists())continue;
                        LogUtil.d("______request multi file key="+key,"name="+f.getName());
                        bodyParams.put(key+"\"; filename=\""+f.getName()+"", RequestBody.create(MediaType.parse("image/*"), f));
                    }
                }
            }
        }
        return bodyParams;
    }

    /**
     * get方法将参数和url 拼接
     * @param url    请求url
     * @param params 请求参数
     * @return 拼接后 url
     */
    public static String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
