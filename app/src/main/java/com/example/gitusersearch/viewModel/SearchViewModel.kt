package com.example.gitusersearch.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitusersearch.SearchAppClass
import com.example.gitusersearch.models.RepoModel
import com.example.gitusersearch.repository.GithubRepo
import java.lang.Exception
import javax.inject.Inject

class SearchViewModel : ViewModel(){

    @Inject
    lateinit var gitHubRepo : GithubRepo

    init {
        SearchAppClass.getAppInstance()?.getappComponent()?.injectSearchViewModel(this)
    }
    var repositoriesLiveData : MutableLiveData<List<RepoModel>> = MutableLiveData()

    suspend fun getRepoIfUserPresent(query : String) {
        try {
            val user = gitHubRepo.getUserByName(query).await()
            val repositoryList = gitHubRepo.getRepositoriesByUser(query).await()

            repositoriesLiveData.value = repositoryList
        } catch (e : Exception) {
            repositoriesLiveData.value = null
        }
    }
}