package com.example.composeqrtest.utilities

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("notExist activity")
}

fun Context.copyTextToClipboard(message: String, label: String = "copy") {
    val clipboardManager =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText(
        label,
        message
    )
    Toast.makeText(this, "$message is copied", Toast.LENGTH_LONG).show()
    clipboardManager.setPrimaryClip(clipData)
}