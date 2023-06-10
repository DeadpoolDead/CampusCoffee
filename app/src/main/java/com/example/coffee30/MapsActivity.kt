package com.example.coffee30

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffee30.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.card.MaterialCardView
import java.util.*
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
    private lateinit var searchview: SearchView
    private lateinit var recView: RecyclerView
    private var iList = ArrayList<Items>()
    private lateinit var adapter: RecAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = "BeanGuru"

        setContentView(R.layout.activity_maps)
        val sugbtn = findViewById<Button>(R.id.btnsuggest)
        val ratbtn = findViewById<Button>(R.id.btnrating)
        val donebtn = findViewById<Button>(R.id.btndone)
        val retbtn = findViewById<Button>(R.id.btnreturn)
        val constraintLayout: ConstraintLayout = findViewById(R.id.Maplayout)
        val McardView: MaterialCardView = findViewById(R.id.Mview1)

        searchview = findViewById(R.id.searchview1)
        recView = findViewById(R.id.recView)
        recView.setHasFixedSize(true)
        recView.visibility = View.INVISIBLE

        recView.layoutManager = LinearLayoutManager(this)
        addToList()
        adapter = RecAdapter(iList)
        recView.adapter = adapter

        searchview.setOnClickListener {
            constraintLayout.visibility = View.INVISIBLE
            sugbtn.visibility = View.INVISIBLE
            ratbtn.visibility = View.INVISIBLE
            donebtn.visibility = View.VISIBLE
            recView.visibility = View.VISIBLE

        }

        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recView.visibility = View.VISIBLE
                constraintLayout.visibility = View.INVISIBLE
                sugbtn.visibility = View.INVISIBLE
                ratbtn.visibility = View.INVISIBLE
                donebtn.visibility = View.VISIBLE
                filterList(newText)
                return true
            }

        })
        val searchCloseButtonId: Int = searchview.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null)
        val closeButton = this.searchview.findViewById(searchCloseButtonId) as ImageView
        closeButton.setOnClickListener {
            constraintLayout.visibility = View.VISIBLE
            sugbtn.visibility = View.VISIBLE
            ratbtn.visibility = View.VISIBLE
            donebtn.visibility = View.INVISIBLE
            recView.visibility = View.INVISIBLE
            searchview.isIconified = true
            //McardView.visibility = View.INVISIBLE

        }


        val table1: TableLayout = findViewById(R.id.table1)
        val table2: TableLayout = findViewById(R.id.table2)

        val shop1 = findViewById<Button>(R.id.shop1)
        val shop2 = findViewById<Button>(R.id.shop2)
        val shop3 = findViewById<Button>(R.id.shop3)
        val shop4 = findViewById<Button>(R.id.shop4)
        val shop5 = findViewById<Button>(R.id.shop5)
        val shop6 = findViewById<Button>(R.id.shop6)


        mapFragment = findViewById<MapView>(R.id.mapView)
        //val mapFragment = supportFragmentManager.findViewById<Button>(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapFragment.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        constraintLayout.visibility = View.VISIBLE

        ratbtn.setOnClickListener {
            constraintLayout.visibility = View.INVISIBLE
            sugbtn.visibility = View.INVISIBLE
            ratbtn.visibility = View.INVISIBLE
            donebtn.visibility = View.VISIBLE
            McardView.visibility = View.INVISIBLE
            recView.visibility = View.INVISIBLE

            shop1.visibility = View.VISIBLE
            shop2.visibility = View.VISIBLE
            shop3.visibility = View.VISIBLE
            shop4.visibility = View.VISIBLE
            shop5.visibility = View.VISIBLE
            shop6.visibility = View.VISIBLE
            shop1.setOnClickListener {
                retbtn.visibility = View.VISIBLE
                table1.visibility = View.VISIBLE
                donebtn.visibility = View.INVISIBLE
                shop1.visibility = View.INVISIBLE
                shop2.visibility = View.INVISIBLE
                shop3.visibility = View.INVISIBLE
                shop4.visibility = View.INVISIBLE
                shop5.visibility = View.INVISIBLE
                shop6.visibility = View.INVISIBLE
            }
            shop2.setOnClickListener {
                retbtn.visibility = View.VISIBLE
                table2.visibility = View.VISIBLE
                donebtn.visibility = View.INVISIBLE
                shop1.visibility = View.INVISIBLE
                shop2.visibility = View.INVISIBLE
                shop3.visibility = View.INVISIBLE
                shop4.visibility = View.INVISIBLE
                shop5.visibility = View.INVISIBLE
                shop6.visibility = View.INVISIBLE

            }
            retbtn.setOnClickListener{
                table1.visibility = View.INVISIBLE
                table2.visibility = View.INVISIBLE
                retbtn.visibility = View.INVISIBLE
                shop1.visibility = View.VISIBLE
                shop2.visibility = View.VISIBLE
                shop3.visibility = View.VISIBLE
                shop4.visibility = View.VISIBLE
                shop5.visibility = View.VISIBLE
                shop6.visibility = View.VISIBLE
                donebtn.visibility = View.VISIBLE
            }



        }
        donebtn.setOnClickListener{
            constraintLayout.visibility = View.VISIBLE
            sugbtn.visibility = View.VISIBLE
            ratbtn.visibility = View.VISIBLE
            donebtn.visibility = View.INVISIBLE
            shop1.visibility = View.INVISIBLE
            shop2.visibility = View.INVISIBLE
            shop3.visibility = View.INVISIBLE
            shop4.visibility = View.INVISIBLE
            shop5.visibility = View.INVISIBLE
            shop6.visibility = View.INVISIBLE
            McardView.visibility = View.VISIBLE

        }




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json))

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

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<Items>()
            for (i in iList) {
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun addToList() {
        iList.add(Items("Latte"))
        iList.add(Items("Americano"))
        iList.add(Items("Cappucino"))
        iList.add(Items("Espresso"))
        iList.add(Items("Hazelnut Latte"))
        iList.add(Items("Cafe Mocha"))
        iList.add(Items("Caramel Macchiato"))
        iList.add(Items("Affogato"))
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