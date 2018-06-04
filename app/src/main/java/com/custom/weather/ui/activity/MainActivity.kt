package com.custom.weather.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import com.custom.weather.R
import com.custom.weather.domain.commands.RequestForecastCommand
import com.custom.weather.domain.model.ForecastList
import com.custom.weather.extesions.DelegatesExt
import com.custom.weather.ui.adapter.ForecastListAdapter
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import java.util.function.Consumer

class MainActivity : AppCompatActivity(), ToolbarManager {
    private val zipCode: Long by DelegatesExt.preference(this, SettingsActivity.ZIP_CODE,
            SettingsActivity.DEFAULT_ZIP)
    override val toolbar: Toolbar
        get() = find(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    private fun loadForecast() {
        Observable.create<ForecastList> {
            val reslt = RequestForecastCommand(zipCode).execute()
            it.onNext(reslt)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { updateUI(it) }
    }
//            async(UI) {
//        val result = bg { RequestForecastCommand(zipCode).execute() }
//        updateUI(result.await())
//    }


    private fun updateUI(weekForecast: ForecastList){
        val adapter = ForecastListAdapter(weekForecast){
            Log.i("updateUI", "updateUI oijflksjd")
        }
        forecastList.adapter = adapter
        toolbarTitle = "${weekForecast.city} (${weekForecast.country})"
    }
}
