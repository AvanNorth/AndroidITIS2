package com.pskda.androiditis2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pskda.androiditis2.R

class WeatherAdapter(private val nameList: List<String>, private val tempList: List<Float>):
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>()
{
    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cityNameTV: TextView = itemView.findViewById(R.id.name_tv)
        val cityTempTV: TextView = itemView.findViewById(R.id.temp_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return WeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.cityNameTV.text = nameList[position]
        holder.cityTempTV.text = tempList[position].toString()
    }

    override fun getItemCount() = 10
}