package com.bangkit.capstone_project.viewmodel.preference


import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope

import com.affan.storyapp.preferences.LoginPreference
import com.bangkit.capstone_project.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PreferenceViewModel(private val pref: LoginPreference) : ViewModel() {
    fun getLoginSession(): Flow<UserModel?> {
        return pref.getLoginSession()
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            pref.saveSession(user)
        }
    }

    fun deleteSession() {
        viewModelScope.launch {
            pref.deleteSession()
        }
    }
}