package com.jacob.pruebarappi.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacob.pruebarappi.R
import com.jacob.pruebarappi.base.MainBase
import com.jacob.pruebarappi.databinding.ActivityMainMenuBinding
import com.jacob.pruebarappi.models.Trend
import com.jacob.pruebarappi.models.UpComing
import com.jacob.pruebarappi.ui.adapters.CarouselTrendAdapter
import com.jacob.pruebarappi.ui.adapters.CarouselUpComingAdapter
import com.jacob.pruebarappi.ui.fragments.RecommendedFragment
import com.jacob.pruebarappi.viewmodel.MainMenuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenuActivity: MainBase() {

    lateinit var binding: ActivityMainMenuBinding
    private var recommendedFragment = RecommendedFragment.newInstance()
    private val mainMenuViewModel: MainMenuViewModel by viewModels()

    private var upComingList = arrayListOf<UpComing>()
    private var trendList = arrayListOf<Trend>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainMenuViewModel.error.observe(this, showError)
        mainMenuViewModel.loading.observe(this, loading)

        setCarouselUpCommingReleases()
        setCaroulselTrend()
        setRecommendedFragment()
        updateUpComing()
        updateTrend()
    }

    private fun setCarouselUpCommingReleases(){
        binding.carouselUpcoming.titleCarousel.text = getString(R.string.upcoming_releases)
        val carouselUpComingReleasesAdapter = CarouselUpComingAdapter(upComingList)
        binding.carouselUpcoming.moviesRecycler.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL,false)
        binding.carouselUpcoming.moviesRecycler.adapter = carouselUpComingReleasesAdapter

    }

    private fun setCaroulselTrend(){
        binding.carouselTrend.titleCarousel.text = getString(R.string.trend)
        val carouselUpComingReleasesAdapter = CarouselTrendAdapter(trendList)
        binding.carouselTrend.moviesRecycler.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL,false)
        binding.carouselTrend.moviesRecycler.adapter = carouselUpComingReleasesAdapter
    }

    private fun setRecommendedFragment(){
        replaceFragment(recommendedFragment, binding.containerFragment.id, false)
    }

    private fun updateUpComing(){
        mainMenuViewModel.getUpComing().observe(this) {
            with(it.results) {
                upComingList.addAll(this)
                binding.carouselUpcoming.moviesRecycler.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun updateTrend(){
        mainMenuViewModel.getTrend().observe(this) {
            with(it.results){
                trendList.addAll(this)
                binding.carouselTrend.moviesRecycler.adapter?.notifyDataSetChanged()
                recommendedFragment.updateGrid(trendList)
            }
        }
    }

}