package com.example.gitusersearch.di

import com.example.gitusersearch.repository.GithubRepo
import com.example.gitusersearch.viewModel.SearchViewModel
import dagger.Component

@GitHubAppScope
@Component(modules = [ApiRepositoryModule::class])
abstract class AppComponent {
    abstract fun getGitHubRepo() : GithubRepo
    abstract fun injectSearchViewModel(searchViewModel: SearchViewModel)
}