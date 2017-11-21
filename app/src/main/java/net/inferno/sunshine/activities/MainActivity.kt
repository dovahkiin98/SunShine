package net.inferno.sunshine.activities

import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import net.inferno.sunshine.ForecastAdapter
import net.inferno.sunshine.R
import net.inferno.sunshine.async.WeatherLoader
import net.inferno.sunshine.data.Weather
import net.inferno.sunshine.data.WeatherDatabase
import net.inferno.sunshine.utils.WeatherUtils

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener, LoaderManager.LoaderCallbacks<List<Weather>?> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        loadOfflineData()
        loadData()

        refreshLayout.setOnRefreshListener { loadData() }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        WeatherUtils.init(this)
        loadData()
    }

    fun loadOfflineData() {

    }

    private fun loadData() = supportLoaderManager.initLoader(22, Bundle.EMPTY, this).forceLoad()

    override fun onCreateLoader(id: Int, args: Bundle?) = WeatherLoader(this)

    override fun onLoadFinished(loader: Loader<List<Weather>?>, data: List<Weather>?) {
        if (data == null) Snackbar.make(container, R.string.network_error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, { loadData() }).show()
        else {
            forecastWeather.adapter = ForecastAdapter(data)
        }

        forecastWeather.setHasFixedSize(true)
        refreshLayout.isRefreshing = false
    }

    override fun onLoaderReset(loader: Loader<List<Weather>?>) = Unit


}
