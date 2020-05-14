package com.gstormdev.diceroller.util

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.Date

@SuppressLint("SetTextI18n")
@BindingAdapter("android:text")
fun <T> setTextFromIterable(view: TextView, list: Iterable<T>?) {
    view.text = "(${list?.joinToString() ?: ""})"
}

@BindingAdapter("app:hideIfZero")
fun hideIfZero(view: View, number: Int) {
    view.visibility = if (number == 0) View.GONE else View.VISIBLE
}

@BindingAdapter("app:hideIfNull")
fun hideIfNull(view: View, obj: Any?) {
    view.visibility = if (obj == null) View.GONE else View.VISIBLE
}

@BindingAdapter("app:date")
fun formatDate(view: TextView, millis: Long) {
    view.text = Formatters.defaultDateFormatter(millis)
}

@BindingAdapter("app:date")
fun formatDate(view: TextView, date: Date) {
    view.text = Formatters.defaultDateFormatter(date)
}

@BindingAdapter("android:src")
fun setImageResource(view: ImageView, resource: Int) {
    view.setImageResource(resource)
}