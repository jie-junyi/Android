package com.example.trending.Model;

import com.example.trending.Presenter.BasePresenter;

public class BaseModel<P extends BasePresenter> {

    public P mPresenter;

    public BaseModel(P mPresenter){
        this.mPresenter = mPresenter;
    }
}
