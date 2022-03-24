package com.rohan.demodistancecalculator.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rohan.demodistancecalculator.R
import com.rohan.demodistancecalculator.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment(R.layout.fragment_distance_info) {
    lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
}