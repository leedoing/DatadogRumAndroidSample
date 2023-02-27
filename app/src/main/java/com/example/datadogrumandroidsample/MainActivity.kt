package com.example.datadogrumandroidsample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.datadog.android.Datadog
import com.datadog.android.DatadogEventListener
import com.datadog.android.DatadogInterceptor
import com.datadog.android.rum.GlobalRum
import com.example.datadogrumandroidsample.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding

	private var mRetrofit: Retrofit? = null

	private var mRetrofitAPI: RetrofitAPI? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		initTransferButtonView()
		initTimingButtonView()
		Datadog.setUserInfo("1", "HyunjinLee", "hyunjin.lee@datadoghq.com")
		initRetrofitInit()
	}

	private fun initTransferButtonView() {
		binding.transfer.setOnClickListener {
			val testString = mRetrofitAPI?.test()
			testString?.enqueue(object : Callback<HyunjinResponse> {
				override fun onResponse(call: Call<HyunjinResponse>, response: Response<HyunjinResponse>) {
					Log.i("rum_test", "responseHeaders" + response.headers())
					Log.i("rum_test", "responseBody" + response.body())
				}

				override fun onFailure(call: Call<HyunjinResponse>, t: Throwable) {
					Log.i("rum_test", "onFail" + t)
				}
			})
		}
	}

	private fun initTimingButtonView() {
		binding.timing.setOnClickListener {
			GlobalRum.get().addTiming("initButton_load_timing")
			GlobalRum.addAttribute("paid", "2")
		}
	}

	fun initRetrofitInit() {
		val okHttpClient = OkHttpClient.Builder()
			.addInterceptor(DatadogInterceptor(rumResourceAttributesProvider = CustomRumResourceAttributesProvider()))
			.eventListenerFactory(DatadogEventListener.Factory())
			.build()

		mRetrofit =  Retrofit.Builder()
			.client(okHttpClient)
			.baseUrl("https://joongomarket.com/")
			.addConverterFactory(GsonConverterFactory.create())
			.build();

		mRetrofitAPI = mRetrofit?.create(RetrofitAPI::class.java)
	}

	override fun onResume() {
		super.onResume()
		GlobalRum.get().startView(this, "MainActivity", HashMap<String, String>().apply { put("onResume", "onResuemValue") })
	}

	override fun onPause() {
		super.onPause()
		GlobalRum.get().stopView(this, HashMap<String, String>().apply { put("onPause", "onPauseValue") })
	}

}