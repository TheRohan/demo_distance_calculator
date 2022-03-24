package com.rohan.demodistancecalculator.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar
import com.rohan.demodistancecalculator.R
import com.rohan.demodistancecalculator.adapters.IUpdateLocation
import com.rohan.demodistancecalculator.data.db.DistanceInfo
import com.rohan.demodistancecalculator.data.network.LocationResponse
import com.rohan.demodistancecalculator.databinding.FragmentCreateDistanceBinding
import com.rohan.demodistancecalculator.other.Constants.DEF_LAT
import com.rohan.demodistancecalculator.other.Constants.DEF_LON
import com.rohan.demodistancecalculator.other.Constants.POLYLINE_COLOR
import com.rohan.demodistancecalculator.other.Constants.POLYLINE_WIDTH
import com.rohan.demodistancecalculator.other.Resource
import com.rohan.demodistancecalculator.other.Utility
import com.rohan.demodistancecalculator.other.Utility.getDistanceInKm
import com.rohan.demodistancecalculator.other.Utility.getDistanceInM
import com.rohan.demodistancecalculator.ui.dialogs.LocationSelectDialogFragment
import com.rohan.demodistancecalculator.ui.viewmodels.CreateDistanceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateDistanceFragment : Fragment(R.layout.fragment_create_distance) {
    lateinit var binding: FragmentCreateDistanceBinding

    private val viewModel: CreateDistanceViewModel by viewModels()
    private var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateDistanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.historyFragment, true)
            .build()

        //map
        binding.mapView.getMapAsync {
            map = it
            map?.uiSettings?.isScrollGesturesEnabled = false;
            map?.uiSettings?.isZoomGesturesEnabled = false;
            redrawLine()
        }

        //1
        binding.bRandom1.setOnClickListener {
            viewModel.setNewStartPoint(Utility.createRandomLatLng())
//            viewModel.setNewStartPoint(LatLng(59.91166, 10.74738)) // for test
        }
        binding.bOpenMap1.setOnClickListener {
            openMapPointPicker(true)
        }

        //2
        binding.bRandom2.setOnClickListener {
            viewModel.setNewEndPoint(Utility.createRandomLatLng())
//            viewModel.setNewEndPoint(LatLng(59.36233, 13.54567)) //for test
        }
        binding.bOpenMap2.setOnClickListener {
            openMapPointPicker(false)
        }

        lifecycleScope.launch {
            viewModel.startPoint.collectLatest {
                if (it.data != null) {
                    setNewDataToField(it, true)

                    zoomToSeeWholeTrack()
                    redrawLine()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.endPoint.collectLatest {
                if (it.data != null) {
                    setNewDataToField(it, false)

                    zoomToSeeWholeTrack()
                    redrawLine()
                }
            }
        }

        //save btn
        binding.bNext.setOnClickListener {
            if (binding.bNext.alpha == 1f) {
                binding.bNext.alpha = 0.5f
                lifecycleScope.launch {
                    val locationInfo1 = viewModel.startPoint.value.data?.convertToLocationInfo()
                    val locationInfo2 = viewModel.endPoint.value.data?.convertToLocationInfo()
                    if (
                        locationInfo1 != null &&
                        locationInfo2 != null
                    ) {
                        val pos1 =
                            LatLng(locationInfo1.lat.toDouble(), locationInfo1.lon.toDouble())
                        val pos2 =
                            LatLng(locationInfo2.lat.toDouble(), locationInfo2.lon.toDouble())

                        zoomToSeeWholeTrack()
                        redrawLine()

                        map?.snapshot { btm ->
                            val toSave = DistanceInfo(
                                btm,
                                locationInfo1,
                                locationInfo2,
                                getDistanceInM(pos1, pos2),
                                getDistanceInKm(pos1, pos2),
                                System.currentTimeMillis()
                            )

                            viewModel.createDistanceInfo(toSave)

                            findNavController().navigate(
                                R.id.action_createDistanceFragment2_to_historyFragment,
                                savedInstanceState,
                                navOptions
                            )
                        }
                    }
                }
            }
        }

        updateButtonAlpha()
    }

    private fun openMapPointPicker(isStartPoint: Boolean) {
        val lat: Float
        val lon: Float

        if (isStartPoint) {
            lat = viewModel.startPoint.value.data?.lat?.toFloatOrNull() ?: DEF_LAT
            lon = viewModel.startPoint.value.data?.lon?.toFloatOrNull() ?: DEF_LON
        } else {
            lat = viewModel.endPoint.value.data?.lat?.toFloatOrNull() ?: DEF_LAT
            lon = viewModel.endPoint.value.data?.lon?.toFloatOrNull() ?: DEF_LON
        }

        val dialog = LocationSelectDialogFragment().apply {
            initDialog(
                lat,
                lon,
                object : IUpdateLocation {
                    override fun updateLatLng(pos: LatLng) {
                        if (isStartPoint) {
                            viewModel.setNewStartPoint(pos)
                        } else {
                            viewModel.setNewEndPoint(pos)
                        }
                    }
                }
            )
        }
        activity?.let { dialog.show(it.supportFragmentManager, LocationSelectDialogFragment.TAG) }
    }

    private fun setNewDataToField(it: Resource<LocationResponse?>, isStartPoint: Boolean) {
        when (it) {
            is Resource.Success -> {
                if (it.data?.lat == null && it.data?.lon == null) {
                    Snackbar.make(
                        requireActivity().findViewById(R.id.rootView),
                        getString(R.string.no_place_err),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                if (isStartPoint) {
                    binding.etLat1.setText(it.data?.lat)
                    binding.etLon1.setText(it.data?.lon)
                } else {
                    binding.etLat2.setText(it.data?.lat)
                    binding.etLon2.setText(it.data?.lon)
                }
            }
            is Resource.Error -> {
                Snackbar.make(
                    requireActivity().findViewById(R.id.rootView),
                    it.message ?: getString(R.string.default_err),
                    Snackbar.LENGTH_LONG
                ).show()
            }
            is Resource.Loading -> {}
        }
        updateButtonAlpha()
    }

    private fun redrawLine() {
        map?.clear()
        val locationInfo1 = viewModel.startPoint.value.data?.convertToLocationInfo()
        val locationInfo2 = viewModel.endPoint.value.data?.convertToLocationInfo()
        if (
            locationInfo1 != null &&
            locationInfo2 != null
        ) {
            val pos1 =
                LatLng(locationInfo1.lat.toDouble(), locationInfo1.lon.toDouble())
            val pos2 =
                LatLng(locationInfo2.lat.toDouble(), locationInfo2.lon.toDouble())

            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(pos1)
                .add(pos2)
            map?.addPolyline(polylineOptions)

            val marker1 = MarkerOptions().position(pos1)
            val marker2 = MarkerOptions().position(pos2)
            map?.addPolyline(polylineOptions)
            map?.addMarker(marker1)
            map?.addMarker(marker2)
        }
    }

    private fun zoomToSeeWholeTrack() {
        val locationInfo1 = viewModel.startPoint.value.data?.convertToLocationInfo()
        val locationInfo2 = viewModel.endPoint.value.data?.convertToLocationInfo()
        if (
            locationInfo1 != null &&
            locationInfo2 != null
        ) {
            val pos1 =
                LatLng(locationInfo1.lat.toDouble(), locationInfo1.lon.toDouble())
            val pos2 =
                LatLng(locationInfo2.lat.toDouble(), locationInfo2.lon.toDouble())

            val bounds = LatLngBounds.builder()
            bounds.include(pos1)
            bounds.include(pos2)

            map?.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds.build(),
                    binding.mapView.width,
                    binding.mapView.height,
                    (binding.mapView.height * 0.05f).toInt()
                )
            )
        }
    }

    private fun updateButtonAlpha() {
        binding.bNext.alpha = if (viewModel.isValidCoordinates()) 1f else 0.5f
    }

    //overrides for map
    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }
}