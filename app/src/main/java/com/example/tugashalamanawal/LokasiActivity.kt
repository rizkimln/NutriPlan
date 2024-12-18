package com.example.tugashalamanawal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale

class LokasiActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var addressInput: EditText
    private lateinit var saveButton: Button
    private val database = FirebaseDatabase.getInstance().reference.child("user_locations")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lokasi)

        // Initialize views
        addressInput = findViewById(R.id.addressInput)
        saveButton = findViewById(R.id.saveButton)

        // Load Map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        saveButton.setOnClickListener {
            val address = addressInput.text.toString()
            if (address.isNotEmpty()) {
                saveAddressAndMarkLocation(address)
            } else {
                Toast.makeText(this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }

    private fun saveAddressAndMarkLocation(address: String) {
        val geocoder = android.location.Geocoder(this, Locale.getDefault())

        try {
            val addresses = geocoder.getFromLocationName(address, 1)
            if (!addresses.isNullOrEmpty()) {
                val location = addresses[0]
                val latLng = LatLng(location.latitude, location.longitude)

                // tanda di map
                googleMap.clear()
                googleMap.addMarker(MarkerOptions().position(latLng).title("Lokasi: $address"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

                // simmpan di Firebase
                val key = database.push().key
                val locationData = mapOf(
                    "id" to key,
                    "address" to address,
                    "latitude" to location.latitude,
                    "longitude" to location.longitude
                )

                database.child(key!!).setValue(locationData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Alamat disimpan ke Firebase", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal menyimpan alamat", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Alamat tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
