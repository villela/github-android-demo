package com.matheusvillela.githubdemoapp.ui.repolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.matheusvillela.githubdemoapp.R
import com.matheusvillela.githubdemoapp.databinding.ItemRepoBinding
import com.matheusvillela.githubdemoapp.presentation.domain.GitHubRepo
import com.matheusvillela.githubdemoapp.shared.OnClickPublisher
import toothpick.InjectConstructor


@InjectConstructor
class RepoListAdapter(
    private val onClickPublisher: OnRepoClickPublisher
) : RecyclerView.Adapter<RepoListAdapter.RepoListAdapterHolder>() {

    private var repos = listOf<GitHubRepo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_repo, parent, false)
        return RepoListAdapterHolder(view, onClickPublisher)
    }

    override fun onBindViewHolder(holder: RepoListAdapterHolder, position: Int) {
        val repo = repos[position]
        holder.bind(repo)
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    fun updateRepos(list: List<GitHubRepo>) {
        val diffResult = DiffUtil.calculateDiff(RepoDiffCallback(list, repos))
        diffResult.dispatchUpdatesTo(this)
        repos = list
    }

    class RepoListAdapterHolder(
        itemView: View,
        private val onClickPublisher: OnClickPublisher<GitHubRepo>
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRepoBinding.bind(itemView)
        private var repo: GitHubRepo? = null

        fun bind(repo: GitHubRepo) {
            binding.repoName.text = repo.name
            binding.userLogin.text = repo.owner.login
            this.repo = repo
        }

        init {
            binding.root.setOnClickListener {
                this.repo?.let {
                    onClickPublisher.onClick(it)
                }
            }
        }
    }

    class RepoDiffCallback(
        private val newList: List<GitHubRepo>,
        private val oldList: List<GitHubRepo>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}