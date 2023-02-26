package com.example.datadogrumandroidsample

data class HyunjinResponse(
	val short_name: String,
	val name: String,
	val icons: List<icon>
) {
	data class icon(
		val src: String?,
		val sizes: String?,
		val type: String,
	)
}

