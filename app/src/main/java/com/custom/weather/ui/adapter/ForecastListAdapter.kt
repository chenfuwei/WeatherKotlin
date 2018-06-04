package com.custom.weather.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.custom.weather.R
import com.custom.weather.domain.model.Forecast
import com.custom.weather.domain.model.ForecastList
import com.custom.weather.extesions.ctx
import com.custom.weather.extesions.toDateString
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastListAdapter(private val weekForecast: ForecastList, private val itemClick: (Forecast) -> Unit) :
    RecyclerView.Adapter<ForecastListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)    }

    override fun getItemCount(): Int = weekForecast.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast[position])
    }

    class ViewHolder(val containerView : View, private val itemClick : (Forecast) -> Unit)
        : RecyclerView.ViewHolder(containerView){
        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                containerView.dateText.text = date.toDateString()
                containerView.descriptionText.text = description
                containerView.maxTemperature.text = "${high}º"
                containerView.minTemperature.text = "${low}º"
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}