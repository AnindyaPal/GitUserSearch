package com.example.gitusersearch.di.activity

import com.example.gitusersearch.di.GitHubAppScope
import com.example.gitusersearch.ui.activities.UserSearchActivity
import dagger.Component

@GitHubAppScope
@Component(modules = [SeachActivityModule::class])
abstract class SearchActivityComponent {
    abstract fun injectSearchActivity(searchActivity: UserSearchActivity)
}