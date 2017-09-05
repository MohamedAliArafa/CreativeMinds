package com.zeowls.creativemindstask.ui;

import com.zeowls.creativemindstask.models.response.GitHubResultModel;

import java.util.List;

/**
 * Created by root on 9/5/17.
 */

public interface MainContract {
    interface View {
        void updateUI(List<GitHubResultModel> models);

        void showProgress();

        void hideProgress();
    }

    interface UserAction {
        void getData();

        void openDetails(String repoURL, String ownerURL);

        void loadMoreItems();
    }
}
