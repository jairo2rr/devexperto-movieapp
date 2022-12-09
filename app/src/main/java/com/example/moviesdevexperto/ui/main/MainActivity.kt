package com.example.moviesdevexperto.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.moviesdevexperto.R
import com.example.moviesdevexperto.databinding.ActivityMainBinding
import com.example.moviesdevexperto.model.Movie
import com.example.moviesdevexperto.model.MovieDbClient
import com.example.moviesdevexperto.ui.detail.DetailActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object{
        private val DEFAULT_REGION = "US"
    }

    private lateinit var  fusedLocationClient: FusedLocationProviderClient
    //Configure the recyclerview
    private val moviesAdapter = MoviesAdapter(emptyList()) {
        navigateToDetail(it)
    }
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        isGranted ->
        requestPopularMovies(isGranted)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        Log.d("MainActivity","onCreate is started")

        binding.recyclerMovies.adapter = moviesAdapter

        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

    }

    @SuppressLint("MissingPermission")
    private fun requestPopularMovies(isLocationGranted: Boolean) {
        if(isLocationGranted){
            fusedLocationClient.lastLocation.addOnCompleteListener {
                doRequestPopularMovies(getRegionFromLocation(it.result))
            }
        }
    }

    private fun doRequestPopularMovies(region: String) {
        lifecycleScope.launch{
            val apiKey = getString(R.string.api_key)
            val popularMovies = MovieDbClient.service.listPopularMovies(apiKey,region)
            moviesAdapter.movies = popularMovies.results
            moviesAdapter.notifyDataSetChanged() //Se ejecuta el cambio del recycler
        }
    }

    private fun getRegionFromLocation(location: Location?):String{
        if(location == null){
            return DEFAULT_REGION
        }
        val geocoder = Geocoder(this)
        val result = geocoder.getFromLocation(
            location.altitude,
            location.longitude,
            1
        )
        return result.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }

    private fun navigateToDetail(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE,movie)
        startActivity(intent)
    }

}