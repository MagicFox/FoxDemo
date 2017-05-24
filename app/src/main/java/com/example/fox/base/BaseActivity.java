package com.example.fox.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.blankj.utilcode.utils.ToastUtils;
import com.cm.rxbus.RxBus;
import com.example.fox.R;
import com.example.fox.present.IBasePresent;
import com.example.fox.present.IBaseView;
import com.example.fox.utils.ActivityManagerTool;
import com.example.fox.widght.CustomerToolbar;
import com.example.fox.widght.LoadingProgressDialog;

import butterknife.ButterKnife;

/**
 * Created by magicfox on 2017/5/19.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView<IBasePresent> {
    private View mView;
    protected CustomerToolbar mCustomToolbar;
    private LoadingProgressDialog mLoading;

    //lift circle start ++++++++++++++++++++++++++++++++++++++++
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.common_view);

        // base setup
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        if (isBindRxBusHere()) {
            RxBus.getDefault().register(this);
        }

        setToolbar();
        initViewStub();
        initViews();

        ActivityManagerTool.getActivityManager().add(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 不保存fragment, 解决activity被回收导致fragment的getActivity为null
        // super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerTool.getActivityManager().removeActivity(this);
        if (isBindRxBusHere()) {
            RxBus.getDefault().unRegister(this);
        }
    }
    //view start ++++++++++++++++++++++++++++++++++++++++
    protected  void initViewStub(){
        mView = getLayoutInflater().inflate(getContentViewLayoutID(),null);
        ((FrameLayout) findViewById(R.id.fl_content)).addView(mView);
        ButterKnife.bind(this,mView);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    //abstract start ++++++++++++++++++++++++++++++++++++++++

    protected void getBundleExtras(Bundle extras){}

    protected abstract int getContentViewLayoutID();

    protected abstract void initViews();

    /**
     * 是否接收RxBus发送的数据
     * @return
     */
    protected boolean isBindRxBusHere(){
        return false;
    }
    //title bar start ++++++++++++++++++++++++++++++++++++++++
    public void showTitle(String title){
        mCustomToolbar.setTitle(title);
    }

    protected  void setToolbar(){
        mCustomToolbar = (CustomerToolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mCustomToolbar);
        mCustomToolbar.setLeftBtnListener(new CustomerToolbar.Listener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //activity start ++++++++++++++++++++++++++++++++++++++++
    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    //present start ++++++++++++++++++++++++++++++++++++++++

    @Override
    public void setPresent(IBasePresent present) {

    }

    @Override
    public boolean isActivityFinish() {
        return isFinishing();
    }

    @Override
    public void showLoading(boolean isShow) {
        if(isShow){
            if (mLoading == null) {
                mLoading = new LoadingProgressDialog(this);
            }
            mLoading.show();
        } else {
            if (mLoading != null) {
                mLoading.dismiss();
            }
        }
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.showShortToast(getApplicationContext(),message);
    }

    @Override
    public void loginFailed(String message) {
        showMessage(message);
    }
    //present end ++++++++++++++++++++++++++++++++++++++++
}
