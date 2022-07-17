package ru.konstantin.myfilm.utils

import android.graphics.Color

fun String.convertToInquiry(): String {
    return this.lowercase().replace(" ", "_").replace("-", "_")
}

fun Int.getStartElementOnPage(): Int {
    return this * MAX_NUMBER_FILM_RESULTS_ON_SCREEN
}

fun String.deleteBrackets(): String {
    return this.replace("(", "").replace(")", "")
}

fun String.convertToProgress(): Int {
    return (this.toFloat() * 10).toInt()
}

fun Int.convertToColor(): Int {
// Схема изменения цвета:
// Rating    R   G  B
//    0  -  201  35 35
//    25 -  174 136 87
//    50 -  167 174 87
//   100 -   42 166 40

    var red: Int = 0
    var green: Int = 0
    var blue: Int = 0
    if (this in 0..25) {
        red = 201 - 27 / 25
        green = 35 + this * 101 / 25
        blue = 35 + this * 52 / 25
    }
    else if (this in 25..50) {
        red = 174 - (this - 25) * 7 / 25
        green = 136 + (this - 25) * 38 / 25
        blue = 87
    }
    else if (this in 50..100) {
        red = 167 - (this - 50) * 125 / 50
        green = 174 - (this - 50) * 8 / 50
        blue = 87 - (this - 50) * 47 / 50
    }
    return Color.argb(ALPHA_NOT_OPAQUE, red, green, blue)
}