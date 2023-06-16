package com.bangkit.capstone_project.data.network.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class UserFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
object UserInjection {
    fun provideRepository(): UserRepository {
        return UserRepository.getInstance()
    }
}