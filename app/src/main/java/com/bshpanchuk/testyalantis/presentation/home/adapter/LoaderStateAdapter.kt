package com.bshpanchuk.testyalantis.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bshpanchuk.testyalantis.databinding.ItemPostLoaderBinding

class LoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderStateAdapter.LoaderViewHolder>() {

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val binding = ItemPostLoaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoaderViewHolder(binding)
    }

    inner class LoaderViewHolder(private val binding: ItemPostLoaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Loading) {
                binding.loader.transitionToEnd()
            } else {
                binding.loader.transitionToStart()
            }
        }
    }
}