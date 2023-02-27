package com.example.datadogrumandroidsample

import android.util.Log
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
		val responseHeader = response!!.headers()!!
		val responseBody = response!!.body()!!

		val bodySource: BufferedSource = responseBody.source()
		bodySource.request(Long.MAX_VALUE)
		val bodyBuffer = bodySource.buffer()


		val charset = Charset.forName("UTF-8")

		val apiBody = bodyBuffer.clone().readString(charset)
		val apiResponse = HashMap<String, String>()

		val sb = StringBuilder()
		sb.append(responseHeader)
		sb.append(apiBody)
		apiResponse.put("apiResponse: ", sb.toString())
		Log.i("apiResponse", "" + apiResponse)
		return apiResponse
	}
}