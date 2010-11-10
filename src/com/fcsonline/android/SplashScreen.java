package com.fcsonline.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SplashScreen extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		Typeface font = Typeface.createFromAsset(getAssets(), "mailrays.ttf");
		
		Button startButton = (Button) this.findViewById(R.id.StartButton);
		startButton.setTypeface(font);
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				startActivity(new Intent("com.fcsonline.android.MainScreen"));
			}
		});

		Button prefButton = (Button) this.findViewById(R.id.PreferencesButton);
		prefButton.setTypeface(font);
		prefButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				startActivity(new Intent("com.fcsonline.android.PreferencesScreen"));
			}
		});
		

	}

}