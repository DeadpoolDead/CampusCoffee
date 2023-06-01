package com.example.coffee30

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.checkSelfPermission
import com.example.coffee30.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.math.roundToInt

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private var sugbtn: Button? = null
    private lateinit var mMap: GoogleMap
    private var distance_pando104: Float? = null
    private var distance_dbean: Float? = null
    private var distance_pandoSUB: Float? = null
    private var distance_breadco: Float? = null
    private var distance_caliu: Float? = null
    private var distance_bookcafe: Float? = null
    private lateinit var mapFragment: MapView
    private lateinit var binding: ActivityMapsBinding
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding = ActivityMapsBinding.inflate(layoutInflater)
        //cursorImageView = findViewById(R.id.cursorImageView)
        //setContentView(binding.root)
        setContentView(R.layout.activity_maps)
        val sugbtn = findViewById<Button>(R.id.btnsuggest)
        val constraintLayout: ConstraintLayout = findViewById(R.id.Maplayout)

        mapFragment = findViewById<MapView>(R.id.mapView)
        //val mapFragment = supportFragmentManager.findViewById<Button>(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapFragment.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        constraintLayout.visibility = View.VISIBLE

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val pando104 = LatLng(35.5723264427519, 129.19014215469363)
        val location_pando104: Location = Location("pando104")
        location_pando104.latitude = pando104.latitude
        location_pando104.longitude = pando104.longitude


        val dbean = LatLng(35.57152358782212, 129.18827533721927)
        val location_dbean: Location = Location("dbean")
        location_dbean.latitude = dbean.latitude
        location_dbean.longitude = dbean.longitude

        val pandoSUB = LatLng(35.574377176971765, 129.18961107730868)
        val location_pandoSUB: Location = Location("pandoSUB")
        location_pandoSUB.latitude = pandoSUB.latitude
        location_pandoSUB.longitude = pandoSUB.longitude

        val breadco = LatLng(35.5717679358264, 129.1892731189728)
        val location_breadco: Location = Location("breadco")
        location_breadco.latitude = breadco.latitude
        location_breadco.longitude = breadco.longitude

        val caliu = LatLng(35.57356125283653, 129.18800175189975)
        val location_caliu: Location = Location("caliu")
        location_caliu.latitude = caliu.latitude
        location_caliu.longitude = caliu.longitude

        val bookcafe = LatLng(35.57376632545991, 129.1877925395966)
        val location_bookcafe: Location = Location("bookcafe")
        location_bookcafe.latitude = bookcafe.latitude
        location_bookcafe.longitude = bookcafe.longitude

        val UNIST = LatLngBounds(
            LatLng(35.57037165147802, 129.18645143508914),
            LatLng(35.57579957029743, 129.19162273406985)
        )
        Log.d("UNIST:", UNIST.toString())
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UNIST.center, 17f))
        mMap.uiSettings.isZoomControlsEnabled = true

        if (checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        mMap.isMyLocationEnabled = true
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location!=null){
                lastLocation = location
                val currentLocation = LatLng(location.latitude, location.longitude)
                val location_current: Location = Location("Current")
                location_current.latitude = currentLocation.latitude
                location_current.longitude = currentLocation.longitude

                distance_pando104 = location_pando104.distanceTo(location_current)
                distance_dbean = location_dbean.distanceTo(location_current)
                distance_pandoSUB = location_pandoSUB.distanceTo(location_current)
                distance_breadco = location_breadco.distanceTo(location_current)
                distance_caliu = location_caliu.distanceTo(location_current)
                distance_bookcafe = location_bookcafe.distanceTo(location_current)

                val smallestFloat = minOf(distance_pando104!!, distance_dbean!!, distance_pandoSUB!!, distance_breadco!!, distance_caliu!!, distance_bookcafe!!)

                if(smallestFloat == distance_pando104){
                    mMap.addMarker(MarkerOptions().position(pando104).title("Pandorothy 104 " + distance_pando104!!.roundToInt().toString() + " meters").icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
                }else{
                    mMap.addMarker(MarkerOptions().position(pando104).title("Pandorothy 104 " + distance_pando104!!.roundToInt().toString() + " meters"))
                }
                if(smallestFloat == distance_dbean){
                    mMap.addMarker(MarkerOptions().position(dbean).title("Dutch and Bean "+ distance_dbean!!.roundToInt().toString() + " meters").icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
                }else{
                    mMap.addMarker(MarkerOptions().position(dbean).title("Dutch and Bean "+ distance_dbean!!.roundToInt().toString() + " meters"))
                }
                if(smallestFloat == distance_pandoSUB){
                    mMap.addMarker(MarkerOptions().position(pandoSUB).title("Pandorothy SUB "+ distance_pandoSUB!!.roundToInt().toString() + " meters").icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
                }else{
                    mMap.addMarker(MarkerOptions().position(pandoSUB).title("Pandorothy SUB "+ distance_pandoSUB!!.roundToInt().toString() + " meters"))
                }
                if(smallestFloat == distance_breadco){
                    mMap.addMarker(MarkerOptions().position(breadco).title("Bread&co " + distance_breadco!!.roundToInt().toString() + "meters").icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
                }else{
                    mMap.addMarker(MarkerOptions().position(breadco).title("Bread&co " + distance_breadco!!.roundToInt().toString() + "meters"))
                }
                if(smallestFloat == distance_caliu){
                    mMap.addMarker(MarkerOptions().position(caliu).title("CALIU "+ distance_caliu!!.roundToInt().toString() + " meters").icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
                }else{
                    mMap.addMarker(MarkerOptions().position(caliu).title("CALIU "+ distance_caliu!!.roundToInt().toString() + " meters"))
                }
                if(smallestFloat == distance_bookcafe){
                    mMap.addMarker(MarkerOptions().position(bookcafe).title("Book cafe "+ distance_bookcafe!!.roundToInt().toString() + " meters").icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
                }else{
                    mMap.addMarker(MarkerOptions().position(bookcafe).title("Book cafe "+ distance_bookcafe!!.roundToInt().toString() + " meters"))
                }

            }

        }

    }

    override fun onStart() {
        super.onStart()
        mapFragment.onStart()

    }

    override fun onResume() {
        super.onResume()
        mapFragment.onResume()
    }

    override fun onStop() {
        super.onStop()
        mapFragment.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapFragment.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        mapFragment.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapFragment.onLowMemory()
    }


    override fun onDestroy() {
        super.onDestroy()
        mapFragment.onDestroy()
    }
}