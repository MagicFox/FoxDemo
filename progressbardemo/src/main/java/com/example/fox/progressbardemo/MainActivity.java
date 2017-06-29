package com.example.fox.progressbardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fox.progressbardemo.view.UpdateLoadingProgressBar;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {
    UpdateLoadingProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar = (UpdateLoadingProgressBar) findViewById(R.id.round_flikerbar);
        bar.setProgress(0);


        Subscription mSubscribe = countDownObservable(100)
//                .doOnSubscribe(() -> {mTvWaitTime.setEnabled(false);})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
//                        mTvWaitTime.setEnabled(true);
//                        mTvWaitTime.setText(R.string.register_identify_code);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long aLong) {
//                        progressBar.setProgress(aLong);
//                        progressBar.incrementProgressBy(2);
                        bar.setProgress(bar.getProgress()+2);
//                        mTvWaitTime.setText(aLong + "s");
                    }
                });

    }

    /**
     * 倒计时
     * @param seconds
     * @return
     */
    public Observable<Long> countDownObservable(int seconds) {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(seconds + 1)
                .map(aLong -> seconds - aLong);
    }

}
