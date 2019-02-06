package com.simecsystem.currencyconverter.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.simecsystem.currencyconverter.data.db.entity.Rate

@Database(
    entities = [Rate::class],
    version = 1
)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun getCurrencyDao(): CurrencyDao

    companion object {
        @Volatile private var instance: CurrencyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyDatabase::class.java,
                    "CurrencyContainer.db"
                ).build()
    }
}