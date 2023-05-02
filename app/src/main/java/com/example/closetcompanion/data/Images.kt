package com.example.closetcompanion.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Images(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageData: ByteArray
)