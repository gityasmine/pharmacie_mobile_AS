package com.example.pharmacie_mobile_as.data.repository

import android.util.Log
import com.example.pharmacie_mobile_as.data.local.MedicamentDao
import com.example.pharmacie_mobile_as.data.local.toDomain
import com.example.pharmacie_mobile_as.data.local.toEntity
import com.example.pharmacie_mobile_as.data.remote.ApiService
import com.example.pharmacie_mobile_as.data.remote.dto.toDomain
import com.example.pharmacie_mobile_as.data.remote.dto.toDto
import com.example.pharmacie_mobile_as.domain.model.Medicament
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MedicamentRepository(
    private val dao: MedicamentDao,
    private val apiService: ApiService
) {

    val allMedicaments: Flow<List<Medicament>> =
        dao.getAllMedicaments().map { entities ->
            entities.map { it.toDomain() }
        }

    fun getAllFormes(): Flow<List<String>> = dao.getAllFormes()

    fun searchMedicaments(query: String): Flow<List<Medicament>> =
        dao.searchMedicaments(query).map { it.map { entity -> entity.toDomain() } }

    fun filterByForme(forme: String): Flow<List<Medicament>> =
        dao.getMedicamentsByForme(forme).map { it.map { entity -> entity.toDomain() } }

    suspend fun getMedicamentById(id: Int): Medicament? {
        return dao.getMedicamentById(id)?.toDomain()
    }

    suspend fun refreshMedicaments(): Result<Unit> {
        return try {
            Log.d("Pharmacie", "Synchronisation avec API...")
            val result = apiService.getAllMedicaments()
            result.fold(
                onSuccess = { dtos ->
                    Log.d("Pharmacie", "API retourne ${dtos.size} medicaments")
                    val entities = dtos.map { it.toDomain().toEntity() }
                    dao.deleteAll()
                    dao.insertAll(entities)
                    Log.d("Pharmacie", "Sauvegarde en Room OK")
                    Result.success(Unit)
                },
                onFailure = {
                    Log.e("Pharmacie", "Erreur API: ${it.message}")
                    Result.failure(it)
                }
            )
        } catch (e: Exception) {
            Log.e("Pharmacie", "Exception: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun addMedicament(medicament: Medicament): Result<String> {
        return try {
            Log.d("Pharmacie", "Ajout medicament: ${medicament.denomination}")

            val tempId = -(System.currentTimeMillis() % Int.MAX_VALUE).toInt()
            val local = medicament.copy(id = tempId)
            dao.insertMedicament(local.toEntity())
            Log.d("Pharmacie", "Ajout local avec ID temporaire: $tempId")

            val result = apiService.addMedicament(medicament.toDto())
            result.fold(
                onSuccess = { response ->
                    Log.d("Pharmacie", "API status: ${response.status}, message: ${response.message}")
                    if (response.status == 1) {
                        dao.deleteMedicamentById(tempId)
                        refreshMedicaments()
                        Result.success(response.message)
                    } else {
                        Log.e("Pharmacie", "API a refuse l'ajout")
                        Result.failure(Exception(response.message))
                    }
                },
                onFailure = { e ->
                    Log.w("Pharmacie", "Mode hors ligne: ${e.message}")
                    Result.success("Ajouté localement")
                }
            )
        } catch (e: Exception) {
            Log.e("Pharmacie", "Erreur ajout: ${e.message}")
            Result.success("Ajouté localement")
        }
    }

    suspend fun updateMedicament(medicament: Medicament): Result<String> {
        return try {
            Log.d("Pharmacie", "Modification medicament ID: ${medicament.id}")
            dao.updateMedicament(medicament.toEntity())

            val result = apiService.updateMedicament(medicament.toDto())
            result.fold(
                onSuccess = { response ->
                    Log.d("Pharmacie", "Modification OK")
                    if (response.status == 1) {
                        Result.success(response.message)
                    } else {
                        Result.failure(Exception(response.message))
                    }
                },
                onFailure = { Result.success("Modifié localement") }
            )
        } catch (e: Exception) {
            Result.success("Modifié localement")
        }
    }

    suspend fun deleteMedicament(medicament: Medicament): Result<String> {
        return try {
            Log.d("Pharmacie", "Suppression medicament ID: ${medicament.id}")
            dao.deleteMedicament(medicament.toEntity())

            val result = apiService.deleteMedicament(medicament.id)
            result.fold(
                onSuccess = { response ->
                    Log.d("Pharmacie", "Suppression OK")
                    if (response.status == 1) {
                        Result.success(response.message)
                    } else {
                        Result.failure(Exception(response.message))
                    }
                },
                onFailure = { Result.success("Supprimé localement") }
            )
        } catch (e: Exception) {
            Result.success("Supprimé localement")
        }
    }
}