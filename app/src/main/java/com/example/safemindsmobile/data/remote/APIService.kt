package com.example.safemindsmobile.data.remote

import com.example.safemindsmobile.data.remote.dto.AuthResponse
import com.example.safemindsmobile.data.remote.dto.CsiHistoryResponse
import com.example.safemindsmobile.data.remote.dto.HomeResponse
import com.example.safemindsmobile.data.remote.dto.IngestResponse
import com.example.safemindsmobile.data.remote.dto.LatestCsiResponse
import com.example.safemindsmobile.data.remote.dto.LoginRequest
import com.example.safemindsmobile.data.remote.dto.SignupRequest
import com.example.safemindsmobile.data.remote.model.SessionRequest
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
    suspend fun ingest(@Body request: SessionRequest): IngestResponse


    @GET("csi/latest")
    suspend fun getLatestCsi(@Query("user_id") userId: String): LatestCsiResponse

    @GET("csi/history")
    suspend fun getCsiHistory(@Query("user_id") userId: String): CsiHistoryResponse


    @GET("home/latest")
    suspend fun getHomeLatest(
        @Query("user_id") userId: String): HomeResponse
}