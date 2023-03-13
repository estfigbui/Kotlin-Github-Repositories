package com.example.android_bankuish_challenge.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.android_bankuish_challenge.databinding.FragmentRepositoryDetailsBinding
import com.example.android_bankuish_challenge.viewmodel.GithubViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Estefania Figueroa Buitrago
 * estefaniafigbui@gmail.com
 *
 * A [Fragment] subclass as the second destination in the navigation.
 * This fragment shows more details about the repository the user selected.
 */

@AndroidEntryPoint
class RepositoryDetailsFragment : Fragment() {

    // Shared view model
    private val githubViewModel: GithubViewModel by activityViewModels()
    private var binding: FragmentRepositoryDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentRepositoryDetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            this.repository = githubViewModel.currentRepository.value
            // set click listener for the Go to Repository button
            this.goRepository.setOnClickListener {
                searchRepository()
            }
        }
    }

    // Implicit Intent to navigate to the specific repository
    private fun searchRepository() {
        val htmlUrl = githubViewModel.currentRepository.value?.owner?.html_url
        val queryUrl: Uri = Uri.parse(htmlUrl)
        val intent = Intent(Intent.ACTION_VIEW, queryUrl)
        context?.startActivity(intent)
    }
}