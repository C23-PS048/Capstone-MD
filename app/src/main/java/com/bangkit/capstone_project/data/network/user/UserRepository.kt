package com.bangkit.capstone_project.data.network.user

class UserRepository {

    suspend fun getUSerDetail(id: String) {

    }

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