package com.rohan.demodistancecalculator.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rohan.demodistancecalculator.R
import com.rohan.demodistancecalculator.databinding.FragmentLocationSelectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationSelectFragment : Fragment(R.layout.fragment_location_select) {
    lateinit var binding: FragmentLocationSelectBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationSelectBinding.inflate(inflater, container, false)
        return binding.root
    }
}