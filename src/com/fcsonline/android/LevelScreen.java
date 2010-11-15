package com.fcsonline.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LevelScreen extends Activity {

	public static final String INTENT_LEVEL_KEY = "level";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level);

		Typeface font = Typeface.createFromAsset(getAssets(), "mailrays.ttf");
		
		TextView labelLevel = (TextView) this.findViewById(R.id.TextViewChooseLevel);
		labelLevel.setTypeface(font);
		
		Button easyButton = (Button) this.findViewById(R.id.EasyButton);
		easyButton.setTypeface(font);
		easyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.fcsonline.android.MainScreen");
				intent.putExtra(INTENT_LEVEL_KEY, 1);
				startActivity(intent);
			}
		});

		Button mediumButton = (Button) this.findViewById(R.id.MediumButton);
		mediumButton.setTypeface(font);
		mediumButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.fcsonline.android.MainScreen");
				intent.putExtra(INTENT_LEVEL_KEY, 2);
				startActivity(intent);
			}
		});

		Button difficultButton = (Button) this.findViewById(R.id.DifficultButton);
		difficultButton.setTypeface(font);
		difficultButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.fcsonline.android.MainScreen");
				intent.putExtra(INTENT_LEVEL_KEY, 3);
				startActivity(intent);
			}
		});

	}

}