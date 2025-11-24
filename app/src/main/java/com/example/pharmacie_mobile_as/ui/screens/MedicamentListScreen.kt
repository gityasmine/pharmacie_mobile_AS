package com.example.pharmacie_mobile_as.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pharmacie_mobile_as.domain.model.Medicament
import com.example.pharmacie_mobile_as.ui.components.MedicamentCard
import com.example.pharmacie_mobile_as.ui.viewmodel.MedicamentUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicamentListScreen(
    uiState: MedicamentUiState,
    onSearch: (String) -> Unit,
    onAddClick: () -> Unit,
    onMedicamentClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Medicament) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Traitements") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Color(0xFF4CAF50)
            ) {
                Icon(Icons.Default.Add, "Ajouter", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    onSearch(it)
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                placeholder = { Text("Rechercher...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true
            )

            if (uiState.medicaments.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aucun médicament trouvé")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.medicaments, key = { it.id }) { medicament ->
                        MedicamentCard(
                            medicament = medicament,
                            onEdit = { onEditClick(medicament.id) },
                            onDelete = { onDeleteClick(medicament) },
                            onClick = { onMedicamentClick(medicament.id) }
                        )
                    }
                }
            }
        }
    }
}