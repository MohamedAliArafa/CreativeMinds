package com.zeowls.creativemindstask.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.zeowls.creativemindstask.MyApplication;
import com.zeowls.creativemindstask.models.response.GitHubResultModel;
import com.zeowls.creativemindstask.utilities.RepoDialogClass;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

/**
 * Created by root on 9/5/17.
 */

public class MainPresenter implements MainContract.UserAction, LifecycleObserver {

    private MainContract.View mView;
    private List<GitHubResultModel> mModel;
    private Context mContext;
    Call<List<GitHubResultModel>> mGetSquareReposCall;
    private Realm mRealm;

    private static int mPageNum = 0;
    private static int mPageSize = 10;

    private Boolean isLastPage = false;

    public MainPresenter(MainContract.View view, Context context, LifecycleRegistry lifecycle) {
        mView = view;
        mContext = context;
        lifecycle.addObserver(this);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    @Override
    public void getData() {
        isLastPage = false;
        mView.showProgress();
        mRealm = Realm.getDefaultInstance();
        List<GitHubResultModel> query = mRealm.where(GitHubResultModel.class).findAll();
        if (query != null) {
            mModel = query;
            mView.hideProgress();
            mView.updateUI(mModel);
        }
        mGetSquareReposCall = MyApplication.get(mContext).getApiService().getSquareRepos(mPageNum, mPageSize);
        mGetSquareReposCall.enqueue(new Callback<List<GitHubResultModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GitHubResultModel>> call, @NonNull Response<List<GitHubResultModel>> response) {
                mModel = response.body();
                mView.hideProgress();

                mRealm.beginTransaction();
                mRealm.copyToRealmOrUpdate(mModel);
                mRealm.commitTransaction();

                mView.updateUI(mModel);
            }

            @Override
            public void onFailure(@NonNull Call<List<GitHubResultModel>> call, @NonNull Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                mView.hideProgress();
            }
        });
    }

    @Override
    public void openDetails(String repoURL, String ownerURL) {
        RepoDialogClass dialogClass = new RepoDialogClass(mContext, repoURL, ownerURL);
        dialogClass.show();
    }

    @Override
    public void loadMoreItems() {
        if (isLastPage)
            return;
        mView.showProgress();
        mRealm = Realm.getDefaultInstance();
        List<GitHubResultModel> query = mRealm.where(GitHubResultModel.class).findAll();
        if (query != null) {
            mModel = query;
            mView.hideProgress();
            mView.updateUI(mModel);
        }
        mGetSquareReposCall = MyApplication.get(mContext).getApiService().getSquareRepos(mPageNum, mPageSize);
        mGetSquareReposCall.enqueue(new Callback<List<GitHubResultModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GitHubResultModel>> call, @NonNull Response<List<GitHubResultModel>> response) {
                mView.hideProgress();
                if (!response.isSuccessful()) {
                    int responseCode = response.code();
                    if (responseCode == 504) { // 504 Unsatisfiable Request (only-if-cached)
                        Toast.makeText(mContext, "Error 504", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

                mModel = response.body();
                if (mModel != null) {

                    if (mModel.size() >= PAGE_SIZE) {
                        mView.updateUI(mModel);
                    } else {
                        isLastPage = true;
                    }

                }
                mRealm.beginTransaction();
                mRealm.copyToRealmOrUpdate(mModel);
                mRealm.commitTransaction();

                mPageNum++;
            }

            @Override
            public void onFailure(@NonNull Call<List<GitHubResultModel>> call, @NonNull Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                mView.hideProgress();
            }
        });
    }

}
