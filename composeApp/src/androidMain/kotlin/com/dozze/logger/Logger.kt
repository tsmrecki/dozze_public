package com.dozze.logger

import android.util.Log

actual fun logV(tag: String, message: String) {
    Log.v(tag, message)
}