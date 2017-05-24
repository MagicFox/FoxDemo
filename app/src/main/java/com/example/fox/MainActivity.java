package com.example.fox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.blankj.utilcode.utils.ToastUtils;
import com.example.fox.base.BaseActivity;
import com.example.fox.model.BankCardModel;
import com.example.fox.model.login.LoginResult;
import com.example.fox.present.IBasePresent;
import com.example.fox.present.user.ILogin;
import com.example.fox.present.user.impl.LoginPresent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ILogin.IView{
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    ILogin.IPresent loginPresent;
    @BindView(R.id.etName)
    EditText etLoginName;
    @BindView(R.id.etPwd)
    EditText etLoginPwd;

    @Override
    protected void initViews() {
        loginPresent = new LoginPresent(this);
    }

    @OnClick(R.id.btnLogin)
    void login(){
        if(!verifyLoginForm()){
            return;
        }
        loginPresent.login(etLoginName.getText().toString(),etLoginPwd.getText().toString());
    }
    @OnClick(R.id.btnLoad)
    void load(){
        loginPresent.load("623e87430147497c8cd3573c25be7443");
    }

    @Override
    public boolean verifyLoginForm() {
        if(TextUtils.isEmpty(etLoginName.getText().toString())){
            ToastUtils.showShortToast(MainActivity.this,etLoginName.getHint().toString());
            return false;
        }
        if(TextUtils.isEmpty(etLoginPwd.getText().toString())){
            ToastUtils.showShortToast(MainActivity.this,etLoginPwd.getHint().toString());
            return false;
        }
        return true;
    }

    @Override
    public void loginSuccess(LoginResult result) {
        ToastUtils.showShortToast(this,"登录成功");
    }

    @Override
    public void loadSuccess(List<BankCardModel> data) {

    }

}
