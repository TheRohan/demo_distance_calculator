package com.rohan.demodistancecalculator.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rohan.demodistancecalculator.R
import com.rohan.demodistancecalculator.databinding.ActivityMainBinding
import com.rohan.demodistancecalculator.other.Utility.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var navHostFragment: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        if (!isNetworkAvailable(this)) {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getText(R.string.error))
                .setMessage(resources.getText(R.string.network_error))
                .setNeutralButton(resources.getText(R.string.ok)) { _, _ ->
                }
                .show()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

}