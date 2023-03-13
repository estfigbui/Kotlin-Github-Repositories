package com.example.android_bankuish_challenge

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android_bankuish_challenge.viewmodel.GithubApiStatus
import com.example.android_bankuish_challenge.data.model.GithubRepository
import com.example.android_bankuish_challenge.ui.adapter.RepositoriesAdapter

/**
 * @author Estefania Figueroa Buitrago
 * estefaniafigbui@gmail.com
 *
 * Binding Adapters responsible for elements values setting
 */

/**
 * Uses the Coil library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        // We load the image showing the user avatar using a loading animation in the meantime
        // If the image does not load, then we show a broken image icon.
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

/**
 * This binding adapter displays the [GithubApiStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("githubApiStatus")
fun bindStatus(statusImageView: ImageView, status: GithubApiStatus) {
    when (status) {
        GithubApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        GithubApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        GithubApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

