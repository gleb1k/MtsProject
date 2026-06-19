package ru.glebik.mtsproject.feature.cell_activation

internal const val PAYMENT_PROVIDER = "YooKassa"

internal fun formatMaskedPan(cardNumber: String): String? {
    val digits = cardNumber.filter { it.isDigit() }
    if (digits.length < 4) return null

    val lastFour = digits.takeLast(4)
    return "**** **** **** $lastFour"
}
