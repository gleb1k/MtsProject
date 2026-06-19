package ru.glebik.mtsproject.core.network

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException

fun Throwable.toApiErrorMessage(
    json: Json,
    fallback: String = "Неизвестная ошибка",
): String {
    if (this is HttpException) {
        val errorBody = response()?.errorBody()?.string()
        return parseApiErrorBody(errorBody, json) ?: message ?: fallback
    }

    return message ?: fallback
}

private fun parseApiErrorBody(
    errorBody: String?,
    json: Json,
): String? {
    if (errorBody.isNullOrBlank()) return null

    return runCatching {
        val detail = json.parseToJsonElement(errorBody).jsonObject["detail"] ?: return null
        formatDetail(detail)
    }.getOrNull()
}

private fun formatDetail(detail: JsonElement): String? {
    return when (detail) {
        is JsonPrimitive -> detail.content.takeIf { it.isNotBlank() }
        is JsonArray -> detail
            .mapNotNull { item -> item.jsonObject["msg"]?.jsonPrimitive?.content?.takeIf { it.isNotBlank() } }
            .joinToString("\n")
            .takeIf { it.isNotBlank() }

        is JsonObject -> detail["msg"]?.jsonPrimitive?.content?.takeIf { it.isNotBlank() }
    }
}
