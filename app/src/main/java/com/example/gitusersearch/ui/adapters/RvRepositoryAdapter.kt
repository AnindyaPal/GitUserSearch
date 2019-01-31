package com.example.gitusersearch.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitusersearch.R
import com.example.gitusersearch.models.RepoModel
import com.example.gitusersearch.ui.viewHolders.RepositoryViewHolder

class RvRepositoryAdapter(val context: Context) : RecyclerView.Adapter<RepositoryViewHolder>() {
    val repositoriesList : MutableList<RepoModel> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
        return RepositoryViewHolder(layoutView)
    }

    override fun getItemCount(): Int {
        return repositoriesList.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repoModel = repositoriesList[position]

        holder.tvRepoName.text = repoModel.name
        holder.tvStars.text = repoModel.stargazers_count.toString()
        holder.tvForks.text = repoModel.forks_count.toString()
        holder.tvLanguage.text = repoModel.language
    }

    fun setData(repositories : List<RepoModel>) {
        repositoriesList.addAll(repositories)
        notifyDataSetChanged()
    }
}