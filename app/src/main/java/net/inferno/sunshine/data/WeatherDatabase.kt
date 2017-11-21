package net.inferno.sunshine.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import net.inferno.sunshine.utils.WeatherUtils

object WeatherDatabase {

    private lateinit var helper: SQLiteOpenHelper
    private lateinit var writeDatabase: SQLiteDatabase
    private lateinit var readDatabase: SQLiteDatabase

    fun init(context: Context) {
        helper = WeatherDatabaseHelper(context)
        writeDatabase = helper.writableDatabase
        readDatabase = helper.readableDatabase
    }

    fun insert(weather: Weather) {
        val content = ContentValues()
        content.put(WeatherContract.COLUMN_MAX_TEMP, weather.maxTemp)
        content.put(WeatherContract.COLUMN_MIN_TEMP, weather.minTemp)
        content.put(WeatherContract.COLUMN_PRESSURE, weather.pressure)
        content.put(WeatherContract.COLUMN_HUMIDITY, weather.humidity)
        content.put(WeatherContract.COLUMN_DESCRIPTION, weather.description)
        content.put(WeatherContract.COLUMN_WIND_SPEED, weather.windSpeed)
        content.put(WeatherContract.COLUMN_WIND_DEGREE, weather.windDegree)
        content.put(WeatherContract.COLUMN_TIME, weather.time)
        content.put(WeatherContract.COLUMN_ICON, weather.iconRes)

        writeDatabase.insert(WeatherContract.TABLE_NAME, null, content)
    }

    fun update() = helper.onUpgrade(writeDatabase, 0, 1)

    fun load(): List<Weather> {
        val list = mutableListOf<Weather>()
        val cursor = readDatabase.query(WeatherContract.TABLE_NAME,
                arrayOf(WeatherContract.COLUMN_MAX_TEMP,
                        WeatherContract.COLUMN_MIN_TEMP,
                        WeatherContract.COLUMN_DESCRIPTION,
                        WeatherContract.COLUMN_TIME,
                        WeatherContract.COLUMN_ICON),
                null, null, null, null,
                WeatherContract.COLUMN_TIME)
        while (cursor.moveToNext()) list += WeatherUtils.fromCursor(cursor, false)
        cursor.close()
        return list
    }
}