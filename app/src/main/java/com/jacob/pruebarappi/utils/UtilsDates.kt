package com.jacob.pruebarappi.utils

import java.util.*

class UtilsDates {
    companion object{
        fun convertDateToCalendar(date: Date): Calendar{
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar
        }
    }
}