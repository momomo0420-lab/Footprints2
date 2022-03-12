package com.example.footprints2.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.footprints2.R
import com.example.footprints2.databinding.FragmentDetailBinding
import com.example.footprints2.model.repository.database.MyLocation
import com.example.footprints2.util.DateManipulator
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    companion object {
        private const val TAG = "DetailFragment"

        private enum class ZOOM(val value: Float) {
            HIGH(20.0F),
            MIDDLE(15.0F),
            LOW(12.0F)
        }

        private enum class OPACITY(val value: Float) {
            HIGH(1.0F),
            MIDDLE(0.7F),
            LOW(0.3F)
        }
    }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)

        // メニューバーの設定
        setupAppBar()
        // グーグルマップの設定
        setupGoogleMap()
    }

    /**
     * アプリバーの設定
     */
    private fun setupAppBar() {
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar_main_activity)
        toolbar.menu.clear()
        val myLocation = args.myLocation
        toolbar.title = DateManipulator.convertDateAndTimeToString2(
            myLocation.dateAndTime
        )
    }

    private fun setupGoogleMap() {
        val myLocation = args.myLocation
        binding.mapView.getMapAsync(getOnMapReadyCallback(myLocation))
    }

    private fun getOnMapReadyCallback(myLocation: MyLocation): OnMapReadyCallback {
        return OnMapReadyCallback { map ->
            lifecycleScope.launch {
                Log.d(TAG, "OnMapReadyCallback")

                // 選択された日付の地点をロードする
                viewModel.loadMyLocationListBy(myLocation.dateAndTime)

                // マーカーの設定
                setupMarker(map)

                // 表示地点の設定
                setupDisplayPoint(map, myLocation)

                // ポリラインの設定
                setupPolyLine(map)

                // UIの設定
                setupUI(map)

                setupHomeButton(map, myLocation)
            }
        }
    }

    private fun setupHomeButton(map: GoogleMap, myLocation: MyLocation) {
        binding.actionHome.setOnClickListener {
            val latLng = LatLng(myLocation.latitude, myLocation.longitude)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM.LOW.value))
        }
    }

    private fun setupUI(map: GoogleMap) {
        with(map.uiSettings) {
            isZoomControlsEnabled = true
        }
    }

    /**
     * マーカーの設定
     */
    private fun setupMarker(map: GoogleMap) {
        Log.d(TAG, "setupMarker")
        val option = MarkerOptions()

        val myLocationList = viewModel.getMyLocationList() ?: return
        for(myLocation in myLocationList) {
            val latLng = LatLng(myLocation.latitude, myLocation.longitude)

            option.apply {
                position(latLng)
                title(DateManipulator.convertDateAndTimeToString(myLocation.dateAndTime))
                snippet(myLocation.address)
                alpha(OPACITY.LOW.value)
            }

            map.addMarker(option)
        }
        map.setOnMarkerClickListener(getOnMarkerClickListener(map))
    }

    /**
     * マーカークリック時の動作
     */
    private fun getOnMarkerClickListener(
        map: GoogleMap
    ): GoogleMap.OnMarkerClickListener = GoogleMap.OnMarkerClickListener { marker ->
        val latLng = marker.position
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM.HIGH.value))
        false
    }

    /**
     * 表示位置の設定
     */
    private fun setupDisplayPoint(map: GoogleMap, myLocation: MyLocation) {
        Log.d(TAG, "setupDisplayPoint")
        val latLng = LatLng(myLocation.latitude, myLocation.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM.LOW.value))
    }

    /**
     * ポリラインの設定
     */
    private fun setupPolyLine(map: GoogleMap) {
        Log.d(TAG, "setupPolyLine")

        val option = PolylineOptions()

        val myLocationList = viewModel.getMyLocationList() ?: return
        for(myLocation in myLocationList) {
            val latLng = LatLng(myLocation.latitude, myLocation.longitude)
            option.add(latLng)
        }

        map.addPolyline(option)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}