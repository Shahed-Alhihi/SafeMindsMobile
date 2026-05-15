package com.example.safemindsmobile.data.repository

import com.example.safemindsmobile.data.mapper.toDashboardData
import com.example.safemindsmobile.data.mock.MockDashboardData
import com.example.safemindsmobile.data.mock.MockSleepData
import com.example.safemindsmobile.data.mock.MockVitalData
import com.example.safemindsmobile.data.model.RiskData
import com.example.safemindsmobile.data.remote.dto.LoginRequest
import com.example.safemindsmobile.data.remote.RetrofitClient
import com.example.safemindsmobile.data.remote.dto.SignupRequest
import com.example.safemindsmobile.data.mapper.toRiskData
import com.example.safemindsmobile.data.model.SleepData
import com.example.safemindsmobile.data.model.VitalsData
import com.example.safemindsmobile.data.model.DashboardData
import com.example.safemindsmobile.data.remote.model.SessionRequest
import com.example.safemindsmobile.data.mapper.toSleepData
import com.example.safemindsmobile.data.mapper.toVitalsData
import com.example.safemindsmobile.data.mock.MockRiskData

class SafeMindsRep {

    private val api=RetrofitClient.api
    suspend fun login(username: String, password: String)=
        api.login(LoginRequest(username, password))

    suspend fun signup(username: String,
                       fullName: String,
                       password: String,
                       ageRange: String,
                       gender: String,
                       height: Float,
                       weight: Float
    )=
        api.signup(
            SignupRequest(
                username =username,
                fullName = fullName,
                password = password,
                ageRange = ageRange,
                gender = gender,
                height=height,
                weight=weight
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

    suspend fun ingestData(request: SessionRequest)=
        api.ingest(request)

    suspend fun getCsiHistory(userId: String)=
        api.getCsiHistory(userId)


    suspend fun getDashboard(userId: String): DashboardData? {
        return try {
            val response = api.getHomeLatest(userId)
            if (response.success && response.data != null) {
                response.data.toDashboardData()
            } else null
        } catch (e: Exception) {
            null
        }


    }

    suspend fun getSleep(userId: String): SleepData?{
        return try{
            val response=api.getHomeLatest(userId)
            if (response.success && response.data!=null){
                response.data.sleep.toSleepData()
            }
            else null
        }
        catch (e:Exception){
            null
            }

        }


suspend fun getVitals(userId: String): VitalsData? {
    return try {
        val response = api.getHomeLatest(userId)
        if (response.success && response.data != null) {
            response.data.vitals.toVitalsData()
        } else null
    }
    catch (
        e: Exception
    ){
        null
    }

    }


    fun getDashboardData() = MockDashboardData
    fun getSleepData() = MockSleepData
    fun getVitalsData() = MockVitalData
    fun getRiskData()= MockRiskData
}


