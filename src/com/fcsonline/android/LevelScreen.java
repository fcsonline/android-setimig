package com.fcsonline.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LevelScreen extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level);

		Typeface font = Typeface.createFromAsset(getAssets(), "mailrays.ttf");
		
		Button easyButton = (Button) this.findViewById(R.id.EasyButton);
		easyButton.setTypeface(font);
		easyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				startActivity(new Intent("com.fcsonline.android.MainScreen"));
			}
		});

		Button mediumButton = (Button) this.findViewById(R.id.MediumButton);
		mediumButton.setTypeface(font);
		mediumButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				startActivity(new Intent("com.fcsonline.android.PreferencesScreen"));
			}
		});

		Button difficultButton = (Button) this.findViewById(R.id.DifficultButton);
		difficultButton.setTypeface(font);
		difficultButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				startActivity(new Intent("com.fcsonline.android.PreferencesScreen"));
			}
		});

	}

}