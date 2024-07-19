package com.pmdm.tictactoeultraclassic.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pmdm.tictactoeultraclassic.viewmodel.UsuarioViewModel

@Composable
fun TicTacToeScreenFacil(navController: NavHostController, nombre: String, fichaJugador: String) {
    val gameState = remember { mutableStateOf(Array(3) { arrayOfNulls<String>(3) }) }
    val jugador = remember { mutableStateOf(fichaJugador) }
    val fichaMaquina = if (fichaJugador == "X") "O" else "X"
    val buttonsDisabled = remember { mutableStateOf(false) }
    val primerMovimiento = remember { mutableStateOf(true) }

    fun movimientoJugador(i: Int, j: Int) {
        if (gameState.value[i][j] == null && !buttonsDisabled.value) {
            gameState.value[i][j] = jugador.value

            if (compruebaGanador(gameState.value, jugador.value)) {
                navController.navigate("updateScore/$nombre/1/0/victoria")
                return
            } else if (tablaLlena(gameState.value)) {
                navController.navigate("updateScore/$nombre/0/0/empate")
                return
            }

            buttonsDisabled.value = true

                if (primerMovimiento.value) {
                    primerMovimiento.value = false
                    // Si el centro está libre y el jugador no ha puesto su ficha ahí, la máquina lo hará
                    if (gameState.value[1][1] == null) {
                        gameState.value[1][1] = fichaMaquina
                    } else {
                        movimientoAleatorio(gameState.value, fichaMaquina)
                    }
                } else {
                    movimientoAleatorio(gameState.value, fichaMaquina)
                }

                if (compruebaGanador(gameState.value, fichaMaquina)) {
                    navController.navigate("updateScore/$nombre/0/1/derrota")
                } else if (tablaLlena(gameState.value)) {
                    navController.navigate("updateScore/$nombre/0/0/empate")
                }

                buttonsDisabled.value = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            for (i in 0..2) {
                Row {
                    for (j in 0..2) {
                        Button(
                            onClick = { movimientoJugador(i, j) },
                            modifier = Modifier
                                .size(100.dp)
                                .padding(2.dp),
                            shape = RectangleShape,
                            enabled = !buttonsDisabled.value && gameState.value[i][j] == null
                        ) {
                            Text(text = gameState.value[i][j] ?: "")
                        }
                    }
                }
            }
        }
    }
}

fun compruebaGanador(board: Array<Array<String?>>, ficha: String): Boolean {
    for (i in 0..2) {
        if ((board[i][0] == ficha && board[i][1] == ficha && board[i][2] == ficha) ||
            (board[0][i] == ficha && board[1][i] == ficha && board[2][i] == ficha)) {
            return true
        }
    }
    if ((board[0][0] == ficha && board[1][1] == ficha && board[2][2] == ficha) ||
        (board[0][2] == ficha && board[1][1] == ficha && board[2][0] == ficha)) {
        return true
    }
    return false
}

fun tablaLlena(board: Array<Array<String?>>): Boolean {
    return board.all { row -> row.all { it != null } }
}

fun movimientoAleatorio(board: Array<Array<String?>>, ficha: String) {
    val emptyCells = mutableListOf<Pair<Int, Int>>()
    for (i in 0..2) {
        for (j in 0..2) {
            if (board[i][j] == null) {
                emptyCells.add(Pair(i, j))
            }
        }
    }
    if (emptyCells.isNotEmpty()) {
        val (x, y) = emptyCells.random()
        board[x][y] = ficha
    }
}

@Composable
fun UpdateScoreScreen(navController: NavHostController, usuarioViewModel: UsuarioViewModel, nombre: String, victorias: Int, derrotas: Int) {
    // Actualizamos la tabla de puntuaciones
    ActualizarTablaPuntuaciones(usuarioViewModel, nombre, victorias, derrotas)
    LaunchedEffect(Unit) {
        navController.navigate("tablaPuntuaciones") {
            popUpTo("menu") { inclusive = true }
        }
    }
}

@Composable
fun ActualizarTablaPuntuaciones(usuarioViewModel: UsuarioViewModel, nombre: String, victorias: Int, derrotas: Int) {
    LaunchedEffect(Unit) {
        usuarioViewModel.actualizaPuntuacion(nombre, victorias, derrotas)
    }
}


