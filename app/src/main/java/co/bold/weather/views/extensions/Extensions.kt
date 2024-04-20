package co.bold.weather.views.extensions

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import co.bold.weather.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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

fun String.toCurrentDayNameFormat(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("EEEE", Locale.ENGLISH)

    val date = inputFormat.parse(this)
    return outputFormat.format(date ?: Date())
}

fun ImageView.setUrlImage(url: String, pbLoaderImage: ProgressBar) {
    val instance = this
    Glide
        .with(this.context)
        .load(url)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                pbLoaderImage.isVisible = false
                instance.setImageDrawable(
                    ContextCompat.getDrawable(
                        instance.context,
                        R.drawable.not_available_weather
                    )
                )
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                pbLoaderImage.isVisible = false
                return false
            }
        })
        .centerCrop()
        .into(this)
}