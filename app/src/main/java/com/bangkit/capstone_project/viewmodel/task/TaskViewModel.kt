package com.bangkit.capstone_project.viewmodel.task

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.bangkit.capstone_project.database.DatabaseRepository
import com.bangkit.capstone_project.model.Task
import com.bangkit.capstone_project.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private var application: Application) : ViewModel() {
    private val mTaskRepository: DatabaseRepository = DatabaseRepository(application)
    private val _uiState: MutableStateFlow<UiState<List<Task>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Task>>>
        get() = _uiState

    private val _detailState: MutableStateFlow<UiState<Task>> = MutableStateFlow(UiState.Loading)
    val detailState: StateFlow<UiState<Task>>
        get() = _detailState

    fun getAllTasks() {
        viewModelScope.launch {
            try {
                val tasks = mTaskRepository.getAllTasks()
                _uiState.value = UiState.Success(tasks)
            } catch (exception: Exception) {
                _uiState.value = UiState.Error("Failed to retrieve tasks: ${exception.message}")
            }
        }
    }

    fun getTaskById(taskId: Int) {
        viewModelScope.launch {
            try {
                val tasks = mTaskRepository.getTaskById(taskId)
                _detailState.value = UiState.Success(tasks)
            } catch (exception: Exception) {
                _detailState.value = UiState.Error("Failed to retrieve tasks: ${exception.message}")
            }
        }
    }

    fun insert(task: Task) {
        viewModelScope.launch {
            mTaskRepository.insert(task)
        }
    }

    fun update(task: Task) {
        viewModelScope.launch {
            mTaskRepository.update(task)
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            mTaskRepository.delete(task)
        }
    }

}