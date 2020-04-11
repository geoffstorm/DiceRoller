package com.gstormdev.diceroller.util

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

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
fun <T> hideIfNull(view: View, obj: Any?) {
    view.visibility = if (obj == null) View.GONE else View.VISIBLE
}