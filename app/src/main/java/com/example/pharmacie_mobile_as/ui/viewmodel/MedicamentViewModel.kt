package com.example.pharmacie_mobile_as.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pharmacie_mobile_as.data.local.MedicamentDatabase
import com.example.pharmacie_mobile_as.data.remote.ApiService
import com.example.pharmacie_mobile_as.data.repository.MedicamentRepository
import com.example.pharmacie_mobile_as.domain.model.Medicament
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class MedicamentUiState(
    val medicaments: List<Medicament> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedForme: String? = null,
    val formes: List<String> = emptyList()
)

class MedicamentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MedicamentRepository
    private val _uiState = MutableStateFlow(MedicamentUiState())
    val uiState: StateFlow<MedicamentUiState> = _uiState.asStateFlow()

    init {
        val database = MedicamentDatabase.getDatabase(application)
        val apiService = ApiService()
        repository = MedicamentRepository(database.medicamentDao(), apiService)

        viewModelScope.launch {
            repository.allMedicaments.collect { medicaments ->
                _uiState.update { it.copy(medicaments = medicaments) }
            }
        }

        viewModelScope.launch {
            repository.getAllFormes().collect { formes ->
                _uiState.update { it.copy(formes = formes) }
            }
        }

        refreshMedicaments()
    }

    fun refreshMedicaments() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.refreshMedicaments()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun searchMedicaments(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        viewModelScope.launch {
            if (query.isBlank()) {
                repository.allMedicaments.collect { medicaments ->
                    _uiState.update { it.copy(medicaments = medicaments) }
                }
            } else {
                repository.searchMedicaments(query).collect { medicaments ->
                    _uiState.update { it.copy(medicaments = medicaments) }
                }
            }
        }
    }

    fun filterByForme(forme: String?) {
        _uiState.update { it.copy(selectedForme = forme) }
        viewModelScope.launch {
            if (forme == null) {
                repository.allMedicaments.collect { medicaments ->
                    _uiState.update { it.copy(medicaments = medicaments) }
                }
            } else {
                repository.filterByForme(forme).collect { medicaments ->
                    _uiState.update { it.copy(medicaments = medicaments) }
                }
            }
        }
    }

    suspend fun getMedicamentById(id: Int): Medicament? {
        return repository.getMedicamentById(id)
    }

    fun addMedicament(denomination: String, forme: String, quantite: Int, photo: String?) {
        viewModelScope.launch {
            val medicament = Medicament(
                denomination = denomination,
                formepharmaceutique = forme,
                qte = quantite,
                photo = photo
            )
            repository.addMedicament(medicament)
        }
    }

    fun updateMedicament(id: Int, denomination: String, forme: String, quantite: Int, photo: String?) {
        viewModelScope.launch {
            val medicament = Medicament(
                id = id,
                denomination = denomination,
                formepharmaceutique = forme,
                qte = quantite,
                photo = photo
            )
            repository.updateMedicament(medicament)
        }
    }

    fun deleteMedicament(medicament: Medicament) {
        viewModelScope.launch {
            repository.deleteMedicament(medicament)
        }
    }

    fun clearMessages() {}
}