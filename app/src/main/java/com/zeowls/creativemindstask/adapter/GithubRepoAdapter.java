package com.zeowls.creativemindstask.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zeowls.creativemindstask.R;
import com.zeowls.creativemindstask.models.response.GitHubResultModel;
import com.zeowls.creativemindstask.ui.MainContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mohamed.arafa on 8/28/2017.
 */

public class GithubRepoAdapter extends RecyclerView.Adapter<GithubRepoAdapter.MyViewHolder> {

    private final Context mContext;
    private List<GitHubResultModel> mList = new ArrayList<>();
    private int mLayout;
    private MainContract.UserAction mPresenter;

    public void clear() {
        mList.clear();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTitleTextView;

        @BindView(R.id.tv_desc)
        TextView mDescTextView;

        @BindView(R.id.tv_user_name)
        TextView mUserNameTextView;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public GithubRepoAdapter(Context context, int layout, MainContract.UserAction presenter) {
        mContext = context;
        mLayout = layout;
        mPresenter = presenter;
    }

    public void updateData(List<GitHubResultModel> list) {
        mList.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(mLayout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final GitHubResultModel model = mList.get(position);
        holder.mTitleTextView.setText(model.getName());
        holder.mDescTextView.setText(model.getDescription());
        holder.mUserNameTextView.setText(model.getOwner().getLogin());
        if (model.getFork()) {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
        }
        holder.itemView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mPresenter.openDetails(model.getHtmlUrl(), model.getOwner().getHtmlUrl());
                        return true;
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }
}
