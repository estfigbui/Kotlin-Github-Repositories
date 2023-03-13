package com.example.android_bankuish_challenge.utils

import androidx.recyclerview.widget.RecyclerView

/**
 * @author Estefania Figueroa Buitrago
 * estefaniafigbui@gmail.com
 *
 * Extension function for RecyclerView so we can initialize it.
 */

fun RecyclerView.initRecycler(
    layoutManager: RecyclerView.LayoutManager,
    adapter: RecyclerView.Adapter<*>
) {
    this.adapter = adapter
    this.layoutManager = layoutManager
}