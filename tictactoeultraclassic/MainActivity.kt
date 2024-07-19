package com.pmdm.tictactoeultraclassic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.pmdm.tictactoeultraclassic.navegation.SetupNavigation
import com.pmdm.tictactoeultraclassic.viewmodel.UsuarioViewModel
import com.pmdm.tictactoeultraclassic.ui.theme.TICTACTOEUltraClassicTheme

class MainActivity : ComponentActivity() {
    private var fichaJugador: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TICTACTOEUltraClassicTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val usuarioViewModel: UsuarioViewModel = viewModel()
                    SetupNavigation(navController, usuarioViewModel, onFichaSeleccionada = {ficha -> fichaJugador = ficha})
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TICTACTOEUltraClassicTheme {
        Greeting("Android")
    }
}