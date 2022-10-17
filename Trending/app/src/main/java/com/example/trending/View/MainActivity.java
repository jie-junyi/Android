package com.example.trending.View;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.trending.API;
import com.example.trending.Bean.ItemsBean;
import com.example.trending.R;
import com.example.trending.Presenter.Adapter;
import com.example.trending.Presenter.MainPresenter;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ren.yale.android.retrofitcachelib.RetrofitCache;

public class MainActivity extends BaseActivity<MainPresenter> implements API.VP {

    private RecyclerView mRecyclerView;
    private Button mButton;
    public static final String BaseURL = "https://private-80a4b1-githubtrendingapi.apiary-mock.com/";
    private final MainPresenter mPresenter = new MainPresenter();
    Adapter adapter;
    private List<ItemsBean> mList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String language = "Go";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        mPresenter.bindView(this);
        initListener();
        initData();
        initView();
        Fresco.initialize(this);
        RetrofitCache.getInstance().init(this).setDefaultTimeUnit(TimeUnit.HOURS).setDefaultTime(2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.java:
                language = "java";
                refreshList(language);
                return true;
            case R.id.c:
                language = "c";
                refreshList(language);
                return true;
            case R.id.python:
                language = "python";
                refreshList(language);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void initData() {
        refreshList(language);
    }

    @Override
    public void initView() {

        mRecyclerView = findViewById(R.id.re_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

//        Animation animation = AnimationUtils.loadAnimation(
//                this, R.anim.list_anim);
//        LayoutAnimationController lac = new LayoutAnimationController(animation);
//        lac.setDelay(0.4f);  //设置动画间隔时间
//        lac.setOrder(LayoutAnimationController.ORDER_NORMAL); //设置列表的显示顺序
//        mRecyclerView.setLayoutAnimation(lac);
//        lac.start();//为ListView 添加动画

        mSwipeRefreshLayout = findViewById(R.id.refresh);
        mSwipeRefreshLayout.setRefreshing(true);
        //mSwipeRefreshLayout.setColorSchemeResources(R.color.gray, R.color.purple_200, R.color.black);//设置下拉进度条颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.black);//设置下拉进度条颜色
        //监听器
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList(language);
            }
        });




    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initListener() {

    }

    @Override
    public MainPresenter getPresenterInstance() {
        return new MainPresenter();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void refreshList(String language) {
        mPresenter.refreshList(language);
    }

    @Override
    public void onSuccess(List<ItemsBean> list) {
        setContentView(R.layout.activity_main);
        initView();
        setData(list);
        //initData();

    }

    @Override
    public void setData(List<ItemsBean> list) {
        mList = list;
        adapter = new Adapter(mList);
        mRecyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void Fail() {
        setContentView(R.layout.error_page);
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshList(language);
            }
        });
    }

}