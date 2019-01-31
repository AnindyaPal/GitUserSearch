package com.example.gitusersearch.di

import dagger.Component

@GitHubAppScope
@Component(modules = [ApiRepositoryModule::class])
abstract class AppComponent