package com.example.pharmacie_mobile_as.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pharmacie_mobile_as.domain.model.Medicament

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMedicamentScreen(
    medicament: Medicament?,
    onBackClick: () -> Unit,
    onSaveClick: (String, String, Int, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var denomination by remember { mutableStateOf(medicament?.denomination ?: "") }
    var forme by remember { mutableStateOf(medicament?.formepharmaceutique ?: "") }
    var quantite by remember { mutableStateOf(medicament?.qte?.toString() ?: "") }

    val isEditMode = medicament != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Modifier" else "Ajouter") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Retour")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = denomination,
                onValueChange = { denomination = it },
                label = { Text("Dénomination") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = forme,
                onValueChange = { forme = it },
                label = { Text("Forme pharmaceutique") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ex: comprimé, gélule, sirop") },
                singleLine = true
            )

            OutlinedTextField(
                value = quantite,
                onValueChange = {
                    if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                        quantite = it
                    }
                },
                label = { Text("Quantité") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Annuler")
                }

                Button(
                    onClick = {
                        if (denomination.isNotBlank() && forme.isNotBlank() && quantite.isNotBlank()) {
                            val qte = quantite.toIntOrNull() ?: 0
                            onSaveClick(denomination, forme, qte, null)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Text(if (isEditMode) "Modifier" else "Ajouter")
                }
            }
        }
    }
}