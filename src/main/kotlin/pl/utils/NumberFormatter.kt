package pl.utils

import java.text.NumberFormat
import java.util.*

class NumberFormatter {
    fun toPercentage(number: Double, fractionDigits: Int): String {
        val percentageFormatter = NumberFormat.getPercentInstance(Locale.US)
        percentageFormatter.maximumFractionDigits = fractionDigits
        return percentageFormatter.format(number)
    }

    fun formatLong(number: Long): String {
        return NumberFormat.getNumberInstance(Locale.US).format(number)
    }
}
