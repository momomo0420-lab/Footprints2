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

        private enum class ZOOM_LEVEL(val value: Float) {
            MAX(20.0F),
            MIN(12.0F)
        }
    }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    private var zoomLevel =  ZOOM_LEVEL.MIN.value

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
        toolbar.title = args.date
    }

    private fun setupGoogleMap() {
        binding.mapView.getMapAsync(getOnMapReadyCallback())
    }

    private fun getOnMapReadyCallback(): OnMapReadyCallback {
        return OnMapReadyCallback { map ->
            lifecycleScope.launch {
                Log.d(TAG, "OnMapReadyCallback")

                viewModel.getMyLocationList(args.date)?.let { myLocationList ->
                    // マーカーの設定
                    setupMarker(map, myLocationList)
                    // ポリラインの設定
                    setupPolyLine(map, myLocationList)

                    // 表示地点の設定
                    setupDisplayPoint(map, myLocationList[0])
                    // カメラボタンの設定
                    setupVideoButton(map)
                }
            }
        }
    }

    /**
     * マーカーの設定
     */
    private fun setupMarker(map: GoogleMap, myLocationList: List<MyLocation>) {
        Log.d(TAG, "setupMarker")
        val option = MarkerOptions()

        for(myLocation in myLocationList) {
            val latLng = LatLng(myLocation.latitude, myLocation.longitude)

            option.apply {
                position(latLng)
                title("${myLocation.date} ${myLocation.time}")
                snippet(myLocation.address)
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
        zoomLevel = ZOOM_LEVEL.MAX.value
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
        false
    }

    /**
     * 表示位置の設定
     */
    private fun setupDisplayPoint(map: GoogleMap, myLocation: MyLocation) {
        Log.d(TAG, "setupDisplayPoint")
        val latLng = LatLng(myLocation.latitude, myLocation.longitude)
        zoomLevel = ZOOM_LEVEL.MIN.value
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
    }

    /**
     * ポリラインの設定
     */
    private fun setupPolyLine(map: GoogleMap, myLocationList: List<MyLocation>) {
        Log.d(TAG, "setupPolyLine")

        val option = PolylineOptions()

        for(myLocation in myLocationList) {
            val latLng = LatLng(myLocation.latitude, myLocation.longitude)
            option.add(latLng)
        }

        map.addPolyline(option)
    }

    /**
     * カメラボタンの設定
     *
     * @param map グーグルマップ
     */
    private fun setupVideoButton(map: GoogleMap) {
        binding.actionCamera.setOnClickListener {
            zoomLevel += 4.0F
            if(zoomLevel > ZOOM_LEVEL.MAX.value) {
                zoomLevel = ZOOM_LEVEL.MIN.value
            }
            map.moveCamera(CameraUpdateFactory.zoomTo(zoomLevel))
        }
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