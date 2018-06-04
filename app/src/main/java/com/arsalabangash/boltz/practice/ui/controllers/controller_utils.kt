package com.arsalabangash.boltz.practice.ui.controllers


data class XPData(val baseXP: Int, val timeFactor: Int)

fun setXP(level: String): XPData {
    return when (level) {
        "Basic" -> XPData(250, 50)
        "Normal" -> XPData(500, 35)
        "Advanced" -> XPData(750, 25)
        else -> XPData(250, 50)
    }
}