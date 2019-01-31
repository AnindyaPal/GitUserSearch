package com.example.gitusersearch.di

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitusersearch.ui.activities.UserSearchActivity
import com.example.gitusersearch.ui.adapters.RvRepositoryAdapter
import com.example.gitusersearch.viewModel.SearchViewModel
import dagger.Module
import dagger.Provides

@Module
class SeachActivityModule(val activity: UserSearchActivity){
    @Provides
    fun getActivityContext(): UserSearchActivity{
        return activity
    }

    @Provides
    fun getLayoutManager(activity: UserSearchActivity): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

    @Provides
    fun getRvRepositoryAdapter(activity: UserSearchActivity) : RvRepositoryAdapter {
        return RvRepositoryAdapter(activity)
    }

    @Provides
    fun getSearchViewModel(activity: UserSearchActivity) : SearchViewModel {
        return ViewModelProviders.of(activity).get(SearchViewModel::class.java)
    }
}