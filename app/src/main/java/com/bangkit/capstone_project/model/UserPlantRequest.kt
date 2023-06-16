package com.bangkit.capstone_project.model
data class UserPlantRequest(
    val location: String,
    val disease: String,
    val startDate: String,
    val lastScheduledDate: String,
    val nextScheduledDate: String,
    val frequency: String,
    val plantId: String,
    val userId: String
)