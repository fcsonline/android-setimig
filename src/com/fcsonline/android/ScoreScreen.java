package com.fcsonline.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScoreScreen extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		Typeface font = Typeface.createFromAsset(getAssets(), "mailrays.ttf");
		
		TableLayout layout = ((TableLayout)findViewById(R.id.score_table_layout));
		
		ArrayList<String> keys = new ArrayList<String>();
		
		for (int i = 1; i <=3; i++) {
			keys.add("score_l" + i + "_hs_val");
			keys.add("score_l" + i + "_mw_val");
			keys.add("score_l" + i + "_ml_val");
			keys.add("score_l" + i + "_sp_val");
		}
		
		for (int i = 0; i < layout.getChildCount(); i++){
			View view = layout.getChildAt(i);
			
			if (view instanceof TableRow) {
				TextView textKey = (TextView)((TableRow)view).getChildAt(0);
				TextView textValue = (TextView)((TableRow)view).getChildAt(1);
				
				textKey.setTypeface(font);
				textValue.setTypeface(font);
				
				String key = keys.get(i);
				
				if (i % 4 == 0){
					textValue.setText("$" + prefs.getInt(key, 0));
				} else if (i % 4 == 1){
					int value = prefs.getInt(key, 0);
					textValue.setText("+$" + prefs.getInt(key, 0));
					
					if (value > 0) { // If is not 0
						textValue.setTextColor(0xFF00DD00);
					}
					
				} else if (i % 4 == 2){
					int value = prefs.getInt(key, 0);
					textValue.setText("-$" + Math.abs(value));
					
					if (value < 0) { // If is not 0
						textValue.setTextColor(0xFFFF0000);	
					}
										
				} else if (i % 4 == 3){
					textValue.setText("" + prefs.getInt(key, 0));
				}
				
			}
			
		}
		
	}

}