package com.rohan.demodistancecalculator.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rohan.demodistancecalculator.R
import com.rohan.demodistancecalculator.databinding.FragmentDistanceInfoBinding
import com.rohan.demodistancecalculator.databinding.FragmentHelloBinding

class HelloFragment : Fragment(R.layout.fragment_distance_info) {
    lateinit var binding: FragmentHelloBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelloBinding.inflate(inflater, container, false)
        return binding.root
    }
}