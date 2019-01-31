package com.example.gitusersearch.ui.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gitusersearch.R

class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvRepoName : TextView = itemView.findViewById(R.id.tvRepositoryName)
    var tvLanguage : TextView = itemView.findViewById(R.id.tvLanguage)
    var tvStars : TextView = itemView.findViewById(R.id.tvStars)
    var tvForks : TextView = itemView.findViewById(R.id.tvForks)
}