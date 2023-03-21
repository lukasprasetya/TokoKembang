package com.lupa.tokokembang

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lupa.tokokembang.database.ItemDB
import com.lupa.tokokembang.database.ItemDao
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {

    val allItems: LiveData<List<ItemDB>> = itemDao.getItems().asLiveData()


    fun isStockAvailable(item: ItemDB): Boolean {
        return (item.itemQuantity > 0)
    }

    fun updateItem(
        itemId: Int,
        itemName: String,
        itemSupplier: String,
        itemDate: String,
        itemCount: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemSupplier, itemDate, itemCount)
        updateItem(updatedItem)
    }


    private fun updateItem(item: ItemDB) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }

    fun sellItem(item: ItemDB) {
        if (item.itemQuantity > 0) {
            val newItem = item.copy(itemQuantity = item.itemQuantity - 1)
            updateItem(newItem)
        }
    }

    fun addNewItem(itemName: String, itemSupplier: String, itemDate: String, itemCount: String) {
        val newItem = getNewItemEntry(itemName, itemSupplier, itemDate, itemCount)
        insertItem(newItem)
    }

    private fun insertItem(item: ItemDB) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    fun deleteItem(item: ItemDB) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    fun retrieveItem(id: Int): LiveData<ItemDB> {
        return itemDao.getItem(id).asLiveData()
    }

    fun isEntryValid(itemName: String, itemSupplier: String, itemDate: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemSupplier.isBlank() || itemDate.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }

    private fun getNewItemEntry(itemName: String, itemSupplier: String, itemDate: String, itemCount: String): ItemDB {
        return ItemDB(
            itemName = itemName,
            itemSupplier = itemSupplier,
            itemDate = itemDate,
            itemQuantity = itemCount.toInt()
        )
    }

    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemSupplier: String,
        itemDate: String,
        itemCount: String
    ): ItemDB {
        return ItemDB(
            id = itemId,
            itemName = itemName,
            itemSupplier = itemSupplier,
            itemDate = itemDate,
            itemQuantity = itemCount.toInt()
        )
    }
}

class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

