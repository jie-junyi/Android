package com.example.expandablelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private List<String> classes;
    private List<List<String>> students;
    private Context context;
    View.OnClickListener ivGoToChildClickListener;

    public MyExpandableListAdapter() {

    }

    public MyExpandableListAdapter(List<String> classes, List<List<String>> students, Context context,
                                   View.OnClickListener ivGoToChildClickListener) {
        this.classes = classes;
        this.students = students;
        this.context = context;
        this.ivGoToChildClickListener = ivGoToChildClickListener;
    }

    @Override
    public int getGroupCount() {  //组的数量
        return classes.size();
    }

    @Override
    public int getChildrenCount(int i) {  //某组中子项数量
        return students.get(i).size();
    }

    @Override
    public Object getGroup(int i) {  //某组
        return classes.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {  //某子项
        return students.get(i).get(i1);

    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupHold groupHold;
        if (view == null) {
            view = (View) LayoutInflater.from(context).inflate(R.layout.item_elv_group, null);
            groupHold = new GroupHold();
            groupHold.tvGroupName = (TextView) view.findViewById(R.id.tv_groupName);
            groupHold.ivGoToChildLv = (ImageView) view.findViewById(R.id.iv_goToChildLV);

            view.setTag(groupHold);

        } else {
            groupHold = (GroupHold) view.getTag();

        }

        String groupName = classes.get(i);
        groupHold.tvGroupName.setText(groupName);

        //取消默认的groupIndicator后根据方法中传入的isExpand判断组是否展开并动态自定义指示器
        if (b) {   //如果组展开
            groupHold.ivGoToChildLv.setImageResource(R.mipmap.img_1);
        } else {
            groupHold.ivGoToChildLv.setImageResource(R.mipmap.img_2);
        }

        //setTag() 方法接收的类型是object ，所以可将position和converView先封装在Map中。Bundle中无法封装view,所以不用bundle
        Map<String, Object> tagMap = new HashMap<>();
        tagMap.put("groupPosition", i);
        tagMap.put("isExpanded", b);
        groupHold.ivGoToChildLv.setTag(tagMap);

        //图标的点击事件
        groupHold.ivGoToChildLv.setOnClickListener(ivGoToChildClickListener);

        return view;

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup convertView) {
        ChildHold childHold;
        if (view == null) {
            view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_elv_child, null);
            childHold = new ChildHold();
            childHold.tvChildName = (TextView) view.findViewById(R.id.tv_elv_childName);
            childHold.cbElvChild = (CheckBox) view.findViewById(R.id.cb_elvChild);
            convertView.setTag(childHold);
        } else {
            childHold = (ChildHold) view.getTag();
        }

        String childName = students.get(i).get(i1);
        childHold.tvChildName.setText(childName);

        childHold.cbElvChild.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context, "条目选中:" + classes.get(i) + "的" +
                            students.get(i).get(i1) + "被选中了", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "条目选中:" + classes.get(i) + "的" +
                            students.get(i).get(i1) + "被取消选中", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;    //默认返回false,改成true表示组中的子条目可以被点击选中
    }

    class GroupHold {
        TextView tvGroupName;
        ImageView ivGoToChildLv;
    }

    class ChildHold {
        TextView tvChildName;
        CheckBox cbElvChild;
    }

}
