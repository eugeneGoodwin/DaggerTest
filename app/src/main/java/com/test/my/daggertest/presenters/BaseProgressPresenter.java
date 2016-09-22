package com.test.my.daggertest.presenters;

import android.app.ProgressDialog;
import android.content.Context;

import com.test.my.daggertest.R;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

public class BaseProgressPresenter<T extends BasePresenterInterface> extends BasePresenter<T>{
    static ProgressDialog progress;

    public void showProgress() {
        Context context = view.getContext();
        if (progress == null) {
            progress = ProgressDialog.show(context, "", context.getString(R.string.loading));
        } else {
            progress.show();
        }
    }

    public void hideProgress() {
        if (progress != null) {
            progress.dismiss();
            progress = null;
        }
    }

    @Override
    public void retrofitError(Response res, Throwable ex){
        if (res != null) {
            if(res.code() == 403){
                // relogin
            }
            else if(res.code() == 504){
                // no internet connection
                //Context context = view.getContext();
            }
        }
        if (ex != null) {
            // exception
        }
        if(ex instanceof HttpException){
            if(((HttpException)ex).code() == 403){
            }

        }
    }
}
