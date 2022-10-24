package com.jacob.pruebarappi.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jacob.pruebarappi.BuildConfig
import com.jacob.pruebarappi.databinding.ItemMovieBinding
import com.jacob.pruebarappi.models.Trend
import com.jacob.pruebarappi.ui.activities.DetailMovieActivity
import com.jacob.pruebarappi.utils.UtilsDates
import java.util.*

class CarouselTrendAdapter(private val items: List<Trend>):
    RecyclerView.Adapter<CarouselTrendAdapter.MediaViewHolder>(){

    inner class MediaViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        with(holder){
            with(items[position]){
                Glide.with(binding.root.context)
                    .load(BuildConfig.POSTER_URL.plus(this.posterPath))
                    .into(binding.poster)

                binding.poster.setOnClickListener {
                    val intent = Intent(binding.root.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.MOVIE_ID, this.id)
                    intent.putExtra(DetailMovieActivity.TITLE, this.title)
                    intent.putExtra(DetailMovieActivity.LANGUAGE, this.originalLanguage)
                    intent.putExtra(DetailMovieActivity.YEAR, UtilsDates.convertDateToCalendar(this.releaseDate).get(
                        Calendar.YEAR).toString())
                    intent.putExtra(DetailMovieActivity.AVERAGE, this.voteAverage)
                    intent.putExtra(DetailMovieActivity.POSTER, this.posterPath)
                    intent.putExtra(DetailMovieActivity.SINOPSIS, this.overview)
                    binding.root.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount() = items.size
}