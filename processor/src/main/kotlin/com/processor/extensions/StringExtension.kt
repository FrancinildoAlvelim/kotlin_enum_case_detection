package com.processor.extensions

import java.util.Locale

fun String.capitalized(): String {
    return this
        .lowercase(Locale.getDefault())
        .replaceFirstChar {
            if (it.isLowerCase()) {
                it.titlecase(Locale.getDefault())
            } else {
                it.toString()
            }
        }
}