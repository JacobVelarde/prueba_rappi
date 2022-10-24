package com.jacob.pruebarappi.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jacob.pruebarappi.base.BaseViewModel
import com.jacob.pruebarappi.models.Trend
import com.jacob.pruebarappi.utils.UtilsDates
import java.util.*
import kotlin.collections.ArrayList

class RecommendedViewModel: BaseViewModel() {

    val languages = MutableLiveData<ArrayList<String>>()
    val years = MutableLiveData<ArrayList<String>>()
    val filterRecommended = MutableLiveData<List<Trend>>()

    fun updateLanguages(items: ArrayList<Trend>){
        val languagesText = arrayListOf<String>()

        for(trend in items){
            val localeLanguage = trend.originalLanguage
            if (languagesText.isEmpty()){
                languagesText.add(localeLanguage)
            }else{
                var addText = true
                for (language in languagesText){
                    if (language == localeLanguage){
                        addText = false
                        break
                    }
                }
                if (addText){
                    languagesText.add(localeLanguage)
                }
            }
        }

        if(languagesText.isNotEmpty()){
            languagesText.sort()
            languages.postValue(languagesText)
        }
    }

    fun updateYears(items: ArrayList<Trend>){

        val yearsText = arrayListOf<String>()

        for(trend in items){
            if (yearsText.isEmpty()){
                yearsText.add(UtilsDates.convertDateToCalendar(trend.releaseDate).get(Calendar.YEAR).toString())
            }else{
                var addText = true
                for (year in yearsText){
                    if (year == UtilsDates.convertDateToCalendar(trend.releaseDate).get(Calendar.YEAR).toString()){
                        addText = false
                        break
                    }
                }

                if (addText){
                    yearsText.add(UtilsDates.convertDateToCalendar(trend.releaseDate).get(Calendar.YEAR).toString())
                }
            }
        }

        if(yearsText.isNotEmpty()){
            yearsText.sort()
            years.postValue(yearsText)
        }
    }

    fun filterGrid(items: ArrayList<Trend>, year: String, language: String){

        if (year.isNotEmpty() && language.isNotEmpty()){
            val filter = items.filter {
                UtilsDates.convertDateToCalendar(it.releaseDate).get(Calendar.YEAR).toString() == year &&
                        it.originalLanguage == language
            }

            if (filter.isNotEmpty()){
                filterRecommended.postValue(if (filter.size > 6) filter.take(6) else filter )
            }else{
                filterRecommended.postValue(filter)
            }
        }

    }
}