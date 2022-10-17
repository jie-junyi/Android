package com.example.trending.Presenter;

import com.example.trending.API;
import com.example.trending.Bean.ItemsBean;
import com.example.trending.Model.MyModel;
import com.example.trending.View.MainActivity;
import com.example.trending.Presenter.BasePresenter;

import java.util.List;

public class MainPresenter extends BasePresenter<MyModel, MainActivity> implements API.VP{

    public void bindView(MainActivity mView){ //绑定View
        this.mView = mView;
    }
    public void unBindView(){
        this.mView = null;
    }

    @Override
    public MyModel getModelInstance() {
        return new MyModel(this);
    }

    @Override
    public void refreshList(String language) {
        mModel.request(language);
    }

    @Override
    public void onSuccess(List<ItemsBean> list) {
        mView.onSuccess(list);
    }

    @Override
    public void setData(List list) {
        mView.setData(list);
    }

    @Override
    public void Fail() {
        mView.Fail();
    }
}
