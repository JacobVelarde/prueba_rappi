package com.jacob.pruebarappi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jacob.pruebarappi.databinding.FragmentRecommendedBinding
import com.jacob.pruebarappi.models.Trend
import com.jacob.pruebarappi.ui.adapters.GridRecommendedAdapter
import com.jacob.pruebarappi.ui.adapters.LanguageSpinnerAdapter
import com.jacob.pruebarappi.ui.adapters.YearSpinnerAdapter
import com.jacob.pruebarappi.viewmodel.RecommendedViewModel

class RecommendedFragment: Fragment() {

    var listRecommended = arrayListOf<Trend>()
    var listRecommendedAdapter = arrayListOf<Trend>()
    private var listLanguages = arrayListOf<String>()
    private var listYears = arrayListOf<String>()
    private var yearSelected = ""
    private var languageSelected = ""

    private lateinit var binding: FragmentRecommendedBinding
    private lateinit var gridRecommendedAdapter: GridRecommendedAdapter
    private lateinit var adapterLenguage: LanguageSpinnerAdapter
    private lateinit var adapterYear: YearSpinnerAdapter

    private val recommendedViewModel: RecommendedViewModel by viewModels()

    companion object{
        fun newInstance(): RecommendedFragment{
            return RecommendedFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setGridRecommended()
        setSpinnerLanguage()
        setSpinnerYear()
    }

    private fun setObservers(){
        recommendedViewModel.languages.observe(viewLifecycleOwner){
            listLanguages.clear()
            listLanguages.addAll(it)
            adapterLenguage.notifyDataSetChanged()
        }

        recommendedViewModel.years.observe(viewLifecycleOwner){
            listYears.clear()
            listYears.addAll(it)
            adapterYear.notifyDataSetChanged()
        }

        recommendedViewModel.filterRecommended.observe(viewLifecycleOwner){
            listRecommendedAdapter.clear()
            listRecommendedAdapter.addAll(it)
            gridRecommendedAdapter.notifyDataSetChanged()
        }
    }

    private fun setSpinnerLanguage(){
        adapterLenguage = LanguageSpinnerAdapter(binding.root.context, listLanguages)
        binding.spinnerLanguage.adapter = adapterLenguage


        binding.spinnerLanguage.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                languageSelected = listLanguages[position]
                recommendedViewModel.filterGrid(listRecommended, yearSelected, languageSelected)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setSpinnerYear(){
        adapterYear = YearSpinnerAdapter(binding.root.context, listYears)
        binding.spinnerYear.adapter = adapterYear

        binding.spinnerYear.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                yearSelected = listYears[position]
                recommendedViewModel.filterGrid(listRecommended, yearSelected, languageSelected)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setGridRecommended(){
        gridRecommendedAdapter = GridRecommendedAdapter(requireContext(), listRecommendedAdapter)
        val gridView = binding.gridRecommendedMovies
        gridView.adapter = gridRecommendedAdapter
    }

    fun updateGrid(list: ArrayList<Trend>){
        listRecommended.addAll(list)
        recommendedViewModel.updateLanguages(list)
        recommendedViewModel.updateYears(list)
        listRecommendedAdapter.addAll(list.take(6))
        gridRecommendedAdapter.notifyDataSetChanged()
    }
}