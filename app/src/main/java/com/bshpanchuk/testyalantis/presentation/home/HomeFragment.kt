package com.bshpanchuk.testyalantis.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bshpanchuk.testyalantis.R
import com.bshpanchuk.testyalantis.databinding.FragmentHomeBinding
import com.bshpanchuk.testyalantis.domain.model.DataPost
import com.bshpanchuk.testyalantis.presentation.home.HomeFragment.ViewState.*
import com.bshpanchuk.testyalantis.presentation.home.adapter.LoaderStateAdapter
import com.bshpanchuk.testyalantis.presentation.home.adapter.RedditPostAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModel()
    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val disposable = CompositeDisposable()

    private val postAdapter by lazy(LazyThreadSafetyMode.NONE) {
        RedditPostAdapter(
            clickOpen = {
                openChromeTab(it)
            },
            clickShare = {
                sharePost(it)
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        loadData()
    }

    private fun initView() {
        with(binding) {
            val loaderStateAdapter = LoaderStateAdapter {
                postAdapter.retry()
            }

            recyclerPost.adapter =
                postAdapter.withLoadStateFooter(loaderStateAdapter)

            recyclerPost.setHasFixedSize(true)
            recyclerPost.addItemDecoration(VerticalSpaceItemDecorator(32))

            postAdapter.addLoadStateListener { loadState ->
                when(loadState.refresh){
                    is LoadState.NotLoading -> {
                        setState(NOT_LOADING)
                    }
                    LoadState.Loading -> {
                        setState(LOADING)
                    }
                    is LoadState.Error -> {
                        setState(ERROR)
                    }
                }
            }

            include.buttonRetry.setOnClickListener {
                postAdapter.retry()
            }
        }
    }

    private fun loadData() {
        disposable.add(
            viewModel.getData().subscribe({
                postAdapter.submitData(lifecycle, it)
            },
                {
                    setState(ERROR)
                })
        )
    }

    private fun setState(state: ViewState){
        when(state){
            ERROR -> {
                binding.includeContainer.isVisible = true
                binding.progressLoad.isVisible = false
            }
            LOADING -> {
              binding.progressLoad.isVisible = true
            }
            NOT_LOADING -> {
                binding.includeContainer.isVisible = false
                binding.progressLoad.isVisible = false
            }
        }
    }

    private fun openChromeTab(item: DataPost.PostItem) {
        val uri = "$REDDIT_URL${item.link}".toUri()
        val builder = CustomTabsIntent.Builder()
        val intent = builder.build()

        intent.launchUrl(requireContext(), uri)
    }

    private fun sharePost(item: DataPost.PostItem) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "$REDDIT_URL${item.link}")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }

    companion object {
        private const val REDDIT_URL = "https://www.reddit.com/"
    }

    private enum class ViewState{
        ERROR,
        LOADING,
        NOT_LOADING
    }
}

