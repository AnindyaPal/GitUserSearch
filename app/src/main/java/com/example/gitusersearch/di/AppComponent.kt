package com.example.gitusersearch.di

import com.example.gitusersearch.repository.GithubRepo
import dagger.Component

@GitHubAppScope
@Component(modules = [ApiRepositoryModule::class])
abstract class AppComponent {
    abstract fun getGitHubRepo() : GithubRepo
}