package com.android.newsapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class PrefUtil(var context: Context) {
    var pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var count: Int
        get() = pref.getInt("count", 0)
        set(count) {
            pref.edit().putInt("count", count).apply()
        }

}