package com.pmdm.tictactoeultraclassic.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pmdm.tictactoeultraclassic.data.Usuario
import com.pmdm.tictactoeultraclassic.viewmodel.UsuarioViewModel

@Composable
fun TablaPuntuacionesScreen(navHostController: NavHostController, usuarioViewModel: UsuarioViewModel = viewModel()) {
    val usuarios by usuarioViewModel.allUsuarios.collectAsState(initial = emptyList())
    var showDialogDeleteAll by remember { mutableStateOf(false) }
    var showDialogDeleteSingle by remember { mutableStateOf(false) }
    var showTextFieldDeleteUser by remember { mutableStateOf(false) }
    var nombreUsuario by remember { mutableStateOf("") }
    var usuarioParaEliminar by remember { mutableStateOf<Usuario?>(null) }
    var mensajeNoExisteUsuario by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Back(navHostController, modifier = Modifier.clickable { navHostController.navigate("menu") })
        Text(text = "Tabla de Puntuaciones", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        usuarios.forEach { usuario ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = usuario.nombre, modifier = Modifier.weight(1f))
                Text(text = "Victorias: ${usuario.victorias}", modifier = Modifier.weight(1f))
                Text(text = "Derrotas: ${usuario.derrotas}", modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { showDialogDeleteAll = true },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Borrar todos los usuarios")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { showTextFieldDeleteUser = true },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Borrar usuario")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showTextFieldDeleteUser) {
            OutlinedTextField(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it },
                label = { Text("Nombre del usuario") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    usuarioViewModel.getUsuarioByName(nombreUsuario) { usuario ->
                        if (usuario != null) {
                            usuarioParaEliminar = usuario
                            showDialogDeleteSingle = true
                        } else {
                            mensajeNoExisteUsuario = "El usuario \"$nombreUsuario\" no existe"
                            showTextFieldDeleteUser = false
                            nombreUsuario = ""
                        }
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Buscar y Borrar")
            }
        }

        if (showDialogDeleteAll) {
            AlertDialog(
                onDismissRequest = { showDialogDeleteAll = false },
                title = { Text(text = "Confirmar eliminación") },
                text = { Text(text = "¿Está seguro de que desea borrar todos los usuarios?") },
                confirmButton = {
                    Button(
                        onClick = {
                            usuarioViewModel.deleteAll()
                            showDialogDeleteAll = false
                        }
                    ) {
                        Text("Sí")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialogDeleteAll = false }
                    ) {
                        Text("No")
                    }
                }
            )
        }

        if (showDialogDeleteSingle) {
            AlertDialog(
                onDismissRequest = { showDialogDeleteSingle = false },
                title = { Text(text = "Confirmar eliminación") },
                text = { Text(text = "¿Está seguro de que desea borrar el usuario ${usuarioParaEliminar?.nombre}?") },
                confirmButton = {
                    Button(
                        onClick = {
                            usuarioParaEliminar?.let { usuarioViewModel.delete(it) }
                            showDialogDeleteSingle = false
                            showTextFieldDeleteUser = false
                            nombreUsuario = ""
                        }
                    ) {
                        Text("Sí")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialogDeleteSingle = false }
                    ) {
                        Text("No")
                    }
                }
            )
        }
    }
}

@Composable
fun Back(navigationController: NavHostController, modifier: Modifier) {
    Icons.Default
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "Back",
        modifier = modifier.clickable { navigationController.navigate("menu") }
    )
}

@Composable
fun BackStart(navigationController: NavHostController, modifier: Modifier){
    Icons.Default
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "Back",
        modifier = modifier.clickable { navigationController.navigate("start") }
    )
}
