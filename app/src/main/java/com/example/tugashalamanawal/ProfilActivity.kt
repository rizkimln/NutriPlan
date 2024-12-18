package com.example.tugashalamanawal

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ProfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profil)

        val editButton: ImageView = findViewById(R.id.edit_button)
        val locationSetting: LinearLayout = findViewById(R.id.location_setting)
        val locationArrow: ImageView = findViewById(R.id.location_arrow)

        // Navigate to Edit Profil Page
//        editButton.setOnClickListener {
//            val intent = Intent(this, EditProfilActivity::class.java)
//            startActivity(intent)
//        }

//        // Navigate to Lokasi Page when clicking "Lokasi" or the arrow
//        val navigateToLokasi = {
//            val intent = Intent(this, LokasiActivity::class.java)
//            startActivity(intent)
//        }

//        locationSetting.setOnClickListener { navigateToLokasi() }
//        locationArrow.setOnClickListener { navigateToLokasi() }
   }
}
