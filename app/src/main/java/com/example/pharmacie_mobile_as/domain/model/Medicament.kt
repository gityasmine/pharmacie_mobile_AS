package com.example.pharmacie_mobile_as.domain.model

data class Medicament(
    val id: Int = 0,
    val denomination: String,
    val formepharmaceutique: String,
    val qte: Int,
    val photo: String? = null
) {
    fun getFullImageUrl(): String? {
        return photo?.let {
            "https://apipharmacie.pecatte.fr/images/$it"
        }
    }
}