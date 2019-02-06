package com.simecsystem.currencyconverter.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Rate", indices = [Index(value = ["code"], unique = true)])
data class Rate(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val code: String,
    val currency: Double
)