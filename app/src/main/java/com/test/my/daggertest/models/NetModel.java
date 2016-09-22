package com.test.my.daggertest.models;

import com.test.my.daggertest.interfaces.API;
import com.test.my.daggertest.interfaces.Callback;
import com.test.my.daggertest.presenters.BasePresenter;
import com.test.my.daggertest.retrofit.entries.JsonPost;
import com.test.my.daggertest.retrofit.entries.JsonUser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NetModel {

    BasePresenter presenter;
    Retrofit retrofit;
    private static API api;

    public static API getApi() {
        return api;
    }

    public NetModel(Retrofit retrofit){
        this.retrofit = retrofit;
        api = this.retrofit.create(API.class);
    }

    public void attachPresenter(BasePresenter t){
        presenter = t;
    }

    public void getPosts(Callback<List<JsonPost>> callback){
        List<JsonPost> posts = new ArrayList<>();
        getApi().getPosts().subscribeOn(Schedulers.newThread())
                .concatMap(list -> rx.Observable.from(list))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listPost ->  { posts.addAll(listPost);
                                            callback.T(posts);},
                            error -> { if(presenter != null)
                                            presenter.retrofitError(null, error);}
                );
    }

    public void getUsers(Callback<List<JsonUser>> callback){
        List<JsonUser> users = new ArrayList<>();
        getApi().getUsers()
                .subscribeOn(Schedulers.newThread())
                .concatMap(list -> rx.Observable.from(list))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listUser ->  { users.addAll(listUser);
                            callback.T(users);},
                        error -> { if(presenter != null)
                            presenter.retrofitError(null, error);}
                );
    }
}
