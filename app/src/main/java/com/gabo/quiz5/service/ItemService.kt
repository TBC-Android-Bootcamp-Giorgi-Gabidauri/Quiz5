package com.gabo.quiz5.service

import com.gabo.quiz5.model.Item
import retrofit2.Response
import retrofit2.http.GET

interface ItemService {
    @GET(END_POINT)
    suspend fun getItems(): Response<Item>

    companion object {
        const val END_POINT = "/v3/b0c6d294-e43e-4552-8a96-88897daf0ab5"
    }
}