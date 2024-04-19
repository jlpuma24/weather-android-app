package co.bold.weather.views.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun String.cleanUrl(): String {
    return if (!this.contains("https"))
        "https:$this"
    else
        this
}

fun Double.toCelsius(): String {
    return "$this °C"
}

fun String.toCurrentDayFormat(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd H:mm", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("EEEE, hh:mm a", Locale.ENGLISH)

    val date = inputFormat.parse(this)
    return outputFormat.format(date ?: Date())
}