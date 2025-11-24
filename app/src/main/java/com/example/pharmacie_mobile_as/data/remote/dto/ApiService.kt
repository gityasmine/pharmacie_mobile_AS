package com.example.pharmacie_mobile_as.data.remote

import android.util.Log
import com.example.pharmacie_mobile_as.data.remote.dto.ApiResponse
import com.example.pharmacie_mobile_as.data.remote.dto.MedicamentDto
import com.example.pharmacie_mobile_as.util.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ApiService {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("Ktor", message)
                }
            }
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 30000
            socketTimeoutMillis = 30000
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }

    private val baseUrl = "${Constants.BASE_URL}/${Constants.PHARMACIE_ID}/medicaments"

    suspend fun getAllMedicaments(): Result<List<MedicamentDto>> {
        return try {
            val response = client.get(baseUrl)
            Result.success(response.body())
        } catch (e: Exception) {
            Log.e("ApiService", "Error getting medicaments", e)
            Result.failure(e)
        }
    }

    suspend fun searchMedicaments(query: String): Result<List<MedicamentDto>> {
        return try {
            val response = client.get(baseUrl) {
                parameter("search", query)
            }
            Result.success(response.body())
        } catch (e: Exception) {
            Log.e("ApiService", "Error searching medicaments", e)
            Result.failure(e)
        }
    }

    suspend fun getMedicamentById(id: Int): Result<MedicamentDto> {
        return try {
            val response = client.get("$baseUrl/$id")
            Result.success(response.body())
        } catch (e: Exception) {
            Log.e("ApiService", "Error getting medicament by id", e)
            Result.failure(e)
        }
    }

    suspend fun addMedicament(medicament: MedicamentDto): Result<ApiResponse> {
        return try {
            val response = client.post(baseUrl) {
                setBody(medicament)
            }
            Result.success(response.body())
        } catch (e: Exception) {
            Log.e("ApiService", "Error adding medicament", e)
            Result.failure(e)
        }
    }

    suspend fun updateMedicament(medicament: MedicamentDto): Result<ApiResponse> {
        return try {
            val response = client.put(baseUrl) {
                setBody(medicament)
            }
            Result.success(response.body())
        } catch (e: Exception) {
            Log.e("ApiService", "Error updating medicament", e)
            Result.failure(e)
        }
    }

    suspend fun deleteMedicament(id: Int): Result<ApiResponse> {
        return try {
            val response = client.delete("$baseUrl/$id")
            Result.success(response.body())
        } catch (e: Exception) {
            Log.e("ApiService", "Error deleting medicament", e)
            Result.failure(e)
        }
    }

    fun close() {
        client.close()
    }
}