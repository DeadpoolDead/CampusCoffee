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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private var sugbtn: Button? = null
    private lateinit var mMap: GoogleMap
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

        sugbtn.setOnClickListener{
            constraintLayout.visibility = View.VISIBLE
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    }

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
                Log.d("currentLocation:", currentLocation.toString())
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