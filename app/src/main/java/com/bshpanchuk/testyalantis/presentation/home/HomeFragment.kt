package com.bshpanchuk.testyalantis.presentation.home


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bshpanchuk.testyalantis.R
import com.bshpanchuk.testyalantis.databinding.ActivityMainBinding
import com.bshpanchuk.testyalantis.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    val viewModel : HomeViewModel by viewModel()
    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}