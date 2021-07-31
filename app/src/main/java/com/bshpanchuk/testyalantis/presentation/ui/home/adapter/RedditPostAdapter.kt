package com.bshpanchuk.testyalantis.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bshpanchuk.testyalantis.common.extension.load
import com.bshpanchuk.testyalantis.databinding.ItemRedditPostBinding
import com.bshpanchuk.testyalantis.presentation.model.RedditPostUI

class RedditPostAdapter(
    private val clickOpen: (RedditPostUI) -> Unit,
    private val clickShare: (RedditPostUI) -> Unit
) : PagingDataAdapter<RedditPostUI, RedditPostAdapter.PostViewHolder>(PostDiffUtil()) {

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemRedditPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    inner class PostViewHolder(private val binding: ItemRedditPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RedditPostUI?) {
            if (item == null) return

            with(binding) {
                textAuthor.text = item.author
                textSubreddit.text = item.subreddit
                textTitle.text = item.title

                textNumberOfComments.text = item.numberOfComments
                textTextRating.text = item.rating
                textTimeAgo.text = item.data

                if (item.imageUrl != null) {
                    imagePost.isVisible = true
                    imagePost.load(item.imageUrl)
                } else {
                    imagePost.isVisible = false
                }

                root.setOnClickListener {
                    clickOpen(item)
                }

                buttonShare.setOnClickListener {
                    clickShare(item)
                }
            }
        }
    }

    private class PostDiffUtil : DiffUtil.ItemCallback<RedditPostUI>() {
        override fun areItemsTheSame(
            oldItem: RedditPostUI, newItem: RedditPostUI
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: RedditPostUI, newItem: RedditPostUI
        ): Boolean {
            return oldItem.name == newItem.name
        }
    }

}