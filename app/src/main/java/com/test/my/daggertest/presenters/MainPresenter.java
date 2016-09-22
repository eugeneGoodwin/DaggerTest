package com.test.my.daggertest.presenters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.test.my.daggertest.Application;
import com.test.my.daggertest.R;
import com.test.my.daggertest.databinding.ItemLayoutBinding;
import com.test.my.daggertest.interfaces.Callback;
import com.test.my.daggertest.models.NetModel;
import com.test.my.daggertest.retrofit.entries.JsonPost;
import com.test.my.daggertest.rxrecycleradapter.RxDataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

public class MainPresenter extends BaseProgressPresenter<MainPresenter.View>{

    @Inject
    NetModel netModel;

    List<JsonPost> dataSet = new ArrayList<>();
    RxDataSource<JsonPost> rxDataSource;

    public void init(){

        Application.getApplication().getNetComponent().inject(this);

        if(netModel != null)
            netModel.attachPresenter(this);

        RecyclerView recyclerView = view.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        rxDataSource = new RxDataSource<>(dataSet);
        rxDataSource.map(p -> p)
                .<ItemLayoutBinding>bindRecyclerView(recyclerView, R.layout.item_layout)
                .subscribe(viewHolder -> {
                    ItemLayoutBinding b = viewHolder.getViewDataBinding();
                    b.userId.setText(String.valueOf(viewHolder.getItem().getId()));
                    b.title.setText(viewHolder.getItem().getTitle());
                    b.description.setText(viewHolder.getItem().getBody());
                });


        update();
    }

    public void update(){
        showProgress();
        netModel.getPosts(new Callback<List<JsonPost>>(){
            @Override
            public void T(List<JsonPost> list){
                hideProgress();
                dataSet.clear();
                dataSet.addAll(list);
                rxDataSource.updateDataSet(dataSet).map(json -> {
                                                                    json.setTitle(json.getTitle().toUpperCase());
                                                                    return json;
                                                                }).updateAdapter();
            }
        });
    }

    public void start() {
        update();
    }

    @Override
    public void retrofitError(Response res, Throwable ex){
        super.retrofitError(res, ex);
        if(ex != null) {
            TextView errorView = view.getStatus();
            errorView.setText(ex.getMessage());
        }
    }

    public interface View extends BasePresenterInterface {
        RecyclerView getRecyclerView();
        TextView getStatus();
    }
}
