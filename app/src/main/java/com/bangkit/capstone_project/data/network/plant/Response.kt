package com.bangkit.capstone_project.data.network.plant

data class PlantResponse(
	val error: Boolean,
	val message: String,
	val plantList: List<PlantResult>
)
data class SinglePlantResponse(
	val error: Boolean,
	val message: String,
	val plantResult: PlantResult
)
