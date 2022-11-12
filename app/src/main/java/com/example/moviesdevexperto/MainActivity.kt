package com.example.moviesdevexperto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.moviesdevexperto.databinding.ActivityMainBinding
import com.example.moviesdevexperto.model.Movie
import com.example.moviesdevexperto.model.MovieDbClient
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        // other way: setContentView(R.layout.activity_main)
        // 'Log.d' to show something on the console

        Log.d("MainActivity","onCreate is started")

        //Configure the recyclerview
        val moviesAdapter = MoviesAdapter(emptyList()) {
            // movie ->
            // it: es el parametro que se le pasa a la lambda
            navigateToDetail(it)
        }
        binding.recyclerMovies.adapter = moviesAdapter

        lifecycleScope.launch{
            val apiKey = getString(R.string.api_key)
            val popularMovies = MovieDbClient.service.listPopularMovies(apiKey)
            moviesAdapter.movies = popularMovies.results
            moviesAdapter.notifyDataSetChanged()
        }
        /*thread {
            val apiKey = getString(R.string.api_key)
            val popularMovies = MovieDbClient.service.listPopularMovies(apiKey)
            val body = popularMovies.execute().body()
            runOnUiThread {
                if(body != null) {
                    moviesAdapter.movies = body.results
                    moviesAdapter.notifyDataSetChanged()
                }
            }

        }*//*This is for thread within Coroutines*/

    }

    private fun navigateToDetail(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE,movie)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity","onDestroy")
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}