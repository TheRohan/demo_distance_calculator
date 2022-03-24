package com.rohan.demodistancecalculator.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohan.demodistancecalculator.R
import com.rohan.demodistancecalculator.adapters.DistanceInfoAdapter
import com.rohan.demodistancecalculator.databinding.FragmentHistoryBinding
import com.rohan.demodistancecalculator.other.SortType
import com.rohan.demodistancecalculator.ui.viewmodels.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {

    lateinit var binding: FragmentHistoryBinding

    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var distanceInfoAdapter: DistanceInfoAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.bAdd.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_createDistanceFragment2)
        }
        setupRecyclerView()

        when (viewModel.sortType.value) {
            SortType.DATE -> binding.spFilter.setSelection(0)
            SortType.DISTANCE -> binding.spFilter.setSelection(1)
        }

        binding.spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adaprerView: AdapterView<*>?,
                view: View?,
                pos: Int,
                id: Long
            ) {
                when (pos) {
                    0 -> viewModel.sortDistanceInfoList(SortType.DATE)
                    1 -> viewModel.sortDistanceInfoList(SortType.DISTANCE)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        lifecycleScope.launchWhenCreated {
            viewModel.distanceInfoList.collect {
                distanceInfoAdapter.submitList(it)
            }
        }
    }

    private fun setupRecyclerView() = binding.rv.apply {
        val context = requireContext()
        distanceInfoAdapter = DistanceInfoAdapter(context)
        adapter = distanceInfoAdapter
        layoutManager = LinearLayoutManager(context)
    }

}