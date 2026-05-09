package com.example.safemindsmobile.data.remote

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @POST("signup")
    suspend fun signup(@Body request: SignupRequest): AuthResponse

    @POST("login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("ingest")
    suspend fun ingest(@Body request: SensorDataRequest): IngestResponse


    @GET("csi/latest")
    suspend fun getLatestCsi(@Query("user_id") userId: String): LatestCsiResponse

    @GET("csi/history")
    suspend fun getCsiHistory(@Query("user_id") userId: String): CsiHistoryResponse
}