package com.bangkit.capstone_project.viewmodel.task

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaskVMFactory  constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: TaskVMFactory? = null
        @JvmStatic
        fun getInstance(application: Application): TaskVMFactory {
            if (INSTANCE == null) {
                synchronized(TaskVMFactory::class.java) {
                    INSTANCE = TaskVMFactory(application)
                }
            }
            return INSTANCE as TaskVMFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}