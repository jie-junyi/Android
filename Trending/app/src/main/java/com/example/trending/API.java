package com.example.trending;

import com.example.trending.Bean.ItemsBean;

import java.util.List;

import io.reactivex.Observable;
import ren.yale.android.retrofitcachelib.anno.Cache;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    public interface M {        //M层请求业务
        void request(String language);
    }
    public interface VP{
        void refreshList(String language);
        void onSuccess(List<ItemsBean> list);
        void setData(List<ItemsBean> list);
        void Fail();
    }

    @Cache()
    @GET("repositories/")
    Observable<List<ItemsBean>> getList(@Query("lang")String language, @Query("since")String weekly);

}
