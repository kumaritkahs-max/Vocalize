package com.yourapp.vocalize.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey val id: String,
    val name: String,
    val colorHex: String,
    val iconResId: Int
)
