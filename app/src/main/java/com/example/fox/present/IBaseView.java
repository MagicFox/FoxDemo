package com.example.fox.present;

/**
 * Created by magicfox on 2017/5/19.
 */

public interface IBaseView<T> {

    void setPresent(T present);

    boolean isActivityFinish();

    //TODO show or hide,是否要分开写？还是一个方法解决显示隐藏
    void showLoading(boolean isShow);

    void showMessage(String message);

    void loginFailed(String message);

}
