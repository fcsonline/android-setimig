package com.fcsonline.android;

import com.fcsonline.android.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class PreferencesScreen extends PreferenceActivity {
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        boolean soundEffectsPref 	= prefs.getBoolean("soundEffectsPref", true);
        boolean rememberBetPref 	= prefs.getBoolean("rememberBetPref", true);
        boolean stand75Pref 		= prefs.getBoolean("stand75Pref", true);
        boolean dealerGamePref 		= prefs.getBoolean("dealerGamePref", true);
        
        String usernamePref 		= prefs.getString("usernamePref", "User");
        String timeoutPref 			= prefs.getString("timeoutPref", "600");

        System.out.println("soundEffectsPref: " + soundEffectsPref);
        System.out.println("rememberBetPref: " 	+ rememberBetPref);
        System.out.println("stand75Pref: " 		+ stand75Pref);
        System.out.println("dealerGamePref: " 	+ dealerGamePref);
        System.out.println("usernamePref: " 	+ usernamePref);
        System.out.println("timeoutPref: " 		+ timeoutPref);
        
    }
    
}