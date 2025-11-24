package com.example.pharmacie_mobile_as.data.remote.dto

import com.example.pharmacie_mobile_as.domain.model.Medicament
import kotlinx.serialization.Serializable

@Serializable
data class MedicamentDto(
    val id: Int = 0,
    val denomination: String,
    val formepharmaceutique: String,
    val qte: Int,
    val photo: String? = null
)

fun MedicamentDto.toDomain(): Medicament {
    return Medicament(
        id = id,
        denomination = denomination,
        formepharmaceutique = formepharmaceutique,
        qte = qte,
        photo = photo
    )
}

fun Medicament.toDto(): MedicamentDto {
    return MedicamentDto(
        id = id,
        denomination = denomination,
        formepharmaceutique = formepharmaceutique,
        qte = qte,
        photo = photo
    )
}

@Serializable
data class ApiResponse(
    val status: Int,
    val message: String
)