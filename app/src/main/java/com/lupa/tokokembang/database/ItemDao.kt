package com.lupa.tokokembang.database

import android.content.ClipData.Item
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemDao {

    @Query("SELECT * from itemdb ORDER BY name ASC")
    fun getItems(): Flow<List<ItemDB>>

    @Query("SELECT * from itemdb WHERE id = :id")
    fun getItem(id: Int): Flow<ItemDB>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ItemDB)

    @Update
    suspend fun update(item: ItemDB)

    @Delete
    suspend fun delete(item: ItemDB)
}