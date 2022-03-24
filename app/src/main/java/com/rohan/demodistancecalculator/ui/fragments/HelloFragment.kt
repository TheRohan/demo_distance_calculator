package com.rohan.demodistancecalculator.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.rohan.demodistancecalculator.R
import com.rohan.demodistancecalculator.databinding.FragmentHelloBinding
import com.rohan.demodistancecalculator.other.Constants.KEY_FIRST_TIME_TOGGLE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class HelloFragment : Fragment(R.layout.fragment_hello) {

    @Inject
    lateinit var sharedPref: SharedPreferences

    @set:[Inject Named("provideFirstTimeToggle")]
    var isFirsAppOpen = true

    lateinit var binding: FragmentHelloBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelloBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.historyFragment, true)
            .build()
        if (!isFirsAppOpen) {
            findNavController().navigate(
                R.id.action_helloFragment_to_historyFragment,
                savedInstanceState,
                navOptions
            )
        }

        binding.bNext.setOnClickListener {

            sharedPref.edit()
                .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
                .apply()

            findNavController().navigate(
                R.id.action_helloFragment_to_createDistanceFragment2,
                savedInstanceState,
                navOptions
            )
        }
    }
}