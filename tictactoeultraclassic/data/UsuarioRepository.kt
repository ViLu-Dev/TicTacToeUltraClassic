package com.pmdm.tictactoeultraclassic.data

import kotlinx.coroutines.flow.Flow

class UsuarioRepository(private val usuarioDao: UsuarioDao) {
    val allUsuarios: Flow<List<Usuario>> = usuarioDao.getAllUsuarios()

    suspend fun insert(usuario: Usuario){
        usuarioDao.insert(usuario)
    }
    suspend fun getUsuarioByName(nombre: String): Usuario?{
        return usuarioDao.getUsuarioByNombre(nombre)
    }

    suspend fun delete(usuario: Usuario) {
        usuarioDao.delete(usuario)
    }

    suspend fun update(usuario: Usuario){
        usuarioDao.update(usuario)
    }

    suspend fun deleteAll() {
        usuarioDao.deleteAll()
    }
}