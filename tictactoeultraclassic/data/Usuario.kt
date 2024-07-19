package com.pmdm.tictactoeultraclassic.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val victorias: Int = 0,
    val derrotas: Int = 0
)
