package com.icefilms.info.prerelease;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;


@ReportsCrashes(formKey = "dExac0I2TWx2eUktS1hDSkZLSC1yUXc6MQ")
public class MyApp extends Application {
	
	@Override
	public void onCreate() {
		ACRA.init(this);
		super.onCreate();
	}
}
