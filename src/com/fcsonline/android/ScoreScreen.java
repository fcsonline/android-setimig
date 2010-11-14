package com.fcsonline.android;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class ScoreScreen extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		((TextView)findViewById(R.id.score_l1_hs_val)).setText("$" + prefs.getInt("score_l1_hs_val", 0));
		((TextView)findViewById(R.id.score_l1_mw_val)).setText("$" + prefs.getInt("score_l1_mw_val", 0));
		((TextView)findViewById(R.id.score_l1_ml_val)).setText("$" + prefs.getInt("score_l1_ml_val", 0));
		((TextView)findViewById(R.id.score_l1_sp_val)).setText(""  + prefs.getInt("score_l1_sp_val", 0));

		((TextView)findViewById(R.id.score_l2_hs_val)).setText("$" + prefs.getInt("score_l2_hs_val", 0));
		((TextView)findViewById(R.id.score_l2_mw_val)).setText("$" + prefs.getInt("score_l2_mw_val", 0));
		((TextView)findViewById(R.id.score_l2_ml_val)).setText("$" + prefs.getInt("score_l2_ml_val", 0));
		((TextView)findViewById(R.id.score_l2_sp_val)).setText(""  + prefs.getInt("score_l2_sp_val", 0));

		((TextView)findViewById(R.id.score_l3_hs_val)).setText("$" + prefs.getInt("score_l3_hs_val", 0));
		((TextView)findViewById(R.id.score_l3_mw_val)).setText("$" + prefs.getInt("score_l3_mw_val", 0));
		((TextView)findViewById(R.id.score_l3_ml_val)).setText("$" + prefs.getInt("score_l3_ml_val", 0));
		((TextView)findViewById(R.id.score_l3_sp_val)).setText(""  + prefs.getInt("score_l3_sp_val", 0));
		
	}

}