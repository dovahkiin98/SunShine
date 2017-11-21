package net.inferno.sunshine.async

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import net.inferno.sunshine.data.Weather
import net.inferno.sunshine.data.WeatherDatabase
import net.inferno.sunshine.utils.WeatherUtils
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class WeatherLoader(context: Context) : AsyncTaskLoader<List<Weather>?>(context) {

    override fun loadInBackground(): List<Weather>? {
        val uri = URL(WeatherUtils.forecastApi)
        val http = uri.openConnection() as HttpURLConnection

        try {
            http.connectTimeout = 10000
            http.readTimeout = 10000
            http.connect()
            val scanner = Scanner(http.inputStream)
            scanner.useDelimiter("\\A")
            val string = scanner.next()
            val json = JSONObject(string)
            val list = json.getJSONArray("list")
            val weatherList = mutableListOf<Weather>()
            var i = 0
            while (i < list.length()) {
                val weather = WeatherUtils.fromJson(list.getJSONObject(i))
                weatherList += weather
                WeatherDatabase.insert(weather)
                i += 8
            }
            return weatherList
        } catch (e: Exception) {
            return null
        }
    }
}