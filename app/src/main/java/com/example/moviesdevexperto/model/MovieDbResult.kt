package com.example.moviesdevexperto.model

data class MovieDbResult(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)