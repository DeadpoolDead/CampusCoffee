package com.example.coffee30

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coffee30.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val pando104 = LatLng(35.5723264427519, 129.19014215469363)
        val dbean = LatLng(35.57152358782212, 129.18827533721927)
        val pandoSUB = LatLng(35.574377176971765, 129.18961107730868)
        val breadco = LatLng(35.5717679358264, 129.1892731189728)
        val caliu = LatLng(35.57356125283653, 129.18800175189975)
        val bookcafe = LatLng(35.57376632545991, 129.1877925395966)
        mMap.addMarker(MarkerOptions().position(pando104).title("Pandorothy 104"))
        mMap.addMarker(MarkerOptions().position(dbean).title("Dutch and Bean"))
        mMap.addMarker(MarkerOptions().position(pandoSUB).title("Pandorothy SUB"))
        mMap.addMarker(MarkerOptions().position(breadco).title("Bread&co"))
        mMap.addMarker(MarkerOptions().position(caliu).title("CALIU"))
        mMap.addMarker(MarkerOptions().position(bookcafe).title("Book cafe"))
        val UNIST = LatLngBounds(
            LatLng(35.57037165147802, 129.18645143508914),
            LatLng(35.57579957029743, 129.19162273406985)
        )
        //mMap.setLatLngBoundsForCameraTarget(UNIST)
        //mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(UNIST, 1))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(pando104))
        //mMap.setLatLngBoundsForCameraTarget(UNIST)
        //mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(UNIST, 1,1,1))
        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(UNIST, 0))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UNIST.center, 17f))

    }
}