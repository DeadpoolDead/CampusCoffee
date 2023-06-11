package com.example.coffee30

import android.Manifest
import android.content.Intent
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

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, RecAdapter.ClickListener {
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
    private lateinit var donebtn: Button
    private lateinit var McardView: MaterialCardView

    private lateinit var pando_dict: Map<String, Int>
    private lateinit var dbean_dict: Map<String, Int>
    private lateinit var pandoSUB_dict: Map<String, Int>
    private lateinit var breadco_dict: Map<String, Int>
    private lateinit var caliu_dict: Map<String, Int>
    private lateinit var bookcafe_dict: Map<String, Int>

    private lateinit var menu: TableLayout

    private lateinit var pando_price: TextView
    private lateinit var pando_dist: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = "BeanGuru"

        setContentView(R.layout.activity_maps)
        val sugbtn = findViewById<Button>(R.id.btnsuggest)
        val ratbtn = findViewById<Button>(R.id.btnrating)
        donebtn = findViewById(R.id.btndone)
        val retbtn = findViewById<Button>(R.id.btnreturn)
        val constraintLayout: ConstraintLayout = findViewById(R.id.Maplayout)
        McardView= findViewById(R.id.Mview1)

        searchview = findViewById(R.id.searchview1)
        recView = findViewById(R.id.recView)
        recView.setHasFixedSize(true)
        recView.visibility = View.INVISIBLE

        recView.layoutManager = LinearLayoutManager(this)
        addToList()
        adapter = RecAdapter(iList, this)
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


        menu = findViewById(R.id.Menu)

        val table1: TableLayout = findViewById(R.id.table1)
        val table2: TableLayout = findViewById(R.id.table2)

        val shop1 = findViewById<Button>(R.id.shop1)
        val shop2 = findViewById<Button>(R.id.shop2)
        val shop3 = findViewById<Button>(R.id.shop3)
        val shop4 = findViewById<Button>(R.id.shop4)
        val shop5 = findViewById<Button>(R.id.shop5)
        val shop6 = findViewById<Button>(R.id.shop6)

        pando_price = findViewById(R.id.textViewm23)

        pando_dist = findViewById(R.id.textViewm33)

        val dbean_dict = mapOf<String, Int>(
            "Dutch Coffee" to 3800,
            "Dutch Americano" to 4300,
            "Dutch Latte" to 4300,
            "Hazelnut Dutch Latte" to 4800,
            "Cube Latte" to 4300,
            "Cube Condensed Milk Latte" to 4800,
            "Cube Cinnamon Latte" to 4800,
            "Espresso" to 3000,
            "Americano" to 3000,
            "Lungo/Ristretto" to 3300,
            "Cafe Latte" to 3500,
            "Cappucino" to 3500,
            "Vanilla Latte" to 4000,
            "Hazelnut Latte" to 4000,
            "Caramel Macchiato" to 4300,
            "Condensed Milk Latte" to 4300,
            "Cafe Mocha" to 4300,
            "Shot Toffee Nut Latte" to 4800,
            "Cheese Latte" to 5000,
            "Cacao Latte" to 3800,
            "Green Tea Latte" to 4000,
            "Sweet Potato Latte" to 4300,
            "Cookie&Cream Latte" to 4300,
            "Toffee Nut Latte" to 4300,
            "Mint Chocolate Latte" to 4500,
            "Citronade/Lemonade" to 4300,
            "Grapefruit Ade" to 4500,
            "Plain Yogurt" to 4800,
            "Strawberry Yogurt" to 4800,
            "Mango Yogurt" to 4800,
            "Blueberry Yogurt" to 4800,
            "Iced Tea" to 3000,
            "Fruit Tea" to 3800,
            "Blending Tea" to 3800,
            "Milk Tea" to 4800
        )
        pando_dict = mapOf<String, Int>(
            "Espresso" to 2000,
            "Americano" to 2000,
            "Hazelnut Americano" to 2300,
            "Cafe Latte" to 2700,
            "Dark/Java chip/Mint chocolate latte" to 3400,
            "Vanilla/Hazelnut/Caramel Latte" to 2800,
            "Dark/Java chip/Mint Mocha" to 3700,
            "Caramel Macchiato" to 3100,
            "Caramel Cafe Mocha" to 3300,
            "Cafe Mocha" to 3700,
            "Chocolate" to 2900,
            "Green Tea Latte" to 3200,
            "Purple Sweet Potato Latte" to 3200,
            "Cookie&Cream Latte" to 3300,
            "Grain Latte" to 3300,
            "Dark/Java chip/Mint Chocolate" to 3200,
            "Vanilla Latte" to 3300,
            "Mango/Strawberry/Blueberry Latte" to 3400,
            "Plain Yogurt" to 3800,
            "Strawberry Yogurt" to 4300,
            "Mango Yogurt" to 4300,
            "Blueberry Yogurt" to 4300,
            "Grapefruit/Lemon/Citron Yogurt" to 3800,
            "Organic Herb Tea" to 2800,
            "Black Tea" to 2800,
            "Peach Ice Tea" to 2500,
            "Fruit Tea" to 2800,
            "Blending Tea" to 3200,
            "Milk Tea" to 3400,
            "Strawberry/Blueberry/Mango Ade" to 2800,
            "Green Grape/Grapefruit ade" to 2800,
            "Grapefruit/Lemon/Citron Ade" to 2800,
            "Strawberry/Kiwi/Banana" to 3300,
            "Strawberry Banana/Kiwi Banana" to 3500,
            "Black Bubble Tea" to 3800,
            "Fruit Bubble Tea" to 3800,
            "Bubble Tea (Grain/Cookie and Cream/Sweet Potato)" to 3800,
            "Vanilla Frappe" to 3800,
            "Cookie&Cream Frappe" to 3800,
            "Choco Frape" to 3800,
            "Grain Frappe" to 3800,
            "Purple Sweet Potato Frappe" to 3800,
            "Mocha PanFrappe" to 3900,
            "Caramel PanFrappe" to 3900,
            "Choco PanFrappe" to 3900,
            "Vanilla PanFrappe" to 3900,
            "Cookie and Cream PanFrappe" to 3900
        )
        //val breadco_dict = mapOf<String, Int>()
        val caliu_dict = mapOf<String, Int>(
            "CALIU Latte" to 4800,
            "Sweer corn Latte" to 5500,
            "Puff Cream Latte" to 5500,
            "Turmeric Latte" to 5500,
            "Americano" to 3000,
            "Decaf Cold Brew" to 3800,
            "Caffe latte" to 4000,
            "Vanilla Latte" to 4300,
            "Condensed Milk Latte" to 4500,
            "Toffee Nut Latte" to 4500,
            "Ice Cream Latte" to 5000,
            "Sweet Matcha Latte" to 4500,
            "Real Choco Latte" to 4500,
            "Real Strawberry Latte" to 4500,
            "Real Mango Latte" to 4500,
            "Real Blueberry Latte" to 4500,
            "Milk Tea" to 4500,
            "Ice Tea" to 3300,
            "Lemonade" to 4500,
            "Grapefruit Ade" to 4500,
            "Orangeade" to 4500,
            "Plain Yogurt Smoothie" to 4500,
            "Strawberry Yogurt Smoothie" to 4500,
            "Mango Yogurt Smoothie" to 4500,
            "Blueberry Yogurt Smoothie" to 4500,
            "Chamomile" to 3500,
            "Peppermint" to 3500,
            "Earl Grey" to 3500,
            "Rooibos" to 3500,
            "Hibiscus" to 3500,
            "Honey Grapefruit Black Tea" to 4000,
            "Strawberry Juice" to 4700,
            "Kiwi Juice" to 4700,
            "Pineapple Juice" to 4700
        )
        val bookcafe_dict = mapOf<String, Int>(
            "Sweet Red Bean Latte" to 4800,
            "Jujube Latte" to 4800,
            "Green Tea Latte" to 4800,
            "Chocholate Latte" to 4800,
            "Sweet Potato Latte" to 4800,
            "Black Seame Latte" to 4800,
            "5 Whole Grains Soy Latte" to 4800,
            "Ginseng Sprout Latte" to 6000,
            "Misugaru Latte" to 4800,
            "Milk Tea" to 4800,
            "Espresso" to 3300,
            "Americano" to 3300,
            "Latte" to 3800,
            "Cappucino" to 3800,
            "Soy Latte" to 4300,
            "Vanilla Latte" to 4300,
            "Hazelnut Latte" to 4300,
            "Cafe Mocha" to 4300,
            "Caramel Macchiato" to 4300,
            "Decaf Drip Coffee" to 3800,
            "Affogato" to 5300,
            "Lemonade" to 4800,
            "Grapefruit Ade" to 4800,
            "Green Grape Ade" to 4800,
            "Jeju Tangerine" to 4800,
            "Chemomile" to 4800,
            "Peppermint" to 4800,
            "Korean Ginseong Sprout Tea" to 5500,
            "Chamomile Relaxer" to 4800,
            "Peach Iced Tea" to 3500,
            "Lemon Tea" to 4800,
            "Grapefruit Tea" to 4800,
            "Yuja Tea" to 4800,
            "Omija Tea" to 4800,
            "Plum Tea" to 4800,
            "Jujube Tea" to 4800,
            "Ginger Lemon Tea" to 4800,
            "Yulmu Tea" to 4800
        )


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
            menu.visibility = View.INVISIBLE

        }
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
        iList.add(Items("CALIU Latte"))
        iList.add(Items("Sweer corn Latte"))
        iList.add(Items("Puff Cream Latte"))
        iList.add(Items("Turmeric Latte"))
        iList.add(Items("Americano"))
        iList.add(Items("Decaf Cold Brew"))
        iList.add(Items("Caffe latte"))
        iList.add(Items("Vanilla Latte"))
        iList.add(Items("Condensed Milk Latte"))
        iList.add(Items("Toffee Nut Latte"))
        iList.add(Items("Ice Cream Latte"))
        iList.add(Items("Sweet Matcha Latte"))
        iList.add(Items("Real Choco Latte"))
        iList.add(Items("Real Strawberry Latte"))
        iList.add(Items("Real Mango Latte"))
        iList.add(Items("Real Blueberry Latte"))
        iList.add(Items("Milk Tea"))
        iList.add(Items("Ice Tea"))
        iList.add(Items("Lemonade"))
        iList.add(Items("Grapefruit Ade"))
        iList.add(Items("Orangeade"))
        iList.add(Items("Plain Yogurt Smoothie"))
        iList.add(Items("Strawberry Yogurt Smoothie"))
        iList.add(Items("Mango Yogurt Smoothie"))
        iList.add(Items("Blueberry Yogurt Smoothie"))
        iList.add(Items("Chamomile"))
        iList.add(Items("Peppermint"))
        iList.add(Items("Earl Grey"))
        iList.add(Items("Rooibos"))
        iList.add(Items("Hibiscus"))
        iList.add(Items("Honey Grapefruit Black Tea"))
        iList.add(Items("Strawberry Juice"))
        iList.add(Items("Kiwi Juice"))
        iList.add(Items("Pineapple Juice"))
        iList.add(Items("Sweet Red Bean Latte"))
        iList.add(Items("Jujube Latte"))
        iList.add(Items("Green Tea Latte"))
        iList.add(Items("Chocholate Latte"))
        iList.add(Items("Sweet Potato Latte"))
        iList.add(Items("Black Seame Latte"))
        iList.add(Items("5 Whole Grains Soy Latte"))
        iList.add(Items("Ginseng Sprout Latte"))
        iList.add(Items("Misugaru Latte"))
        iList.add(Items("Espresso"))
        iList.add(Items("Latte"))
        iList.add(Items("Cappucino"))
        iList.add(Items("Soy Latte"))
        iList.add(Items("Hazelnut Latte"))
        iList.add(Items("Cafe Mocha"))
        iList.add(Items("Caramel Macchiato"))
        iList.add(Items("Decaf Drip Coffee"))
        iList.add(Items("Affogato"))
        iList.add(Items("Green Grape Ade"))
        iList.add(Items("Jeju Tangerine"))
        iList.add(Items("Chemomile"))
        iList.add(Items("Korean Ginseong Sprout Tea"))
        iList.add(Items("Chamomile Relaxer"))
        iList.add(Items("Peach Iced Tea"))
        iList.add(Items("Lemon Tea"))
        iList.add(Items("Grapefruit Tea"))
        iList.add(Items("Yuja Tea"))
        iList.add(Items("Omija Tea"))
        iList.add(Items("Plum Tea"))
        iList.add(Items("Jujube Tea"))
        iList.add(Items("Ginger Lemon Tea"))
        iList.add(Items("Yulmu Tea"))
        iList.add(Items("Dutch Coffee"))
        iList.add(Items("Dutch Americano"))
        iList.add(Items("Dutch Latte"))
        iList.add(Items("Hazelnut Dutch Latte"))
        iList.add(Items("Cube Latte"))
        iList.add(Items("Cube Condensed Milk Latte"))
        iList.add(Items("Cube Cinnamon Latte"))
        iList.add(Items("Lungo/Ristretto"))
        iList.add(Items("Cafe Latte"))
        iList.add(Items("Shot Toffee Nut Latte"))
        iList.add(Items("Cheese Latte"))
        iList.add(Items("Cacao Latte"))
        iList.add(Items("Cookie&Cream Latte"))
        iList.add(Items("Mint Chocolate Latte"))
        iList.add(Items("Citronade/Lemonade"))
        iList.add(Items("Plain Yogurt"))
        iList.add(Items("Strawberry Yogurt"))
        iList.add(Items("Mango Yogurt"))
        iList.add(Items("Blueberry Yogurt"))
        iList.add(Items("Iced Tea"))
        iList.add(Items("Fruit Tea"))
        iList.add(Items("Blending Tea"))
        iList.add(Items("Hazelnut Americano"))
        iList.add(Items("Dark/Java chip/Mint chocolate latte"))
        iList.add(Items("Vanilla/Hazelnut/Caramel Latte"))
        iList.add(Items("Dark/Java chip/Mint Mocha"))
        iList.add(Items("Caramel Cafe Mocha"))
        iList.add(Items("Chocolate"))
        iList.add(Items("Purple Sweet Potato Latte"))
        iList.add(Items("Grain Latte"))
        iList.add(Items("Dark/Java chip/Mint Chocolate"))
        iList.add(Items("Mango/Strawberry/Blueberry Latte"))
        iList.add(Items("Grapefruit/Lemon/Citron Yogurt"))
        iList.add(Items("Organic Herb Tea"))
        iList.add(Items("Black Tea"))
        iList.add(Items("Peach Ice Tea"))
        iList.add(Items("Strawberry/Blueberry/Mango Ade"))
        iList.add(Items("Green Grape/Grapefruit ade"))
        iList.add(Items("Grapefruit/Lemon/Citron Ade"))
        iList.add(Items("Strawberry/Kiwi/Banana"))
        iList.add(Items("Strawberry Banana/Kiwi Banana"))
        iList.add(Items("Black Bubble Tea"))
        iList.add(Items("Fruit Bubble Tea"))
        iList.add(Items("Bubble Tea (Grain/Cookie and Cream/Sweet Potato)"))
        iList.add(Items("Vanilla Frappe"))
        iList.add(Items("Cookie&Cream Frappe"))
        iList.add(Items("Choco Frape"))
        iList.add(Items("Grain Frappe"))
        iList.add(Items("Purple Sweet Potato Frappe"))
        iList.add(Items("Mocha PanFrappe"))
        iList.add(Items("Caramel PanFrappe"))
        iList.add(Items("Choco PanFrappe"))
        iList.add(Items("Vanilla PanFrappe"))
        iList.add(Items("Cookie and Cream PanFrappe"))
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
    override fun ClickedItem(items: Items) {
        recView.visibility = View.INVISIBLE
        McardView.visibility = View.INVISIBLE
        donebtn.visibility = View.VISIBLE
        menu.visibility = View.VISIBLE
        searchview.isIconified = true

        for (i in pando_dict.keys) {
            if(items.title == i){
                recView.visibility = View.INVISIBLE
                searchview.isIconified = true
                pando_price.text = pando_dict[i].toString()
                pando_dist.text = distance_pando104!!.roundToInt().toString()
            }
        }

    }

}