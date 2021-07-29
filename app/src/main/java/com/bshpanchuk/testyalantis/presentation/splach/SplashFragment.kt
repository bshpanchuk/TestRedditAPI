package com.bshpanchuk.testyalantis.presentation.splach


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bshpanchuk.testyalantis.R
import com.bshpanchuk.testyalantis.databinding.FragmentLoginBinding
import com.bshpanchuk.testyalantis.databinding.FragmentSplashBinding
import com.bshpanchuk.testyalantis.presentation.splach.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {

    val viewModel : SplashViewModel by viewModel()
    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}