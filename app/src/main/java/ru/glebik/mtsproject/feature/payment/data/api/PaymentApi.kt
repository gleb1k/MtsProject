package ru.glebik.mtsproject.feature.payment.data.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.glebik.mtsproject.feature.payment.data.model.CreatePaymentMethodRequest
import ru.glebik.mtsproject.feature.payment.data.model.PaymentMethodResponse

interface PaymentApi {

    @POST("/api/v1/payment-methods/")
    suspend fun createPaymentMethod(
        @Body body: CreatePaymentMethodRequest,
    ): PaymentMethodResponse
}
