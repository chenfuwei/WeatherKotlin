package com.custom.weather.data.db

import android.util.Log
import com.custom.weather.domain.datasource.ForecastDataSource
import com.custom.weather.domain.model.Forecast
import com.custom.weather.domain.model.ForecastList
import com.custom.weather.extesions.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class ForecastDb(private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
                 private val dataMapper: DBDataMapper = DBDataMapper()) : ForecastDataSource{
    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? = forecastDbHelper.use {
        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"
        val dailyForecast = select(DayForecastTable.NAME).
                whereSimple(dailyRequest, zipCode.toString(), date.toString()).
                parseList { DayForecast(HashMap(it)) }
        val city = select(CityForecastTable.NAME).
                whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { CityForecast(HashMap(it), dailyForecast) }
        val forecastList = city?.let { dataMapper.convertToDomain(it) }
        Log.i("ForecastDb", "forecastList size = ${forecastList?.size}" )
        forecastList
    }

    override fun requestDayForecast(id: Long): Forecast? = forecastDbHelper.use {
        val forecast = select(DayForecastTable.NAME).byId(id).
                parseOpt { DayForecast(HashMap(it)) }

        forecast?.let { dataMapper.convertDayToDomain(it) }
    }

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecast)){
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
        }
    }
}