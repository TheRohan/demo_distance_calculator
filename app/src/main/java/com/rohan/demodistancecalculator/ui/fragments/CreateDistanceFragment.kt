package com.rohan.demodistancecalculator.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rohan.demodistancecalculator.R
import com.rohan.demodistancecalculator.databinding.FragmentCreateDistanceBinding
import com.rohan.demodistancecalculator.databinding.FragmentLocationSelectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateDistanceFragment : Fragment(R.layout.fragment_create_distance) {
    lateinit var binding: FragmentCreateDistanceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateDistanceBinding.inflate(inflater, container, false)
        return binding.root
    }
}