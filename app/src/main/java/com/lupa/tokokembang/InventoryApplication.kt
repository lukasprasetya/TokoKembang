package com.lupa.tokokembang

import android.app.Application
import com.lupa.tokokembang.database.AppDatabase

/*
class InventoryApplication : Application() {

    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
}*/
class InventoryApplication : Application() {
    // Using by lazy so the database is only created when needed
    // rather than when the application starts
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}