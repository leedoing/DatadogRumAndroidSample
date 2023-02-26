package com.example.datadogrumandroidsample

import com.datadog.android.rum.RumResourceAttributesProvider
import okhttp3.Request
import okhttp3.Response
import okio.BufferedSource
import java.nio.charset.Charset

class CustomRumResourceAttributesProvider : RumResourceAttributesProvider {
	override fun onProvideAttributes(
		request: Request,
		response: Response?,
		throwable: Throwable?
	): Map<String, Any?> {
		val responseBody = response!!.body()!!
		val source: BufferedSource = responseBody.source()
		source.request(Long.MAX_VALUE)
		val buffer = source.buffer()
		val charset = Charset.forName("UTF-8")
		val apiBody = buffer.clone().readString(charset)

		val apiResponse = HashMap<String, String>()
		apiResponse.put("apiBody : ", apiBody)
		return apiResponse
	}
}