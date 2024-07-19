package com.pmdm.tictactoeultraclassic.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MenuScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF78909C)),
        contentAlignment = Alignment.Center
    ) {
        BackStart(navController, modifier = Modifier
            .align(Alignment.TopStart))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp),
                onClick = {
                navController.navigate("dificultad")
                }) {
                Text(text = "Jugar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp), onClick = {
                navController.navigate("tablaPuntuaciones")
            }) {
                Text(text = "Tabla de puntuaciones")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp), onClick = {
                    (navController.context as ComponentActivity).finish()
            }) {
                Text(text = "Salir")
            }
        }
    }
}