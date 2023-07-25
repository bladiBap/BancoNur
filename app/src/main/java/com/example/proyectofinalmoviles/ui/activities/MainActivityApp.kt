package com.example.proyectofinalmoviles.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.proyectofinalmoviles.R
import com.example.proyectofinalmoviles.databinding.ActivityMainAppBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivityApp : AppCompatActivity() {

    private lateinit var binding: ActivityMainAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main_app)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_qr, R.id.map, R.id.navigation_transferencia, R.id.navegation_movimiento
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


    companion object {
        const val TOKEN_TAG = "token"
        fun newIntent(context: Context, token: String): Intent {
            val intent = Intent(context, MainActivityApp::class.java)
            intent.putExtra(TOKEN_TAG, token)
            return intent
        }
    }
}