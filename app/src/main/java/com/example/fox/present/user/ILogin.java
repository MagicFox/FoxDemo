package com.example.fox.present.user;

import com.example.fox.model.BankCardModel;
import com.example.fox.model.login.LoginResult;
import com.example.fox.present.IBasePresent;
import com.example.fox.present.IBaseView;

import java.util.List;

/**
 * Created by magicfox on 2017/5/19.
 */

public interface ILogin {
    interface IView extends IBaseView<IBasePresent> {
        boolean verifyLoginForm();
        void loginSuccess(LoginResult result);
        void loginFailed(String message);
        void loadSuccess(List<BankCardModel> data);
    }

    interface IPresent extends IBasePresent{
        void login(String name,String pwd);
        void load(String accountId);
    }
}
