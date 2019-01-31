package com.example.gitusersearch.repository

import retrofit2.http.GET
import retrofit2.http.Path

interface GithubRepo {
    @GET("/users/{username}")
    fun getUserByName(@Path("username") userName : String)

    @GET("/users/{username}/repos")
    fun getRepositoriesByUser(@Path("username") userName : String)
}