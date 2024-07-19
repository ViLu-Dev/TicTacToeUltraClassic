package com.pmdm.tictactoeultraclassic.navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pmdm.tictactoeultraclassic.screens.DificultadScreen
import com.pmdm.tictactoeultraclassic.screens.MenuScreen
import com.pmdm.tictactoeultraclassic.screens.SeleccionFichaDificilScreen
import com.pmdm.tictactoeultraclassic.screens.SeleccionFichaFacilScreen
import com.pmdm.tictactoeultraclassic.screens.SeleccionFichaMedioScreen
import com.pmdm.tictactoeultraclassic.screens.StartScreen
import com.pmdm.tictactoeultraclassic.screens.TablaPuntuacionesScreen
import com.pmdm.tictactoeultraclassic.screens.TicTacToeScreenDificil
import com.pmdm.tictactoeultraclassic.screens.TicTacToeScreenFacil
import com.pmdm.tictactoeultraclassic.screens.TicTacToeScreenMedio
import com.pmdm.tictactoeultraclassic.screens.UpdateScoreScreen
import com.pmdm.tictactoeultraclassic.viewmodel.UsuarioViewModel

@Composable
fun SetupNavigation(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    onFichaSeleccionada: (String) -> Unit
) {

    val ultimoUsuarioGuardado by usuarioViewModel.ultimoUsuarioGuardado.observeAsState()
    NavHost(navController = navController, startDestination = "start") {
        composable("start") {
            StartScreen(usuarioViewModel = usuarioViewModel) { usuario ->
                navController.navigate("menu")
            }
        }
        composable("menu") {
            MenuScreen(navController = navController)
        }
        composable("tablaPuntuaciones", arguments = listOf(
        )) { backStackEntry ->
            TablaPuntuacionesScreen(navController, usuarioViewModel)
        }
        composable("dificultad") {
            DificultadScreen(navController)
        }
        composable("juegoFacil/{fichaJugador}") {backStackEntry ->
            val fichaJugador = backStackEntry.arguments?.getString("fichaJugador") ?: "X"
            ultimoUsuarioGuardado?.let { usuario -> TicTacToeScreenFacil(navController = navController, nombre = usuario.nombre, fichaJugador = fichaJugador)}
        }
        composable("juegoMedio/{fichaJugador}") {backStackEntry ->
            val fichaJugador = backStackEntry.arguments?.getString("fichaJugador") ?: "X"
            ultimoUsuarioGuardado?.let { usuario -> TicTacToeScreenMedio(navController = navController, nombre = usuario.nombre, fichaJugador = fichaJugador) }
        }
        composable("juegoDificil/{fichaJugador}") {backStackEntry ->
            val fichaJugador = backStackEntry.arguments?.getString("fichaJugador") ?: "X"
            ultimoUsuarioGuardado?.let { usuario -> TicTacToeScreenDificil(navController = navController, nombre = usuario.nombre, fichaJugador = fichaJugador)}
        }
        composable("seleccionFichaFacil") {
            SeleccionFichaFacilScreen(
                navController = navController,
                onFichaSeleccionada = onFichaSeleccionada
            )
        }
        composable("seleccionFichaMedio") {
            SeleccionFichaMedioScreen(
                navController = navController,
                onFichaSeleccionada = onFichaSeleccionada
            )
        }
        composable("seleccionFichaDificil") {
            SeleccionFichaDificilScreen(
                navController = navController,
                onFichaSeleccionada = onFichaSeleccionada
            )
        }
        composable("updateScore/{nombre}/{victorias}/{derrotas}/{resultado}", arguments = listOf(
            navArgument("nombre") { type = NavType.StringType },
            navArgument("victorias") { type = NavType.IntType },
            navArgument("derrotas") { type = NavType.IntType },
            navArgument("resultado") { type = NavType.StringType }
        )) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val victorias = backStackEntry.arguments?.getInt("victorias") ?: 0
            val derrotas = backStackEntry.arguments?.getInt("derrotas") ?: 0
            UpdateScoreScreen(
                navController = navController,
                usuarioViewModel = usuarioViewModel,
                nombre = nombre,
                victorias = victorias,
                derrotas = derrotas
            )
        }
    }
}