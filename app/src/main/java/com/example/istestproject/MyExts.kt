package com.example.istestproject

import android.content.Context
import android.widget.Toast

fun Context.showToast(msg: String, duration: Int) {

    Toast.makeText(this, msg, duration).show()
}