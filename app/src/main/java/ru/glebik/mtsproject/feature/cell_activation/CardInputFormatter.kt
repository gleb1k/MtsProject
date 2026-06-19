package ru.glebik.mtsproject.feature.cell_activation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

object CellActivationInputLimits {
    const val CARD_NUMBER_MAX_LENGTH = 16
    const val EXPIRY_DATE_MAX_LENGTH = 4
    const val CVV_MAX_LENGTH = 3
}

fun filterCardNumberDigits(input: String): String {
    return input.filter { it.isDigit() }.take(CellActivationInputLimits.CARD_NUMBER_MAX_LENGTH)
}

fun filterExpiryDateDigits(input: String): String {
    return input.filter { it.isDigit() }.take(CellActivationInputLimits.EXPIRY_DATE_MAX_LENGTH)
}

fun filterCvvDigits(input: String): String {
    return input.filter { it.isDigit() }.take(CellActivationInputLimits.CVV_MAX_LENGTH)
}

object CardNumberVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text
        val formatted = digits.chunked(4).joinToString(" ")

        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 0) return 0
                    val spaces = (offset - 1) / 4
                    return (offset + spaces).coerceAtMost(formatted.length)
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 0) return 0
                    var digitCount = 0
                    for (index in 0 until offset.coerceAtMost(formatted.length)) {
                        if (formatted[index] != ' ') digitCount++
                    }
                    return digitCount.coerceAtMost(digits.length)
                }
            },
        )
    }
}

object ExpiryDateVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text
        val formatted = when {
            digits.length <= 2 -> digits
            else -> "${digits.take(2)}/${digits.drop(2)}"
        }

        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 0) return 0
                    if (offset <= 2) return offset.coerceAtMost(formatted.length)
                    return (offset + 1).coerceAtMost(formatted.length)
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 0) return 0
                    if (offset <= 2) return offset.coerceAtMost(digits.length)
                    return (offset - 1).coerceAtMost(digits.length)
                }
            },
        )
    }
}
