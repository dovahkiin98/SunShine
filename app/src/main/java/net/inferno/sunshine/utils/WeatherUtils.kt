package net.inferno.sunshine.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.preference.PreferenceManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import net.inferno.sunshine.R
import net.inferno.sunshine.data.Weather
import net.inferno.sunshine.data.WeatherContract
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

object WeatherUtils {
    private val forecastUrl = "http://api.openweathermap.org/data/2.5/forecast"
    private val appId = "c05b6b5f53ca1aed10cd835094c317a4"

    private lateinit var queue: RequestQueue
    private lateinit var location: String
    private lateinit var unit: String
    private lateinit var language: String

    val forecastApi
        get() = Uri.parse(forecastUrl).buildUpon()
                .appendQueryParameter("q", location)
                .appendQueryParameter("units", unit)
                .appendQueryParameter("appid", appId)
                .appendQueryParameter("lang", language)
                .toString()

    fun init(context: Context) {
        queue = Volley.newRequestQueue(context)
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        location = prefs.getString("location", "Damascus,SY")
        unit = prefs.getString("unit", "metric")
        language = Locale.getDefault().language
    }

    fun fromJson(json: JSONObject): Weather {
        val obj = Weather()
        val main = json.getJSONObject("main")
        val wind = json.getJSONObject("wind")
        val clouds = json.getJSONObject("clouds")
        val weather = json.getJSONArray("weather").getJSONObject(0)
        var rain = 0.0
        var snow = 0.0

        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.getString("dt_txt"))
        obj.time = date.time
        obj.maxTemp = main.getDouble("temp_max")
        obj.minTemp = main.getDouble("temp_min")
        obj.pressure = main.getDouble("pressure")
        obj.humidity = main.getInt("humidity")

        obj.description = description(weather.getInt("id"))
        obj.windSpeed = wind.getDouble("speed")
        obj.windDegree = wind.getDouble("deg")
        obj.clouds = clouds.getInt("all")

        obj.iconRes = iconRes(weather.getInt("id"))
        obj.rain = rain
        obj.snow = snow

        return obj
    }

    fun fromCursor(cursor: Cursor, full: Boolean): Weather {
        val weather = Weather()

        weather.maxTemp = cursor.getDouble(cursor.getColumnIndex(WeatherContract.COLUMN_MAX_TEMP))
        weather.minTemp = cursor.getDouble(cursor.getColumnIndex(WeatherContract.COLUMN_MIN_TEMP))
        weather.description = cursor.getInt(cursor.getColumnIndex(WeatherContract.COLUMN_DESCRIPTION))
        weather.iconRes = cursor.getInt(cursor.getColumnIndex(WeatherContract.COLUMN_ICON))
        weather.time = cursor.getLong(cursor.getColumnIndex(WeatherContract.COLUMN_TIME))

        if (full) {
            weather.pressure = cursor.getDouble(cursor.getColumnIndex(WeatherContract.COLUMN_PRESSURE))
            weather.humidity = cursor.getInt(cursor.getColumnIndex(WeatherContract.COLUMN_HUMIDITY))
            weather.windSpeed = cursor.getDouble(cursor.getColumnIndex(WeatherContract.COLUMN_WIND_SPEED))
            weather.windDegree = cursor.getDouble(cursor.getColumnIndex(WeatherContract.COLUMN_WIND_DEGREE))
        }
        return weather
    }

    private fun description(weatherId: Int): Int {
        return when (weatherId) {
            in 200..232 -> R.string.condition_2xx
            in 300..321 -> R.string.condition_3xx
            500 -> R.string.condition_500
            501 -> R.string.condition_501
            502 -> R.string.condition_502
            503 -> R.string.condition_503
            511 -> R.string.condition_511
            520 -> R.string.condition_520
            521 -> R.string.condition_521
            522 -> R.string.condition_522
            531 -> R.string.condition_531
            600 -> R.string.condition_600
            601 -> R.string.condition_601
            602 -> R.string.condition_602
            611 -> R.string.condition_611
            612 -> R.string.condition_612
            701 -> R.string.condition_701
            711 -> R.string.condition_711
            721 -> R.string.condition_721
            731 -> R.string.condition_731
            741 -> R.string.condition_741
            751 -> R.string.condition_751
            761 -> R.string.condition_761
            762 -> R.string.condition_762
            771 -> R.string.condition_771
            781 -> R.string.condition_781
            800 -> R.string.condition_800
            801 -> R.string.condition_801
            802 -> R.string.condition_802
            803 -> R.string.condition_803
            804 -> R.string.condition_804
            900 -> R.string.condition_900
            901 -> R.string.condition_901
            902 -> R.string.condition_902
            903 -> R.string.condition_903
            904 -> R.string.condition_904
            905 -> R.string.condition_905
            906 -> R.string.condition_906
            951 -> R.string.condition_951
            952 -> R.string.condition_952
            953 -> R.string.condition_953
            954 -> R.string.condition_954
            955 -> R.string.condition_955
            956 -> R.string.condition_956
            957 -> R.string.condition_957
            958 -> R.string.condition_958
            959 -> R.string.condition_959
            960 -> R.string.condition_960
            961 -> R.string.condition_961
            962 -> R.string.condition_962
            else -> 0
        }
    }

    private fun iconRes(weatherId: Int): Int {
        return when (weatherId) {
            in 200..232 -> R.drawable.ic_storm
            in 300..321 -> R.drawable.ic_light_rain
            in 500..504 -> R.drawable.ic_rain
            511 -> R.drawable.ic_snow
            in 520..531 -> R.drawable.ic_rain
            in 600..622 -> R.drawable.ic_snow
            in 701..761 -> R.drawable.ic_fog
            761, 771, 781 -> R.drawable.ic_storm
            800 -> R.drawable.ic_clear
            801 -> R.drawable.ic_light_clouds
            in 802..804 -> R.drawable.ic_clouds
            in 900..906 -> R.drawable.ic_storm
            in 958..962 -> R.drawable.ic_storm
            in 951..957 -> R.drawable.ic_clear
            else -> R.drawable.ic_storm
        }
    }
}