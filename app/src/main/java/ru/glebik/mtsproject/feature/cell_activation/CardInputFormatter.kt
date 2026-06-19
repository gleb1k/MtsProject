package ru.glebik.mtsproject.feature.cell_activation

object CellActivationInputLimits {
    const val CARD_NUMBER_MAX_LENGTH = 19
    const val EXPIRY_DATE_MAX_LENGTH = 5
    const val CVV_MAX_LENGTH = 3
}

fun formatCardNumber(input: String): String {
    val digits = input.filter { it.isDigit() }.take(16)
    return digits.chunked(4).joinToString(" ")
}

fun formatExpiryDate(input: String): String {
    val digits = input.filter { it.isDigit() }.take(4)

    return when {
        digits.length <= 2 -> digits
        else -> "${digits.take(2)}/${digits.drop(2)}"
    }
}

fun formatCvv(input: String): String {
    return input.filter { it.isDigit() }.take(CellActivationInputLimits.CVV_MAX_LENGTH)
}
