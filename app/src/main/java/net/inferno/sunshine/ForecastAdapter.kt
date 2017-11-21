package net.inferno.sunshine

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.inferno.sunshine.data.Weather
import net.inferno.sunshine.databinding.TitleWeatherItemBinding
import net.inferno.sunshine.databinding.WeatherItemBinding

class ForecastAdapter(private val list: List<Weather>) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) = holder.bind(list[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder = ForecastViewHolder(
            LayoutInflater.from(parent.context).inflate(
                    if (viewType == 1) R.layout.weather_item else R.layout.title_weather_item,
                    parent, false), viewType)

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int) =
            if (position == 0) 0 else 1

    inner class ForecastViewHolder(view: View, private val viewType: Int) : RecyclerView.ViewHolder(view) {

        fun bind(weather: Weather) {
            val maxTemp = Math.floor(weather.maxTemp).toInt()
            val minTemp = Math.floor(weather.minTemp).toInt()
            val date = DateFormat.format("E, MMM dd E", weather.time)
            val description = itemView.context.getString(weather.description)
            if (viewType == 0) {
                val binding = DataBindingUtil.bind(itemView) as TitleWeatherItemBinding
                binding.maxTempText.text = "$maxTemp\u00b0"
                binding.minTempText.text = "$minTemp\u00b0"
                binding.dateText.text = date
                binding.weatherDescription.text = description
                binding.weatherIcon.setImageResource(weather.iconRes)
            } else {
                val binding = DataBindingUtil.bind(itemView) as WeatherItemBinding
                binding.maxTempText.text = "$maxTemp\u00b0"
                binding.minTempText.text = "$minTemp\u00b0"
                binding.dateText.text = date
                binding.weatherDescription.text = description
                binding.weatherIcon.setImageResource(weather.iconRes)
            }
        }
    }
}