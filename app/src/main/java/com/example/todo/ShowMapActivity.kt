package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.appcompat.app.ActionBar
import com.example.todo.databinding.ActivityShowMapBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class ShowMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityShowMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (supportActionBar != null) {
            val actionBar: ActionBar? = supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(false)
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        val noteType = intent.getStringExtra("noteType")
        var latitude = intent.getDoubleExtra("latitude", 91.0)
        var longitude = intent.getDoubleExtra("longitude", 181.0)
        if (latitude!=91.0 && longitude!=181.0){
            val location = LatLng(latitude, longitude)
            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(latitude, longitude))
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f))
        }else googleMap.minZoomLevel

        if (noteType.equals("New")){
            googleMap.setOnMapClickListener {
                latitude = it.latitude
                longitude = it.longitude
                intent = Intent(applicationContext, AddEditNoteActivity::class.java)
                intent.putExtra("noteType", "New")
                intent.putExtra("latitude", latitude)
                intent.putExtra("longitude", longitude)
                startActivity(intent)
            }
        }

        if (noteType.equals("Edit")){
            googleMap.setOnMapClickListener {
                latitude = it.latitude
                longitude = it.longitude
                intent = Intent(applicationContext, AddEditNoteActivity::class.java)
                intent.putExtra("noteType", "Edit")
                intent.putExtra("latitude", latitude)
                intent.putExtra("longitude", longitude)
                startActivity(intent)
            }
        }
    }
}

