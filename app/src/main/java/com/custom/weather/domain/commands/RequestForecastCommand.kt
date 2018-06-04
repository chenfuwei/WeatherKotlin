package com.custom.weather.domain.commands

import com.custom.weather.domain.datasource.ForecastProvider
import com.custom.weather.domain.model.ForecastList

class RequestForecastCommand(private val zipCode: Long, private val forecastProvider: ForecastProvider = ForecastProvider()):
Command<ForecastList>{

    companion object {
        const val DAYS = 7
    }
    override fun execute(): ForecastList = forecastProvider.requestByZipCode(zipCode, DAYS)
}