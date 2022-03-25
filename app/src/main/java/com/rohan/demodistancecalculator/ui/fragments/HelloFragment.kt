package com.rohan.demodistancecalculator.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.rohan.demodistancecalculator.R
import com.rohan.demodistancecalculator.databinding.FragmentHelloBinding
import com.rohan.demodistancecalculator.other.Constants.KEY_FIRST_TIME_TOGGLE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
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
            if (binding.bNext.alpha != 1f) {
                return@setOnClickListener
            }
            sharedPref.edit()
                .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
                .apply()

            findNavController().navigate(
                R.id.action_helloFragment_to_createDistanceFragment2,
                savedInstanceState,
                navOptions
            )
        }

        lifecycleScope.launchWhenStarted {
            startFadeInAnim()
        }
    }

    private suspend fun startFadeInAnim(){
        binding.tvPart1.alpha = 0f
        binding.tvPart2.alpha = 0f
        binding.tvPart3.alpha = 0f
        binding.bNext.alpha = 0f

        binding.tvPart1.animate().alpha(0f).setDuration(1000).setInterpolator(DecelerateInterpolator())
            .withEndAction {
                binding.tvPart1.animate().alpha(1f).setDuration(1000)
                    .setInterpolator(AccelerateInterpolator()).start()
            }.start()

        delay(1700)

        binding.tvPart2.animate().alpha(0f).setDuration(700).setInterpolator(DecelerateInterpolator())
            .withEndAction {
                binding.tvPart2.animate().alpha(1f).setDuration(700)
                    .setInterpolator(AccelerateInterpolator()).start()
            }.start()

        delay(1200)

        binding.tvPart3.animate().alpha(0f).setDuration(700).setInterpolator(DecelerateInterpolator())
            .withEndAction {
                binding.tvPart3.animate().alpha(1f).setDuration(700)
                    .setInterpolator(AccelerateInterpolator()).start()
            }.start()

        binding.bNext.animate().alpha(0f).setDuration(700).setInterpolator(DecelerateInterpolator())
            .withEndAction {
                binding.bNext.animate().alpha(1f).setDuration(700)
                    .setInterpolator(AccelerateInterpolator()).start()
            }.start()
    }
}