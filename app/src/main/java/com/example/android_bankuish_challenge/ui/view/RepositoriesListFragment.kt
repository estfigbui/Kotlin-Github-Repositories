package com.example.android_bankuish_challenge.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.android_bankuish_challenge.R
import com.example.android_bankuish_challenge.ui.adapter.RepositoriesAdapter
import com.example.android_bankuish_challenge.databinding.FragmentRepositoriesListBinding
import com.example.android_bankuish_challenge.ui.adapter.LoadMoreAdapter
import com.example.android_bankuish_challenge.utils.initRecycler
import com.example.android_bankuish_challenge.viewmodel.GithubViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Estefania Figueroa Buitrago
 * estefaniafigbui@gmail.com
 *
 * This fragment shows the repositories list in a Recycler View. The user can choose a specific repository to check further details about it.
 */

@AndroidEntryPoint
class RepositoriesListFragment : Fragment() {

    @Inject
    lateinit var repositoriesAdapter: RepositoriesAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    // Binding object instance corresponding to the fragment_repositories_list.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentRepositoriesListBinding? = null

    // We use the 'by activityViewModels()' since this is a shared view model
    private val githubViewModel: GithubViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentRepositoriesListBinding.inflate(inflater)
        fragmentBinding.lifecycleOwner = viewLifecycleOwner
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            this.viewModel = githubViewModel

            lifecycleScope.launchWhenCreated {
                viewModel!!.repositoriesList.collect {
                    repositoriesAdapter.submitData(it)
                }
            }

            // LoadStateFlow emits a snapshot whenever the loading state of the current PagingData changes
            lifecycleScope.launchWhenCreated {
                repositoriesAdapter.loadStateFlow.collect {
                    it.refresh
                }
            }

            repositoriesList.apply {
                // Initialize RecyclerView
                initRecycler(LinearLayoutManager(this.context), repositoriesAdapter)

                // Presents the LoadMoreAdapter as a footer alongside the PagingDataAdapter
                adapter = repositoriesAdapter.withLoadStateFooter(
                    LoadMoreAdapter {
                        repositoriesAdapter.retry()
                    }
                )
            }

            // Handles the click on a specific item
            repositoriesAdapter.setOnItemClickListener { githubRepository ->
                githubViewModel.updateCurrentRepository(githubRepository)
                findNavController().navigate(R.id.action_repositoriesListFragment_to_repositoryDetailsFragment)
            }
        }

        // Handles the swipe refresh and reloads the recycler view
        swipeRefreshLayout = binding!!.swipeLayout
        swipeRefreshLayout.setOnRefreshListener {
            binding?.apply {
                githubViewModel.updateRepositories()
                this.repositoriesList.adapter!!.notifyDataSetChanged()
            }
            swipeRefreshLayout.isRefreshing = false
        }
    }
}