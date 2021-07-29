package com.bshpanchuk.testyalantis.presentation.login


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bshpanchuk.testyalantis.R
import com.bshpanchuk.testyalantis.databinding.FragmentLoginBinding
import com.bshpanchuk.testyalantis.presentation.splach.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    val viewModel : LoginViewModel by viewModel()
    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}