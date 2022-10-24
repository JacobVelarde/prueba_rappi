package com.jacob.pruebarappi.ui.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.jacob.pruebarappi.BuildConfig
import com.jacob.pruebarappi.base.MainBase
import com.jacob.pruebarappi.databinding.ActivityDetailMovieBinding
import com.jacob.pruebarappi.viewmodel.DetailMovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailMovieActivity: MainBase() {

    companion object{
        const val TITLE = "title"
        const val LANGUAGE = "language"
        const val YEAR = "year"
        const val AVERAGE = "average"
        const val POSTER = "poster"
        const val SINOPSIS = "sinopsis"
        const val MOVIE_ID = "movie_id"
    }

    private var urlTrailer = ""
    private var movieId: Long = 0L
    lateinit var binding: ActivityDetailMovieBinding
    private val detailMovieViewModel: DetailMovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        getTrailer()
        openTrailer()
        configureBackButton()
    }

    private fun configureBackButton(){
        binding.btnBack.setOnClickListener{
            onBackPressed()
        }
    }

    private fun setView(){
        if (intent.extras != null){
            movieId = intent.getLongExtra(MOVIE_ID, 0)
            binding.titleMovie.text = intent.getStringExtra(TITLE)
            binding.yearMovie.text = intent.getStringExtra(YEAR)
            binding.languageMovie.text = intent.getStringExtra(LANGUAGE)
            binding.averageMovie.text = intent.getDoubleExtra(AVERAGE, 0.0).toString()
            binding.textSinopsis.text = intent.getStringExtra(SINOPSIS)
            var posterPath = intent.getStringExtra(POSTER)

            Glide.with(this)
                .asBitmap()
                .load(BuildConfig.POSTER_URL.plus(posterPath))
                .fitCenter()
                .centerInside()
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap?>?
                    ) {
                        val drawable = BitmapDrawable(resources, resource)
                        binding.containerPoster.background = drawable
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
    }

    private fun getTrailer(){
        detailMovieViewModel.getVideos(movieId.toInt()).observe(this){
            with(it.results){
                urlTrailer = when(this[0].site){
                    "YouTube" -> BuildConfig.YOUTUBE.plus(this[0].key)
                    "Vimeo" -> BuildConfig.VIMEO.plus(this[0].key)
                    else -> {""}
                }
            }
        }
    }

    private fun openTrailer(){

        binding.btnShowTrailer.setOnClickListener{
            if (urlTrailer.isEmpty()){
                showMessageError("Url Not Found")
            }else{
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(urlTrailer)
                startActivity(i)
            }
        }
    }
}