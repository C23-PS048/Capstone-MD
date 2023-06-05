package com.bangkit.capstone_project.database

import android.app.Application
import com.bangkit.capstone_project.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class DatabaseRepository(application: Application) {
    private val mTaskDao: TaskDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = TaskDatabase.getDatabase(application)
        mTaskDao = db.noteDao()
    }
    suspend fun getAllTasks(): List<Task> = withContext(Dispatchers.IO) {
        mTaskDao.getAllTasks()
    }

    suspend fun insert(task: Task) = withContext(Dispatchers.IO) {
        mTaskDao.insert(task)
    }

    suspend fun delete(task: Task) = withContext(Dispatchers.IO) {
        mTaskDao.delete(task)
    }

    suspend fun update(task: Task) = withContext(Dispatchers.IO) {
        mTaskDao.update(task)
    }

    suspend fun getTaskById(taskId: Int) = withContext(Dispatchers.IO) {
        mTaskDao.getTaskById(taskId)
    }

}