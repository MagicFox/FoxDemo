package com.fox.datepicker.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

	private static final Gson G = new GsonBuilder().registerTypeAdapter(Double.class,  new JsonSerializer<Double>() {

		public JsonElement serialize(Double src, Type typeOfSrc,
									 JsonSerializationContext context) {
			Integer value = (int)Math.round(src);
			if(value.intValue() == src.doubleValue()){
				return new JsonPrimitive(value);
			}else {
				return new JsonPrimitive(src);
			}
		}
	}).disableHtmlEscaping().create();//.serializeNulls()

	private JsonUtil() {

	}

	public static <T> T fromJson2(String json, Type type) {
		if(TextUtils.isEmpty(json) || TextUtils.equals("null",json)){
			return null;
		}
		try{
			return G.fromJson(json, type);
		} catch (JsonSyntaxException e){
			return null;
		}
	}

	public static <T> T fromJson(String json, Class<T> classOfT) {
		if(TextUtils.isEmpty(json) || TextUtils.equals("null",json)){
			return null;
		}
		try{
			return G.fromJson(json, classOfT);
		}catch (JsonSyntaxException e){
			return null;
		}
	}

	public static <T> ArrayList<T> stringToArray(String s) {
		Gson g = new Gson();
		Type listType = new TypeToken<ArrayList<T>>(){}.getType();
		ArrayList<T> list = g.fromJson(s, listType);
		return list;
	}

//	public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
//		T[] arr = new Gson().fromJson(s, clazz);
//		return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
//	}

	public static <T> T fromJson(String json, Type type) {
		if(TextUtils.isEmpty(json) || TextUtils.equals("null",json)){
			return null;
		}
		try{
			return G.fromJson(json, type);
		} catch (JsonSyntaxException e){
			return null;
		}
	}

	public static <T> String toJson(T t) {
		return G.toJson(t);
	}

	/**
	 * 参数值转字符串
	 * @param jsonParam
	 * @return
	 */
	public static String paramValue2String(Object jsonParam){
		if(jsonParam instanceof String){
			return (String) jsonParam;
		}else{
			return toJson(jsonParam);
		}
	}

	static ParameterizedType type(final Class raw, final Type... args) {
		return new ParameterizedType() {
			public Type getRawType() {
				return raw;
			}

			public Type[] getActualTypeArguments() {
				return args;
			}

			public Type getOwnerType() {
				return null;
			}
		};
	}
}
