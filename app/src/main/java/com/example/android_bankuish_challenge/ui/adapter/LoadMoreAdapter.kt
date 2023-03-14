package com.example.android_bankuish_challenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bankuish_challenge.databinding.LoadMorePagingFooterBinding

/**
 * @author Estefania Figueroa Buitrago
 * estefaniafigbui@gmail.com
 *
 * Adapter for displaying a RecyclerView item based on LoadState, in this case, a loading spinner and a retry error button.
 */

class LoadMoreAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadMoreAdapter.ViewHolder>() {

    private lateinit var binding: LoadMorePagingFooterBinding

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        // Inflates the layout we created with a spinner, an informative text and a retry button
        binding =
            LoadMorePagingFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(retry)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        // This item in the RecyclerView is set to hold the loading state of the next page
        holder.setData(loadState)
    }

    inner class ViewHolder(retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        // Sets a click listener on the retry button to try to load again a new page
        init {
            binding.retryButton.setOnClickListener { retry() }
        }

        // Defines what is shown inside depending on LoadState
        fun setData(state: LoadState) {
            binding.apply {
                progressBar.isVisible = state is LoadState.Loading
                loadMoreText.isVisible = state is LoadState.Error
                retryButton.isVisible = state is LoadState.Error
            }
        }
    }
}