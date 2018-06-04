package com.custom.weather.domain.datasource

import com.custom.weather.domain.model.Forecast
import com.custom.weather.domain.model.ForecastList

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long): Forecast?
}