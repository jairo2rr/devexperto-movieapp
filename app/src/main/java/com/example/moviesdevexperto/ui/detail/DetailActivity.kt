package com.example.moviesdevexperto.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.bumptech.glide.Glide
import com.example.moviesdevexperto.R
import com.example.moviesdevexperto.databinding.ActivityDetailBinding
import com.example.moviesdevexperto.model.Movie

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "DetailActivity:movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        if (movie != null){
            title = movie.title
            Glide
                .with(this)
                .load("https://image.tmdb.org/t/p/w780/${movie.backdrop_path}")
                .into(binding.imageMovie)
            binding.overview.text = movie.overview
            bindDetailInfo(binding.detailInfo,movie)
        }

    }

    private fun bindDetailInfo(detailInfo: TextView, movie: Movie) {
        /*buildSpannedString: nos permite codificar lineas de codigo en texto y asignarles
        * un appereance a cada linea de texto*/
        detailInfo.text = buildSpannedString {
            appendInfo(R.string.original_language,movie.original_language)
            appendInfo(R.string.original_title,movie.original_title)
            appendInfo(R.string.release_date,movie.release_date)
            appendInfo(R.string.popularity,movie.popularity.toString())
            appendInfo(R.string.vote_average,movie.vote_average.toString())

            /*!Important: Para agregar texto sin strings*/

            /*bold { append("Original language: ") }
            appendLine(movie.original_language)

            bold { append("Original title: ") }
            appendLine(movie.original_title)

            bold { append("Release Date: ") }
            appendLine(movie.release_date)*/
        }
    }

    private fun SpannableStringBuilder.appendInfo(stringRes: Int, value:String){
        bold {
            append(getString(stringRes))
            append(": ")
        }
        appendLine(value)
    }
}