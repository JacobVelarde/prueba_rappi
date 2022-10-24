package com.jacob.pruebarappi.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.jacob.pruebarappi.databinding.ItemLanguageBinding
import java.util.*
import kotlin.collections.ArrayList

class LanguageSpinnerAdapter(
    context: Context,
    private val listItems: ArrayList<String>
) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var binding: ItemLanguageBinding

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView)
    }


    private fun initView(position: Int, convertView: View?): View{
        var view = convertView
        val holder: ItemViewHolder

        if (view == null) {
            binding = ItemLanguageBinding.inflate(inflater)
            view = binding.root
            holder = ItemViewHolder()
            holder.language = binding.text
            view.tag = holder
        } else {
            holder = view.tag  as ItemViewHolder
        }

        with(listItems[position]){
            //TODO Convert language to apropriate name to user
            val displayLanguage = Locale(this).displayLanguage
            holder.language.text = displayLanguage
        }

        return view
    }

    override fun getCount() = listItems.size

    internal class ItemViewHolder{
        lateinit var language: TextView
    }

}