package com.example.fox.present;

import com.example.fox.BasePrint;
import com.example.fox.BuildConfig;
import com.example.fox.model.BankCardModel;
import com.example.fox.model.login.LoginResult;
import com.example.fox.present.user.ILogin;
import com.example.fox.present.user.impl.LoginPresent;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

/**
 * Created by magicfox on 2017/5/24.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TestPresent extends BasePrint{
    @Test
    public void test(){
        LoginPresent present = new LoginPresent(new ILogin.IView() {
            @Override
            public boolean verifyLoginForm() {
                return false;
            }

            @Override
            public void loginSuccess(LoginResult result) {

                println("loginSuccess");
            }

            @Override
            public void loginFailed(String message) {
                println("loginFailed");
                showMessage(" error");
            }

            @Override
            public void loadSuccess(List<BankCardModel> data) {

                println("loadSuccess");
            }

            @Override
            public void setPresent(IBasePresent present) {

            }

            @Override
            public boolean isActivityFinish() {
                return false;
            }

            @Override
            public void showLoading(boolean isShow) {
                println("showLoading");

            }

            @Override
            public void showMessage(String message) {
                println("showMessage="+message);
            }
        });

        present.login("18621774835","6666");
        present.load("asfa");
    }
}
