package com.example.pharmacie_mobile_as.ui.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pharmacie_mobile_as.ui.screens.AddEditMedicamentScreen
import com.example.pharmacie_mobile_as.ui.screens.MedicamentDetailScreen
import com.example.pharmacie_mobile_as.ui.screens.MedicamentListScreen
import com.example.pharmacie_mobile_as.ui.viewmodel.MedicamentViewModel

sealed class Screen(val route: String) {
    object List : Screen("list")
    object Add : Screen("add")
    object Detail : Screen("detail/{medicamentId}") {
        fun createRoute(medicamentId: Int) = "detail/$medicamentId"
    }
}

@Composable
fun NavGraph(navController: NavHostController, viewModel: MedicamentViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = Screen.List.route) {
        composable(Screen.List.route) {
            MedicamentListScreen(
                uiState = uiState,
                onSearch = { query -> viewModel.searchMedicaments(query) },
                onAddClick = { navController.navigate(Screen.Add.route) },
                onMedicamentClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                onDeleteClick = { medicament -> viewModel.deleteMedicament(medicament) }
            )
        }

        composable(Screen.Add.route) {
            AddEditMedicamentScreen(
                medicament = null,
                onBackClick = { navController.navigateUp() },
                onSaveClick = { denomination, forme, quantite, photo ->
                    viewModel.addMedicament(denomination, forme, quantite, photo)
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("medicamentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val medicamentId = backStackEntry.arguments?.getInt("medicamentId") ?: 0
            var medicament by remember {
                mutableStateOf<com.example.pharmacie_mobile_as.domain.model.Medicament?>(null)
            }

            LaunchedEffect(medicamentId) {
                medicament = viewModel.getMedicamentById(medicamentId)
            }

            MedicamentDetailScreen(
                medicament = medicament,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}