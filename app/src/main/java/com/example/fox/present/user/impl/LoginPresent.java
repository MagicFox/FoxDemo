package com.example.fox.present.user.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.blankj.utilcode.utils.ToastUtils;
import com.example.fox.http.Api;
import com.example.fox.http.HttpUtils;
import com.example.fox.http.callback.ResponseCallBack;
import com.example.fox.model.BankCardModel;
import com.example.fox.model.DataResult;
import com.example.fox.model.login.LoginRequest;
import com.example.fox.model.login.LoginResult;
import com.example.fox.present.user.ILogin;
import com.example.fox.utils.LogUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by magicfox on 2017/5/19.
 */

public class LoginPresent implements ILogin.IPresent{
    ILogin.IView iView;
    public LoginPresent(ILogin.IView iView){
        this.iView = iView;
    }

    @Override
    public void login(String name, String pwd) {
        LoginRequest request = new LoginRequest();
        request.mobile = name;
        request.captcha = pwd;
        request.device = "testafdasfd";

        HttpUtils.getInstance().httpPost(Api.LOGIN, request, new ResponseCallBack() {
            @Override
            public void onStart() {
                iView.showLoading(true);
            }

            @Override
            public void onSuccess(DataResult result) {
                iView.showLoading(false);
                iView.loginSuccess((LoginResult)result.detail);
            }

            @Override
            public void onFailure(String errorMsg) {
                iView.showLoading(false);
                iView.loginFailed(errorMsg);
            }

            @NonNull
            @Override
            public Class getType() {
                return LoginResult.class;
            }

            @Override
            public Type getType2() {
                return new TypeToken<DataResult<LoginResult>>(){}.getType();
            }
        });
    }

    @Override
    public void load(String accountId) {
        Map<String,String> map = new HashMap<>();
        map.put("accountId", accountId);   //账户ID
        HttpUtils.getInstance().httpPost(Api.QUERY_BANKCARD_LIST, map, new ResponseCallBack() {
            @Override
            public void onStart() {
                iView.showLoading(true);
            }

            @Override
            public void onSuccess(DataResult result) {
                iView.showLoading(false);
                LogUtil.d("_______成功");
            }

            @Override
            public void onFailure(String errorMsg) {
                iView.showLoading(false);
                iView.loginFailed(errorMsg);
            }

            @NonNull
            @Override
            public Class<BankCardModel> getType() {
                return BankCardModel.class;
            }

            @Override
            public Type getType2() {
                return new TypeToken<DataResult<List<BankCardModel>>>(){}.getType();
            }
        });

    }

    @Override
    public void start() {

    }
}
