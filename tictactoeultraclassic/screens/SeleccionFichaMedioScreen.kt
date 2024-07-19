package com.pmdm.tictactoeultraclassic.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SeleccionFichaMedioScreen(navController: NavHostController, onFichaSeleccionada: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Back(navigationController = navController, modifier = Modifier.align(Alignment.TopStart))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Selecciona tu ficha")
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(
                    onClick = {
                        onFichaSeleccionada("X")
                        navController.navigate("juegoMedio/X")
                    },
                    colors = ButtonDefaults.buttonColors(Color.Green),
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .height(48.dp)
                ) {
                    Text(text = "X")
                }
                Button(
                    onClick = {
                        onFichaSeleccionada("O")
                        navController.navigate("juegoMedio/O")
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .height(48.dp)
                ) {
                    Text(text = "O")
                }
            }
        }
    }
}