package com.example.android_bankuish_challenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bankuish_challenge.databinding.RepositoryListItemBinding
import com.example.android_bankuish_challenge.data.model.GithubRepository
import javax.inject.Inject

/**
 * @author Estefania Figueroa Buitrago
 * estefaniafigbui@gmail.com
 *
 * This class implements a [RecyclerView] [PagingDataAdapter] which listens to internal PagingData
 * loading events as pages are loaded.
 */

class RepositoriesAdapter @Inject constructor() :
    PagingDataAdapter<GithubRepository, RepositoriesAdapter.RepositoriesViewHolder>(DiffCallback) {

    // The RepositoriesViewHolder constructor takes the binding variable from the associated
    // RepositoryListItem, which gives it access to the full [GithubRepository] information.
    inner class RepositoriesViewHolder(private var binding: RepositoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(githubRepository: GithubRepository) {
            binding.repository = githubRepository
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(githubRepository)
                }
            }
        }
    }

    // Handles the event in which an item is clicked
    private var onItemClickListener: ((GithubRepository) -> Unit)? = null
    fun setOnItemClickListener(listener: (GithubRepository) -> Unit) {
        onItemClickListener = listener
    }

    // Create new [RecyclerView]
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoriesViewHolder {
        return RepositoriesViewHolder(
            RepositoryListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    // Replaces the contents of a view
    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    // Allows the RecyclerView to determine which items have changed when the list of
    // [GithubRepository] has been updated.
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<GithubRepository>() {
            override fun areItemsTheSame(
                oldItem: GithubRepository,
                newItem: GithubRepository
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GithubRepository,
                newItem: GithubRepository
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}