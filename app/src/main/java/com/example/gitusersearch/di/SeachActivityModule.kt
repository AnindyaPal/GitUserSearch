package com.example.gitusersearch.di

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides

@Module
class SeachActivityModule(val context: Context){
    @Provides
    fun getActivityContext(): Context{
        return context
    }

    @Provides
    fun getLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(context)
    }
}