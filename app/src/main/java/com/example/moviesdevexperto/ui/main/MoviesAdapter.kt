package com.example.moviesdevexperto.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdevexperto.databinding.ItemViewMovieBinding
import com.example.moviesdevexperto.model.Movie

class MoviesAdapter(
    var movies:List<Movie>,
    private val movieClickedListener:(Movie) -> Unit
    ):RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // The first way without binding
        // val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_movie,parent,false)
        // return ViewHolder(view)

        // With binding
        val binding = ItemViewMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { movieClickedListener(movie) }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    // The first way without binding
    // class ViewHolder(view:View):RecyclerView.ViewHolder(view)

    class ViewHolder(private val binding: ItemViewMovieBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(movie:Movie){
            binding.tvTitle.text = movie.title
            Glide
                .with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w185/${movie.poster_path}")
                .into(binding.ivCover);
        }
    }
}