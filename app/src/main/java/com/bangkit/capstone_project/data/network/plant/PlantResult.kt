package com.bangkit.capstone_project.data.network.plant

data class PlantResult(
	val image: List<String>,
	val scientificName: String,
	val wateringFrequency: String,
	val plantTips: String,
	val temperature: String,
	val description: String,
	val id: Int,
	val wateringTips: String,
	val plantName: String,
	val slug: String
)
