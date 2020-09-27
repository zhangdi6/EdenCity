package com.edencity.customer.base;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import com.edencity.customer.BuildConfig;
import com.edencity.customer.util.Logger;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class BaseRepository {

    public static <T, R> void observer(LifecycleProvider provider, Observable<T> observable, Function<T, ObservableSource<R>> function, final IBaseCallBack<R> callBack) {

        observable.flatMap(function)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider instanceof RxAppCompatActivity ? ((RxAppCompatActivity)provider).<R>bindUntilEvent(ActivityEvent.DESTROY)
                        :((RxFragment)provider).<R>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                 .subscribe(new Observer<R>() {
                     @Override
                     public void onSubscribe(Disposable d) {

                     }
                     @Override
                     public void onNext(R r) {
                         if(BuildConfig.DEBUG){
                             Logger.d("%s onNext = %s ", getClass().getSimpleName(), r.toString());
                         }
                         callBack.onSuccess(r);
                     }

                     @Override
                     public void onError(Throwable e) {
                         if(BuildConfig.DEBUG){
                             e.printStackTrace();
                             Logger.d("%s onError =  %s", getClass().getSimpleName(), e.getMessage());
                         }
                         callBack.onFail(e.getMessage());
                     }

                     @Override
                     public void onComplete() {

                     }
                 })
        ;
    }

    public static <T, R> void observerNoMap(LifecycleProvider provider, Observable<R> observable, final IBaseCallBack<R> callBack) {

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).compose(provider instanceof RxAppCompatActivity ? ((RxAppCompatActivity)provider).<R>bindUntilEvent(ActivityEvent.DESTROY)
                : ((RxFragment)provider).<R>bindUntilEvent(FragmentEvent.DESTROY_VIEW)).subscribe(new Observer<R>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(R r) {
                if(BuildConfig.DEBUG){
                    Logger.d("%s onNext = %s ", getClass().getSimpleName(), r.toString());
                }
                callBack.onSuccess(r);
            }

            @Override
            public void onError(Throwable e) {
                if(BuildConfig.DEBUG){
                    e.printStackTrace();
                    Logger.d("%s onError =  %s", getClass().getSimpleName(), e.getMessage());
                }
                callBack.onFail(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

}
