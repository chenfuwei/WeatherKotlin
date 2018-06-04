package com.custom.weather.domain.commands

import com.custom.weather.data.server.ForecastServer
import com.custom.weather.domain.datasource.ForecastProvider
import com.custom.weather.domain.model.Forecast

class RequestDayForecastCommand(val id : Long, private val forecastProvider: ForecastProvider = ForecastProvider()) : Command<Forecast>{

    override fun execute(): Forecast = forecastProvider.requestForecast(id)
}