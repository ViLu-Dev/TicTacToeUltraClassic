package com.pmdm.tictactoeultraclassic.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pmdm.tictactoeultraclassic.data.Usuario
import com.pmdm.tictactoeultraclassic.viewmodel.UsuarioViewModel

@Composable
fun StartScreen(usuarioViewModel: UsuarioViewModel, onUsuarioGuardado: (Usuario) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var usuarioGuardado by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(modifier = Modifier.align(Alignment.TopStart).padding(30.dp),
            text = "Bienvenid@ a tres en raya UltraClassic. " +
                    "Para empezar a jugar introduce tu nombre," +
                    " y pulsa guardar. \n\nLuego, navega por el" +
                    " navegador de opciones según lo que quieras hacer." +
                    "\n\n¡Espero que te guste!")
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Introduce tu nombre", color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (nombre.isNotBlank()) {
                    val usuario = Usuario(nombre = nombre)
                    usuarioViewModel.insert(usuario)
                    usuarioGuardado = true
                    onUsuarioGuardado(usuario)
                } else {
                    mensajeError = "El campo del nombre no puede estar vacío"
                }
            }) {
                Text("Guardar")
            }
            if (usuarioGuardado) {
                Text(text = "Usuario guardado!", color = Color.Green)
            }
            mensajeError?.let {
                Text(text = it, color = Color.Red)
            }
        }
    }
}

