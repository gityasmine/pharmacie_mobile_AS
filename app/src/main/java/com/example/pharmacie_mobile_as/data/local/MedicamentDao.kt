package com.example.pharmacie_mobile_as.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicamentDao {

    @Query("SELECT * FROM medicaments ORDER BY denomination ASC")
    fun getAllMedicaments(): Flow<List<MedicamentEntity>>

    @Query("SELECT * FROM medicaments WHERE id = :id")
    suspend fun getMedicamentById(id: Int): MedicamentEntity?

    @Query("SELECT * FROM medicaments WHERE denomination LIKE '%' || :search || '%' ORDER BY denomination ASC")
    fun searchMedicaments(search: String): Flow<List<MedicamentEntity>>

    @Query("SELECT * FROM medicaments WHERE formepharmaceutique = :forme ORDER BY denomination ASC")
    fun getMedicamentsByForme(forme: String): Flow<List<MedicamentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicament(medicament: MedicamentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(medicaments: List<MedicamentEntity>)

    @Update
    suspend fun updateMedicament(medicament: MedicamentEntity)

    @Delete
    suspend fun deleteMedicament(medicament: MedicamentEntity)

    @Query("DELETE FROM medicaments WHERE id = :id")
    suspend fun deleteMedicamentById(id: Int)

    @Query("DELETE FROM medicaments")
    suspend fun deleteAll()

    @Query("SELECT DISTINCT formepharmaceutique FROM medicaments ORDER BY formepharmaceutique ASC")
    fun getAllFormes(): Flow<List<String>>
}