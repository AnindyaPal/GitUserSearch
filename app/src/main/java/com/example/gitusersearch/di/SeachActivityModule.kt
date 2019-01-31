package com.example.gitusersearch.di

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitusersearch.ui.adapters.RvRepositoryAdapter
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

    @Provides
    fun getRvRepositoryAdapter(context: Context) : RvRepositoryAdapter {
        return RvRepositoryAdapter(context)
    }
}