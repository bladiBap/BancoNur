package com.example.proyectofinalmoviles.ui.menu.sucursales

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinalmoviles.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    var map : GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: SucursalViewModel
    private lateinit var token: String

    private val callback = OnMapReadyCallback { googleMap ->
        this.map =  googleMap
        googleMap.uiSettings.isZoomControlsEnabled = true
        setCurrentLocationButton()
        //toLocationCurrent()
    }

    private fun setCurrentLocationButton() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
            return
        }
        map?.isMyLocationEnabled = true
    }

    private fun  toLocationCurrent(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location == null) return@addOnSuccessListener
            val ubication = LatLng(location.latitude, location.longitude)
            map?.addMarker(MarkerOptions().position(ubication).title("Ubicación Actual"))
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(ubication, 15f))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        token = "Bearer " + requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE)
            .getString("token", null).toString()
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        viewModel = ViewModelProvider(this).get(SucursalViewModel::class.java)
        loadObservers()
        loadMark()
        mapFragment?.getMapAsync(callback)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setCurrentLocationButton()
            toLocationCurrent()
        }
    }

    fun loadMark (){
        viewModel.getLocations(token)
    }

    fun loadObservers(){
        viewModel.listLocations.observe(viewLifecycleOwner) {
            if (it != null) {
                for (sucursal in it) {
                    val ubication =
                        LatLng(sucursal.latitud.toDouble(), sucursal.longitud.toDouble())
                    if (sucursal.tipo == 0){
                        map?.addMarker(
                            MarkerOptions().position(ubication).title(sucursal.direccion)
                                .snippet("Sucursal")
                        )
                    }
                    if (sucursal.tipo == 1){
                        map?.addMarker(
                            MarkerOptions().position(ubication).title(sucursal.direccion)
                                .snippet("Surcursal(ATM)")
                        )
                    }

                    if (sucursal.tipo == 2){
                        map?.addMarker(
                            MarkerOptions().position(ubication).title(sucursal.direccion)
                                .snippet("ATM")
                        )
                    }
                }
            }
        }
    }
}