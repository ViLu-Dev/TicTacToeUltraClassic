package com.pmdm.tictactoeultraclassic.viewmodel

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pmdm.tictactoeultraclassic.data.AppDatabase
import com.pmdm.tictactoeultraclassic.data.Usuario
import com.pmdm.tictactoeultraclassic.data.UsuarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application): AndroidViewModel(application) {
    private val usuarioRepository: UsuarioRepository
    val allUsuarios: Flow<List<Usuario>>
    private val usuarioGuardado = MutableLiveData<Usuario?>()
    val ultimoUsuarioGuardado: LiveData<Usuario?> get() = usuarioGuardado

    init{
        val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()
        usuarioRepository = UsuarioRepository(usuarioDao)
        allUsuarios = usuarioRepository.allUsuarios
    }

    fun insert(usuario: Usuario) = viewModelScope.launch {
        usuarioRepository.insert(usuario)
        usuarioGuardado.postValue(usuario)
    }

    fun delete(usuario: Usuario) = viewModelScope.launch {
        usuarioRepository.delete(usuario)
    }

    fun deleteAll() = viewModelScope.launch {
        usuarioRepository.deleteAll()
    }

    fun getUsuarioByName(nombre: String, callback: (Usuario?) -> Unit) = viewModelScope.launch {
        callback(usuarioRepository.getUsuarioByName(nombre))
    }

    fun actualizaPuntuacion(nombre: String, victoria: Int, derrota: Int) = viewModelScope.launch {
        val usuario = usuarioRepository.getUsuarioByName(nombre)
        if (usuario != null) {
            val updatedUsuario = usuario.copy(
                victorias = usuario.victorias + victoria,
                derrotas = usuario.derrotas + derrota
            )
            usuarioRepository.update(updatedUsuario)
        } else {
            val newUsuario = Usuario(nombre = nombre, victorias = victoria, derrotas = derrota)
            usuarioRepository.insert(newUsuario)
        }
    }
}