package com.csosa.fntest.data.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import androidx.core.os.ConfigurationCompat
import com.csosa.fntest.data.R

class AppPreferences(val context: Context) {

    val currentLocale = ConfigurationCompat.getLocales(context.resources.configuration)[0]

    private var sharedPreferences
            = context.getSharedPreferences("FNTest.sharedPreferences", MODE_PRIVATE)

    var lastCallVehicles: Long?
        get() = getLong(Key.LAST_CALL_VEHICLES)
        set(value) = setLong(Key.LAST_CALL_VEHICLES, value)


    private fun getLong(key: Key): Long? {

        return if (sharedPreferences.contains(key.name)) {

            sharedPreferences.getLong(key.name, 0)
        } else {
            null
        }
    }

    private fun setLong(key: Key, value: Long?) {

        value?.let {

            sharedPreferences.edit { putLong(key.name, value) }
        } ?: remove(key)
    }


    private fun remove(key: Key) = sharedPreferences.edit { remove(key.name) }

    enum class Key {
        LAST_CALL_VEHICLES;
    }

}
