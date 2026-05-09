package com.example.safemindsmobile.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.safemindsmobile.data.local.dao.SessionDao
import com.example.safemindsmobile.data.local.entity.HourlyCheckEntity
import com.example.safemindsmobile.data.local.entity.SleepSummaryEntity
import com.example.safemindsmobile.data.local.entity.SyncStateEntity

@Database(
    entities = [SleepSummaryEntity::class, HourlyCheckEntity::class, SyncStateEntity::class],
    version = 1
)

abstract class AppDatabase: RoomDatabase() {
    abstract fun sessionDao(): SessionDao

    companion object{
        @Volatile private var INSTANCE: AppDatabase? =null
        fun getInstance(context: Context): AppDatabase{
            return INSTANCE?:synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,"safeminds_db").build().also {
                        INSTANCE = it
                }

            }
        }
    }

}