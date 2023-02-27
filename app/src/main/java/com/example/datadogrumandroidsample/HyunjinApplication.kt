package com.example.datadogrumandroidsample

import android.app.Application
import android.util.Log
import com.datadog.android.Datadog
import com.datadog.android.DatadogSite
import com.datadog.android.core.configuration.Configuration
import com.datadog.android.core.configuration.Credentials
import com.datadog.android.privacy.TrackingConsent
import com.datadog.android.rum.GlobalRum
import com.datadog.android.rum.RumMonitor


class HyunjinApplication : Application() {
	override fun onCreate() {

		super.onCreate()
		Log.i("rum_test", "oncreate")

		initRum()

		Log.i("rum_test", "init Complete")
	}

	private fun initRum() {
		val clientToken = "pub41b104a21ba4efc448cf8dbf0220a333"
		val applicationId = "461ef18c-7241-4864-ad50-3b094275d3a7"

		val environmentName = "stg"
		val appVariantName = "DatadogRumAndroidSample"

		val configuration = Configuration.Builder(
			logsEnabled = true,
			tracesEnabled = true,
			crashReportsEnabled = true,
			rumEnabled = true
		)
			.trackInteractions()
			.useSite(DatadogSite.US1)
			.build()

		val credentials = Credentials(clientToken, environmentName, appVariantName, applicationId)
		Datadog.initialize(this, credentials, configuration, TrackingConsent.GRANTED)
		// Session 샘플레이트
		GlobalRum.registerIfAbsent(RumMonitor.Builder().sampleRumSessions(100.0f).build())
	}
}
