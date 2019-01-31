package com.example.gitusersearch.ui.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitusersearch.ContentProviders.MySuggestionProvider
import com.example.gitusersearch.R
import com.example.gitusersearch.di.DaggerSearchActivityComponent
import com.example.gitusersearch.di.SeachActivityModule
import com.example.gitusersearch.ui.adapters.RvRepositoryAdapter
import com.example.gitusersearch.viewModel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class UserSearchActivity : AppCompatActivity() {

    @Inject
    internal lateinit var rvRepositoryAdapter: RvRepositoryAdapter
    @Inject
    internal lateinit var layoutManager : LinearLayoutManager
    @Inject
    internal lateinit var searchViewModel : SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerSearchActivityComponent.builder()
            .seachActivityModule(SeachActivityModule(this))
            .build().injectSearchActivity(this)

        initMembers()
    }

    private fun initMembers() {

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL)

        rvRepositories.adapter = rvRepositoryAdapter
        rvRepositories.layoutManager = layoutManager

        searchViewModel.repositoriesLiveData.observe(this, Observer {fullNameRepoList ->
            run {
                hideProgressBar()
                if (fullNameRepoList != null) {
                    rvRepositoryAdapter.setData(fullNameRepoList.repoList) // adapter set
                    val user = fullNameRepoList.user // setting user details
                    tvUserName.text = user.name
                    tvEmail.text = user.email
                    tvName.text = user.name
                }
                else {
                    rvRepositoryAdapter.setData(mutableListOf())
                    Toast.makeText(this, " The user does not exist", Toast.LENGTH_LONG).show()
                }
            }
        } )
    }

    fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onNewIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
                    .saveRecentQuery(query, null)
                showProgressBar()
                searchViewModel.performUserSearch(query = query)
            }
        }
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
