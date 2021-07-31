package com.bshpanchuk.testyalantis.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bshpanchuk.testyalantis.R
import com.bshpanchuk.testyalantis.common.di.BASE_URL
import com.bshpanchuk.testyalantis.databinding.FragmentHomeBinding
import com.bshpanchuk.testyalantis.presentation.model.RedditPostUI
import com.bshpanchuk.testyalantis.presentation.ui.home.HomeFragment.ViewState.*
import com.bshpanchuk.testyalantis.presentation.ui.home.adapter.LoaderStateAdapter
import com.bshpanchuk.testyalantis.presentation.ui.home.adapter.RedditPostAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
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
            val header = LoaderStateAdapter {
                postAdapter.retry()
            }

            val footer = LoaderStateAdapter {
                postAdapter.retry()
            }

            recyclerPost.apply {
                adapter = postAdapter.withLoadStateHeaderAndFooter(header, footer)
                setHasFixedSize(true)
                addItemDecoration(VerticalSpaceItemDecorator(32))
            }

            postAdapter.addLoadStateListener { loadState ->
                when (loadState.refresh) {
                    is LoadState.NotLoading -> setState(NOT_LOADING)
                    LoadState.Loading -> setState(LOADING)
                    is LoadState.Error -> setState(ERROR)
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
                    it.printStackTrace()
                    setState(ERROR)
                })
        )
    }

    private fun setState(state: ViewState) {
        with(binding) {
            when (state) {
                ERROR -> {
                    includeContainer.isVisible = true
                    progressLoad.isVisible = false
                }
                LOADING -> {
                    progressLoad.isVisible = true
                }
                NOT_LOADING -> {
                    includeContainer.isVisible = false
                    progressLoad.isVisible = false
                }
            }
        }
    }

    private fun openChromeTab(item: RedditPostUI) {
        val uri = "$BASE_URL${item.link}".toUri()
        val builder = CustomTabsIntent.Builder()
        val intent = builder.build()

        intent.launchUrl(requireContext(), uri)
    }

    private fun sharePost(item: RedditPostUI) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "$BASE_URL${item.link}")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }

    private enum class ViewState {
        ERROR,
        LOADING,
        NOT_LOADING
    }
}
