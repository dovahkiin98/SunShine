package net.inferno.sunshine.async

import android.app.IntentService
import android.content.Intent

class WeatherService : IntentService("WeatherService") {

    companion object {
        val loadAction = "WeatherService.Load"
    }

    override fun onHandleIntent(intent: Intent) {
        if (intent.action == loadAction) {

        }
    }
}
