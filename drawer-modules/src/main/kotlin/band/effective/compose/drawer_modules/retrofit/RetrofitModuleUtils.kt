package band.effective.compose.drawer_modules.retrofit

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner

internal fun SharedPreferences.put(key: String, value: String) {
  edit().putString(key, value).apply()
}

internal fun SharedPreferences.put(key: String, value: Long) {
  edit().putLong(key, value).apply()
}

internal fun SharedPreferences.put(key: String, value: Int) {
  edit().putInt(key, value).apply()
}

@SuppressLint("ApplySharedPref") // We're deliberately blocking.
internal fun SharedPreferences.putBlocking(key: String, value: String) {
  edit().putString(key, value).commit()
}
