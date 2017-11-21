package net.inferno.sunshine.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class WeatherDatabaseHelper(context: Context) : SQLiteOpenHelper(context, WeatherContract.DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) =
            db.execSQL("CREATE TABLE ${WeatherContract.TABLE_NAME} (" +
                    "${WeatherContract.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${WeatherContract.COLUMN_MAX_TEMP} REAL NOT NULL, " +
                    "${WeatherContract.COLUMN_MIN_TEMP} REAL NOT NULL, " +
                    "${WeatherContract.COLUMN_PRESSURE} REAL NOT NULL, " +
                    "${WeatherContract.COLUMN_WIND_SPEED} REAL NOT NULL, " +
                    "${WeatherContract.COLUMN_WIND_DEGREE} REAL NOT NULL, " +
                    "${WeatherContract.COLUMN_DESCRIPTION} INTEGER NOT NULL, " +
                    "${WeatherContract.COLUMN_TIME} INTEGER NOT NULL, " +
                    "${WeatherContract.COLUMN_ICON} INTEGER NOT NULL)")

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${WeatherContract.TABLE_NAME}")
        onCreate(db)
    }
}