package com.zeowls.creativemindstask.service;

import com.zeowls.creativemindstask.models.response.GitHubResultModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mohamed.arafa on 8/27/2017.
 */

public interface ApiService {

    @GET("users/square/repos")
    Call<List<GitHubResultModel>> getSquareRepos(@Query("page") int page, @Query("per_page") int pageSize);

}
