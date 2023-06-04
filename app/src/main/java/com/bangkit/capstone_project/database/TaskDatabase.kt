package com.bangkit.capstone_project.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.capstone_project.model.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun noteDao(): TaskDao
    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): TaskDatabase {
            if (INSTANCE == null) {
                synchronized(TaskDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TaskDatabase::class.java, "tasks")
                        .build()
                }
            }
            return INSTANCE as TaskDatabase
        }
    }
}