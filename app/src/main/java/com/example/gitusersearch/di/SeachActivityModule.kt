package com.example.gitusersearch.di

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitusersearch.ui.activities.UserSearchActivity
import com.example.gitusersearch.ui.adapters.RvRepositoryAdapter
import com.example.gitusersearch.viewModel.SearchViewModel
import dagger.Module
import dagger.Provides

@Module
class SeachActivityModule(var activity: UserSearchActivity){
    @Provides
    @GitHubAppScope
    fun getActivityContext(): UserSearchActivity{
        return activity
    }

    @Provides
    @GitHubAppScope
    fun getLayoutManager(activity: UserSearchActivity): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

    @Provides
    @GitHubAppScope
    fun getRvRepositoryAdapter(): RvRepositoryAdapter {
        return RvRepositoryAdapter()
    }

    @Provides
    @GitHubAppScope
    fun getSearchViewModel(activity: UserSearchActivity) : SearchViewModel {
        return ViewModelProviders.of(activity).get(SearchViewModel::class.java)
    }
}