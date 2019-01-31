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
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitusersearch.ContentProviders.MySuggestionProvider
import com.example.gitusersearch.R
import com.example.gitusersearch.Utils
import com.example.gitusersearch.di.activity.DaggerSearchActivityComponent
import com.example.gitusersearch.di.activity.SeachActivityModule
import com.example.gitusersearch.ui.adapters.RvRepositoryAdapter
import com.example.gitusersearch.viewModel.SearchViewModel
import com.google.android.material.snackbar.Snackbar
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
                    tvUserName.text = user.login

                    if (user.email.toString() == "null") {
                        tvEmail.text = "Email not provided"
                    }
                    else {
                        tvEmail.text = user.email
                    }

                    tvName.text = user.name
                }
                else {
                    rvRepositoryAdapter.setData(mutableListOf())
                    Toast.makeText(this, " The user does not exist", Toast.LENGTH_LONG).show()
                }
            }
        } )
    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onNewIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
                    .saveRecentQuery(query, null)
                showProgressBar()
                rvRepositoryAdapter.setData(mutableListOf())
                networkCheckAndProcess(query)
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


    private fun showSnackbar(query: String) {
        val snackbar = Snackbar.make(vRoot, "Please check internet connection" , Snackbar.LENGTH_INDEFINITE )
        snackbar.setAction("RETRY") { networkCheckAndProcess(query) }
        snackbar.setActionTextColor(ResourcesCompat.getColor(resources, R.color.colorAccent, null))
        snackbar.setActionTextColor(ContextCompat.getColor(this@UserSearchActivity,R.color.colorAccent))
        snackbar.show()
    }

    private fun networkCheckAndProcess(query : String) {
        if (Utils.isNetworkAvailable(this@UserSearchActivity)) {
            showProgressBar()
            searchViewModel.performUserSearch(query)
        } else {
            showSnackbar(query)
        }
    }
}
