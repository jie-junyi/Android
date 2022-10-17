package com.example.trending.Presenter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trending.Bean.ItemsBean;
import com.example.trending.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.samlss.broccoli.Broccoli;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<ItemsBean> mItemsBeans;
    private Map<View, Broccoli> mViewPlaceholderManager = new HashMap<>();
    private Map<View, Runnable> mTaskManager = new HashMap<>();

    public Adapter(List<ItemsBean> itemsBeans) {
        this.mItemsBeans = itemsBeans;
    }

    public void setList(List<ItemsBean> itemsBeans) {
        this.mItemsBeans = itemsBeans;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final SimpleDraweeView mView;
        private final TextView mName;
        private final TextView mRepo;
        private final TextView mDes;
        private final TextView mStars;
        private final TextView mForks;
        private ConstraintLayout mConcrete;
        private LinearLayout mSummary;
        private int opened = -1;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.imageView);
            mName = itemView.findViewById(R.id.user);
            mRepo = itemView.findViewById(R.id.repo);
            mDes = itemView.findViewById(R.id.des);
            mStars = itemView.findViewById(R.id.star_count);
            mForks = itemView.findViewById(R.id.fork_count);

            mConcrete = itemView.findViewById(R.id.constraintLayout1);
            mSummary = itemView.findViewById(R.id.summary);
            mSummary.setOnClickListener(this);
        }

        void bindView(int pos, ItemsBean items) {

            Uri url = Uri.parse(items.getAvatar());
            mView.setImageURI(url);
            mName.setText(items.getAuthor());
            mRepo.setText(items.getName());
            mStars.setText(String.valueOf(items.getStars()));
            mForks.setText(String.valueOf(items.getForks()));

            if (items.getDescription()!= null) {
                mDes.setText(items.getDescription());
            }

            if (pos == opened) {
                mConcrete.setVisibility(View.VISIBLE);
            } else {
                mConcrete.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            if (opened == getAdapterPosition()) {
                opened = -1;
                notifyItemChanged(getAdapterPosition());
            } else {
                int oldOpened = opened;
                opened = getAdapterPosition();
                notifyItemChanged(oldOpened);
                notifyItemChanged(opened);
            }
        }
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        //为item的每一项设置占位符
        BroccoliManager.initRecyclerView(holder.itemView,holder.mView,holder.mName,holder.mRepo);
        //delay to show the data
        Runnable task = mTaskManager.get(holder.itemView);
        if (task == null) {

            task = new Runnable() {
                @Override
                public void run() {
                    //when you need to update data, you must to call the remove or the clear method.
                    BroccoliManager.byItemViewClear(holder.itemView);

                    if (mItemsBeans.isEmpty()) {
                        return;
                    }

                   holder.bindView(position,mItemsBeans.get(position));
                }
            };
            mTaskManager.put(holder.itemView, task);
        } else {
            holder.itemView.removeCallbacks(task);
        }
        holder.itemView.postDelayed(task, 100);
        holder.bindView(position,mItemsBeans.get(position));
    }
        //holder.bindView(position, mItemsBeans.get(position));
    @Override
    public int getItemCount() {
        return mItemsBeans.size() ;
    }
}


