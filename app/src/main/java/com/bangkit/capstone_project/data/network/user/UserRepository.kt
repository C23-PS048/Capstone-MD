package com.bangkit.capstone_project.data.network.user

import android.util.Log
import com.bangkit.capstone_project.data.network.config.ApiConfig
import retrofit2.HttpException

class UserRepository {



    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(): UserRepository =
            instance ?: synchronized(this) {
                UserRepository().apply {
                    instance = this
                }
            }
    }
}