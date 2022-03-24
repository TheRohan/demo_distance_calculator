package com.rohan.demodistancecalculator.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.rohan.demodistancecalculator.R
import com.rohan.demodistancecalculator.databinding.FragmentDistanceInfoBinding
import com.rohan.demodistancecalculator.other.Utility
import com.rohan.demodistancecalculator.ui.viewmodels.DistanceInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DistanceInfoFragment : Fragment(R.layout.fragment_distance_info) {
    lateinit var binding: FragmentDistanceInfoBinding

    private val viewModel: DistanceInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDistanceInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.historyFragment, true)
            .build()

        binding.bOk.setOnClickListener {
            navigateToHistoryFragment(
                savedInstanceState,
                navOptions
            )
        }

        if (arguments == null) {
            navigateToHistoryFragment(
                savedInstanceState,
                navOptions
            )
            return
        } else {
            val id = DistanceInfoFragmentArgs.fromBundle(requireArguments()).distanceInfoId
            if (id < 0) {
                navigateToHistoryFragment(
                    savedInstanceState,
                    navOptions
                )
                return
            }
            //init ui

            lifecycleScope.launch {
                viewModel.distanceInfo.collectLatest {
                    if (it == null) {
                        binding.tvFullName1.text = ""
                        binding.tvLat1.text = ""
                        binding.tvLon1.text = ""
                        binding.tvCountry1.text = ""
                        binding.tvCity1.text = ""

                        binding.tvFullName2.text = ""
                        binding.tvLat2.text = ""
                        binding.tvLon2.text = ""
                        binding.tvCountry2.text = ""
                        binding.tvCity2.text = ""

                        binding.tvDistanceM.text = ""
                        binding.tvDistanceKM.text = ""
                        binding.tvDate.text = ""
                    } else {
                        Glide.with(binding.root).load(it.img).into(binding.iMap)

                        binding.tvFullName1.text = it.locationInfo1.fullName ?: ""

                        binding.tvLat1.text = it.locationInfo1.lat.toString()
                        binding.tvLon1.text = it.locationInfo1.lon.toString()

                        if (it.locationInfo1.country.isNullOrBlank()) {
                            binding.llcCountry1.visibility = GONE
                        } else {
                            binding.llcCountry1.visibility = VISIBLE
                            binding.tvCountry1.text = it.locationInfo1.country ?: ""
                        }
                        if (it.locationInfo1.state.isNullOrBlank()) {
                            binding.llcCity1.visibility = GONE
                        } else {
                            binding.llcCity1.visibility = VISIBLE
                            binding.tvCity1.text = it.locationInfo1.state ?: ""
                        }


                        binding.tvFullName2.text = it.locationInfo2.fullName ?: ""

                        binding.tvLat2.text = it.locationInfo2.lat.toString()
                        binding.tvLon2.text = it.locationInfo2.lon.toString()

                        if (it.locationInfo2.country.isNullOrBlank()) {
                            binding.llcCountry2.visibility = GONE
                        } else {
                            binding.llcCountry2.visibility = VISIBLE
                            binding.tvCountry2.text = it.locationInfo2.country ?: ""
                        }
                        if (it.locationInfo2.state.isNullOrBlank()) {
                            binding.llcCity2.visibility = GONE
                        } else {
                            binding.llcCity2.visibility = VISIBLE
                            binding.tvCity2.text = it.locationInfo2.state ?: ""
                        }


                        binding.tvDistanceM.text = "${it.distanceInM}m"
                        binding.tvDistanceKM.text = "${it.distanceInKM}km"
                        val dateString = Utility.millsToText(it.createdDate)
                        binding.tvDate.text = dateString
                    }
                }
            }
            viewModel.loadDistanceInfoById(id)

        }
    }

    private fun navigateToHistoryFragment(args: Bundle?, navOptions: NavOptions?) {
        findNavController().navigate(
            R.id.action_distanceInfoFragment_to_historyFragment,
            args,
            navOptions
        )
    }
}