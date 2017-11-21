package net.inferno.sunshine

import android.app.Application
import net.inferno.sunshine.data.WeatherDatabase
import net.inferno.sunshine.utils.WeatherUtils

class SunShine : Application() {
    override fun onCreate() {
        super.onCreate()
        WeatherUtils.init(this)
        WeatherDatabase.init(this)
    }
}