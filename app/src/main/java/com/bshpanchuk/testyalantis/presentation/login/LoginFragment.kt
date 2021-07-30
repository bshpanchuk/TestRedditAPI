package com.bshpanchuk.testyalantis.presentation.login


import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bshpanchuk.testyalantis.R
import com.bshpanchuk.testyalantis.databinding.FragmentLoginBinding
import com.kirkbushman.auth.RedditAuth
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val authClient: RedditAuth by inject()

    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login()
    }

    private fun login() {
        binding.browser.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                if  (authClient.isRedirectedUrl(url)){
                    binding.browser.stopLoading()

                    lifecycleScope.launch {
                        withContext(IO){
                            authClient.getTokenBearer(url)
                        }
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }
        }
        binding.browser.clearFormData()
        binding.browser.loadUrl(authClient.provideAuthorizeUrl())

    }

    override fun onDestroy() {
        super.onDestroy()

        binding.browser.removeAllViews()
        binding.browser.destroy()
    }

}