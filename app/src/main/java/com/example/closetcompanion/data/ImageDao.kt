package com.example.closetcompanion.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: Images)

    @Query("DELETE FROM images")
    suspend fun deleteAllImages()

    @Query("SELECT * FROM images")
    suspend fun getAllImages(): List<Images>
}