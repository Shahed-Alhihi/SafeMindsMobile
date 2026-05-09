package com.example.safemindsmobile.data.repository

import com.example.safemindsmobile.data.mock.MockDashboardData
import com.example.safemindsmobile.data.mock.MockSleepData
import com.example.safemindsmobile.data.mock.MockVitalData
import com.example.safemindsmobile.data.model.RiskData
import com.example.safemindsmobile.data.remote.LoginRequest
import com.example.safemindsmobile.data.remote.RetrofitClient
import com.example.safemindsmobile.data.remote.SensorDataRequest
import com.example.safemindsmobile.data.remote.SignupRequest
import com.example.safemindsmobile.data.mapper.toRiskData

class SafeMindsRep {

    private val api=RetrofitClient.api
    suspend fun login(username: String, password: String)=
        api.login(LoginRequest(username, password))

    suspend fun signup(username: String,
                       fullName: String,
                       password: String,
                       ageRange: String,
                       gender: String
    )=
        api.signup(
            SignupRequest(
                username =username,
                fullName = fullName,
                password = password,
                ageRange = ageRange,
                gender = gender
        )
    )

    suspend fun getLatestCsi(userId: String)=
        api.getLatestCsi(userId)

    suspend fun getRiskData(userId: String): RiskData ? {
        return try {
            val response = RetrofitClient.api.getLatestCsi(userId)
            if (response.success&& response.data!=null){
                response.data.toRiskData()
            }
            else null
        }
        catch (e:Exception){
            null
        }
    }

    suspend fun ingestData(request: SensorDataRequest)=
        api.ingest(request)

    suspend fun getCsiHistory(userId: String)=
        api.getCsiHistory(userId)
    fun getDashboardData()= MockDashboardData
    fun getSleepData()=MockSleepData
    fun getVitalsData()= MockVitalData
}


   // fun getRiskData()= MockRiskData