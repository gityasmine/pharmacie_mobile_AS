package com.example.pharmacie_mobile_as.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pharmacie_mobile_as.domain.model.Medicament
import com.example.pharmacie_mobile_as.util.Constants

@Entity(tableName = Constants.MEDICAMENT_TABLE)
data class MedicamentEntity(
    @PrimaryKey(autoGenerate = false)  // L'API génère les IDs
    val id: Int = 0,
    val denomination: String,
    val formepharmaceutique: String,
    val qte: Int,
    val photo: String? = null,
    val lastUpdated: Long = System.currentTimeMillis()
)

fun MedicamentEntity.toDomain(): Medicament {
    return Medicament(
        id = id,
        denomination = denomination,
        formepharmaceutique = formepharmaceutique,
        qte = qte,
        photo = photo
    )
}

fun Medicament.toEntity(): MedicamentEntity {
    return MedicamentEntity(
        id = id,
        denomination = denomination,
        formepharmaceutique = formepharmaceutique,
        qte = qte,
        photo = photo
    )
}