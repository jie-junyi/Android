package com.example.expandablelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpandableListViewActivity extends AppCompatActivity {

    private View.OnClickListener ivCoToChildClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandablelv);

        init();

    }

    private void init() {
        final ExpandableListView elv01 = (ExpandableListView) findViewById(R.id.evl_01);

        final List<String> classes = new ArrayList<>();
        classes.add("一班");
        classes.add("二班");
        classes.add("三班");

        List<List<String>> students = new ArrayList<>();
        List<String> child1Data = new ArrayList<>();
        child1Data.add("小学一年级");
        child1Data.add("小学二年级");
        child1Data.add("小学三年级");
        child1Data.add("小学四年级");
        child1Data.add("小学五年级");
        child1Data.add("小学六年级");
        List<String> child2Data = new ArrayList<>();
        child2Data.add("初一");
        child2Data.add("初二");
        child2Data.add("初三");
        List<String> child3Data = new ArrayList<>();
        child3Data.add("高一");
        child3Data.add("高二");
        child3Data.add("高三");
        students.add(child1Data);
        students.add(child2Data);
        students.add(child3Data);

        ivCoToChildClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取被点击图标所在的group的索引
                Map<String, Object> map = (Map<String, Object>) v.getTag();
                int groupPosition = (int) map.get("groupPosition");
//                boolean isExpand = (boolean) map.get("isExpanded");   //这种是通过tag传值
                boolean isExpand = elv01.isGroupExpanded(groupPosition);    //判断分组是否展开

                if (isExpand) {
                    elv01.collapseGroup(groupPosition);
                } else {
                    elv01.expandGroup(groupPosition);
                }
            }
        };
        //创建并设置适配器
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(classes, students, this,
                ivCoToChildClickListener);
        elv01.setAdapter(adapter);

        //默认展开第一个分组
        elv01.expandGroup(0);

        //展开某个分组时，并关闭其他分组。注意这里设置的是 ExpandListener
        elv01.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //遍历 group 的数组（或集合），判断当前被点击的位置与哪个组索引一致，不一致就合并起来。
                for (int i = 0; i < classes.size(); i++) {
                    if (i != groupPosition) {
                        elv01.collapseGroup(i); //收起某个指定的组
                    }
                }
            }
        });

        //点击某个分组时，跳转到指定Activity
        elv01.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(ExpandableListViewActivity.this, "组被点击了，跳转到具体的Activity", Toast.LENGTH_SHORT).show();
                return true;    //拦截点击事件，不再处理展开或者收起
            }
        });

        //某个分组中的子View被点击时的事件
        elv01.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
                                        long id) {

                Toast.makeText(ExpandableListViewActivity.this, "组中的条目被点击：" + classes.get(groupPosition) + "的" +
                        students.get(groupPosition).get(childPosition) + "放学后到校长办公室", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }
}