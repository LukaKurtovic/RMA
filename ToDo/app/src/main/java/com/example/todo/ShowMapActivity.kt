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

        if (noteType.equals("New")) {
            val title = intent.getStringExtra("title")
            val description = intent.getStringExtra("description")
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(0.0, 0.0)))
            googleMap.setOnMapClickListener {
                val lat = it.latitude
                val long = it.longitude
                intent = Intent(applicationContext, AddEditNoteActivity::class.java)
                intent.putExtra("noteType", "NewMap")
                intent.putExtra("title", title)
                intent.putExtra("description", description)
                intent.putExtra("latitude", lat)
                intent.putExtra("longitude", long)
                startActivity(intent)
            }
        }

        if (noteType.equals("Edit")) {
            val title = intent.getStringExtra("title")
            val description = intent.getStringExtra("description")
            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)
            val location = LatLng(latitude, longitude)
            googleMap.addMarker(
                MarkerOptions()
                    .title(title)
                    .position(LatLng(latitude, longitude))
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f))
            googleMap.setOnMapClickListener {
                val lat = it.latitude
                val long = it.longitude
                intent = Intent(applicationContext, AddEditNoteActivity::class.java)
                intent.putExtra("noteType", "NewMap")
                intent.putExtra("title", title)
                intent.putExtra("description", description)
                intent.putExtra("latitude", lat)
                intent.putExtra("longitude", long)
                startActivity(intent)
            }
        }

        if (noteType.equals("EditExisting")) {
            val title = intent.getStringExtra("title")
            val description = intent.getStringExtra("description")
            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)
            val noteID = intent.getIntExtra("noteID", -1)
            val location = LatLng(latitude, longitude)
            googleMap.addMarker(
                MarkerOptions()
                    .title(title)
                    .position(LatLng(latitude, longitude))
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17f))
            googleMap.setOnMapClickListener {
                val lat = it.latitude
                val long = it.longitude
                intent = Intent(applicationContext, AddEditNoteActivity::class.java)
                intent.putExtra("noteType", "ExistingMap")
                intent.putExtra("title", title)
                intent.putExtra("description", description)
                intent.putExtra("latitude", lat)
                intent.putExtra("longitude", long)
                intent.putExtra("noteID", noteID)
                startActivity(intent)
            }

        }
    }
}
