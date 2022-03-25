package com.rohan.demodistancecalculator.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rohan.demodistancecalculator.adapters.IUpdateLocation
import com.rohan.demodistancecalculator.databinding.DialogFragmentLocationSelectBinding
import com.rohan.demodistancecalculator.other.Constants.DEF_LAT
import com.rohan.demodistancecalculator.other.Constants.DEF_LON
import com.rohan.demodistancecalculator.other.Constants.MAP_ZOOM

class LocationSelectDialogFragment :
    DialogFragment() {
    companion object {
        const val TAG = "LocationSelectDialogFragment"
    }

    private var lat: Float = DEF_LAT
    private var lon: Float = DEF_LON
    private var iUpdateLocation: IUpdateLocation? = null

    fun initDialog(
        lat: Float = DEF_LAT,
        lon: Float = DEF_LON,
        iUpdateLocation: IUpdateLocation
    ) {
        this.lat = lat
        this.lon = lon
        this.iUpdateLocation = iUpdateLocation
    }

    lateinit var binding: DialogFragmentLocationSelectBinding

    private var mapView: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentLocationSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)

        binding.mapView.getMapAsync {
            mapView = it
            mapView?.uiSettings?.isMapToolbarEnabled = false

            //loc
            mapView?.setOnMapClickListener { arg0 ->
                val tmp = LatLng(
                    arg0.latitude,
                    arg0.longitude
                )
                lat = arg0.latitude.toFloat()
                lon = arg0.longitude.toFloat()
                drawMarker(tmp)
            }

            drawMarker(
                LatLng(
                    lat.toDouble(),
                    lon.toDouble()
                )
            )
        }

        binding.bOk.setOnClickListener {
            iUpdateLocation?.updateLatLng(
                LatLng(
                    lat.toDouble(),
                    lon.toDouble()
                )
            )

            dismiss()
        }

        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }


    private fun drawMarker(pos: LatLng) {
        mapView?.clear()
        mapView?.addMarker(
            MarkerOptions().position(pos)
        )
        mapView?.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, MAP_ZOOM))
    }

    //    overrides for mapView
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