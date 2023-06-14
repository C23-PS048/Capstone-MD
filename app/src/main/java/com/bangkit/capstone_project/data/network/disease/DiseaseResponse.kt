package com.bangkit.capstone_project.data.network.disease

import com.google.gson.annotations.SerializedName

data class DiseaseResponse(

	@field:SerializedName("diseaseResult")
	val diseaseResult: DiseaseResult? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DiseaseResult(

	@field:SerializedName("diseaseName")
	val diseaseName: String? = null,

	@field:SerializedName("cause")
	val cause: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("plantName")
	val plantName: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("care")
	val care: String? = null
)
data class AllDiseaseResponse(

	@field:SerializedName("diseaseList")
	val diseaseList: List<DiseaseListItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DiseaseListItem(

	@field:SerializedName("diseaseName")
	val diseaseName: String? = null,

	@field:SerializedName("cause")
	val cause: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("plantName")
	val plantName: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("care")
	val care: String? = null
)
