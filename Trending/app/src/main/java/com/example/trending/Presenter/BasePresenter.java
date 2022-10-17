package com.example.trending.Presenter;

import com.example.trending.Model.BaseModel;
import com.example.trending.View.BaseActivity;

public abstract class BasePresenter<M extends BaseModel,V extends BaseActivity> {

    public V mView;
    public M mModel;

    public BasePresenter(){
        this.mModel = getModelInstance();
    }

    public void bindView(V mView){ //绑定View
        this.mView = mView;
    }
    public void unBindView(){
        this.mView = null;
    }

    public abstract M getModelInstance();


}
