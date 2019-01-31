package com.example.gitusersearch.ui.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitusersearch.ContentProviders.MySuggestionProvider
import com.example.gitusersearch.R
import com.example.gitusersearch.SearchAppClass
import com.example.gitusersearch.di.DaggerSearchActivityComponent
import com.example.gitusersearch.di.SeachActivityModule
import com.example.gitusersearch.ui.adapters.RvRepositoryAdapter
import com.example.gitusersearch.viewModel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class UserSearchActivity : AppCompatActivity() {

    @Inject
    lateinit var rvRepositoryAdapter: RvRepositoryAdapter
    @Inject
    lateinit var layoutManager : LinearLayoutManager
    @Inject
    lateinit var searchViewModel : SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerSearchActivityComponent.builder()
            .appComponent(SearchAppClass.getAppInstance()?.getappComponent())
            .seachActivityModule(SeachActivityModule(this))
            .build()

        initMembers()
    }

    private fun initMembers() {
        rvRepositories.adapter = rvRepositoryAdapter
        rvRepositories.layoutManager = layoutManager

        searchViewModel.repositoriesLiveData.observe(this, Observer {repositoryList ->
            run {
                if (repositoryList != null)
                    rvRepositoryAdapter.setData(repositoryList)
                else
                    Toast.makeText(this, " Either there is not user/ user has no repositories", Toast.LENGTH_LONG).show()
            }
        } )
    }

    override fun onNewIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
                    .saveRecentQuery(query, null)
                searchViewModel.performUserSearch(query = query)
            }
        }
        super.onNewIntent(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.actionSearch -> onSearchRequested()
        }
        return super.onOptionsItemSelected(item)
    }
}
