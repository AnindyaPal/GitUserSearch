package com.example.gitusersearch.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitusersearch.SearchAppClass
import com.example.gitusersearch.models.RepoModel
import com.example.gitusersearch.repository.GithubRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SearchViewModel : ViewModel(), CoroutineScope{

    val job : Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    @Inject
    lateinit var gitHubRepo : GithubRepo

    init {
        SearchAppClass.getAppInstance()?.getappComponent()?.injectSearchViewModel(this)
    }

    var repositoriesLiveData : MutableLiveData<List<RepoModel>> = MutableLiveData()

    fun performUserSearch(query: String){
        launch { getRepoIfUserPresent(query) }
    }

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