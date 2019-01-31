package com.example.gitusersearch.repository

import com.example.gitusersearch.models.RepoModel
import com.example.gitusersearch.models.UserModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubRepo {
    @GET("/users/{username}")
    fun getUserByName(@Path("username") userName : String) : Deferred<UserModel>

    @GET("/users/{username}/repos")
    fun getRepositoriesByUser(@Path("username") userName : String) : Deferred<List<RepoModel>>
}