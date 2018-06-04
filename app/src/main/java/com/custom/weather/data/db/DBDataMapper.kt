package com.custom.weather.data.db

import com.custom.weather.domain.model.Forecast
import com.custom.weather.domain.model.ForecastList

class DBDataMapper{
    fun convertFromDomain(forecast: ForecastList) = with(forecast){
        val daily = dailyForecast.map { convertDayFromDomain(id, it) }
        CityForecast(id, city, country, daily)
    }

    private fun convertDayFromDomain(cityId: Long, forecast: Forecast) = with(forecast){
        DayForecast(date, description, high, low, iconUrl, cityId)
    }

    fun convertToDomain(forecast: CityForecast) = with(forecast){
        var daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(_id, city,country, daily)
    }

    fun convertDayToDomain(dayForecast: DayForecast) = with(dayForecast){
        Forecast(_id, date, description, high, low, iconUrl)
    }
}