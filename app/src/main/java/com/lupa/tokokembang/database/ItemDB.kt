package com.lupa.tokokembang.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity
data class ItemDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    @ColumnInfo("name")
    val itemName: String,
    @ColumnInfo("supplier")
    val itemSupplier: String,
    @ColumnInfo("date")
    val itemDate: String,
    @ColumnInfo("quantity")
    val itemQuantity: Int,
    /*@ColumnInfo("price")
    val itemPrice: Double*/
)

fun ItemDB.getFormattedDate():String =
    DateFormat.getDateInstance().format(itemDate)