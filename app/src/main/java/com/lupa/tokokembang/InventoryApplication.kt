package com.lupa.tokokembang

import android.app.Application
import com.lupa.tokokembang.database.AppDatabase


class InventoryApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}