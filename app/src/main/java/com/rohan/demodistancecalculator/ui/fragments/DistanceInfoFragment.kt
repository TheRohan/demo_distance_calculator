package com.rohan.demodistancecalculator.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.rohan.demodistancecalculator.R
import com.rohan.demodistancecalculator.databinding.FragmentDistanceInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DistanceInfoFragment : Fragment(R.layout.fragment_distance_info) {
    lateinit var binding: FragmentDistanceInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDistanceInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.historyFragment, true)
            .build()

        binding.bOk.setOnClickListener {
            findNavController().navigate(
                R.id.action_distanceInfoFragment_to_historyFragment,
                savedInstanceState,
                navOptions
            )
        }

    }
}