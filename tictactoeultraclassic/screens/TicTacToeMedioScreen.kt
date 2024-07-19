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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TicTacToeScreenMedio(navController: NavHostController, nombre: String, fichaJugador: String) {
    val gameState = remember { mutableStateOf(Array(3) { arrayOfNulls<String>(3) }) }
    val currentPlayer = remember { mutableStateOf(fichaJugador) }
    val machineFicha = if (fichaJugador == "X") "O" else "X"
    val buttonsDisabled = remember { mutableStateOf(false) }
    val isFirstMove = remember { mutableStateOf(true) }

    fun movimientoJugador(i: Int, j: Int) {
        if (gameState.value[i][j] == null && !buttonsDisabled.value) {
            gameState.value[i][j] = currentPlayer.value

            if (compruebaGanador(gameState.value, currentPlayer.value)) {
                navController.navigate("updateScore/$nombre/1/0/victoria")
                return
            } else if (tablaLlena(gameState.value)) {
                navController.navigate("updateScore/$nombre/0/0/empate")
                return
            }

            buttonsDisabled.value = true

                if (isFirstMove.value) {
                    isFirstMove.value = false
                    // Si el centro está libre y el jugador no ha puesto su ficha ahí, la máquina lo hará
                    if (gameState.value[1][1] == null) {
                        gameState.value[1][1] = machineFicha
                    } else {
                        makeBestMove(gameState.value, machineFicha)
                    }
                } else {
                    makeBestMove(gameState.value, machineFicha)
                }

                if (compruebaGanador(gameState.value, machineFicha)) {
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

fun makeBestMove(board: Array<Array<String?>>, ficha: String) {
    // Verifica si hay un movimiento ganador
    for (i in 0..2) {
        for (j in 0..2) {
            if (board[i][j] == null) {
                board[i][j] = ficha
                if (compruebaGanador(board, ficha)) return
                board[i][j] = null
            }
        }
    }

    // Realiza un movimiento estratégico
    // Priorizando el centro, luego los bordes y finalmente las esquinas
    if (board[1][1] == null) {
        board[1][1] = ficha
        return
    }

    val strategicMoves = listOf(
        Pair(0, 1), Pair(1, 0), Pair(1, 2), Pair(2, 1), // Bordes
        Pair(0, 0), Pair(0, 2), Pair(2, 0), Pair(2, 2)  // Esquinas
    )

    for ((x, y) in strategicMoves) {
        if (board[x][y] == null) {
            board[x][y] = ficha
            return
        }
    }

    // Si no hay un movimiento estratégico disponible, hace un movimiento aleatorio
    movimientoAleatorio(board, ficha)
}
