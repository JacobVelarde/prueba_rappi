package com.jacob.pruebarappi.ui.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jacob.pruebarappi.BuildConfig
import com.jacob.pruebarappi.R
import com.jacob.pruebarappi.databinding.ItemMovieBinding
import com.jacob.pruebarappi.databinding.ItemMovieRecomendedBinding
import com.jacob.pruebarappi.models.Trend
import com.jacob.pruebarappi.ui.activities.DetailMovieActivity
import com.jacob.pruebarappi.utils.UtilsDates
import java.util.*
import kotlin.collections.ArrayList

class GridRecommendedAdapter(
    context: Context,
    private val items: ArrayList<Trend>
    ): ArrayAdapter<Trend>(context, R.layout.item_movie, items) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var binding: ItemMovieRecomendedBinding

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        var convertView = view
        val holder: ItemViewHolder

        if (convertView == null) {
            binding = ItemMovieRecomendedBinding.inflate(inflater)
            convertView = binding.root
            holder = ItemViewHolder()
            holder.poster = binding.poster
            convertView.tag = holder

        } else {
            holder = convertView.tag as ItemViewHolder
        }

        with(items[position]){
            Glide.with(binding.root.context)
                .load(BuildConfig.POSTER_URL.plus(this.posterPath))
                .into(holder.poster!!)

            holder.poster!!.setOnClickListener {
                val intent = Intent(binding.root.context, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.MOVIE_ID, this.id)
                intent.putExtra(DetailMovieActivity.TITLE, this.title)
                intent.putExtra(DetailMovieActivity.LANGUAGE, this.originalLanguage)
                intent.putExtra(DetailMovieActivity.YEAR, UtilsDates.convertDateToCalendar(this.releaseDate).get(Calendar.YEAR).toString())
                intent.putExtra(DetailMovieActivity.AVERAGE, this.voteAverage)
                intent.putExtra(DetailMovieActivity.POSTER, this.posterPath)
                intent.putExtra(DetailMovieActivity.SINOPSIS, this.overview)
                binding.root.context.startActivity(intent)
            }
        }

        return convertView

    }

    override fun getCount() = items.size


    internal class ItemViewHolder {
        var poster: ImageView? = null
    }

}