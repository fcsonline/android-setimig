package com.fcsonline.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fcsonline.android.core.Controller;
import com.fcsonline.android.core.Dealer;
import com.fcsonline.android.core.Player;

public class MainScreen extends Activity {

	Controller controller;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		initializeFonts();
		initializeButtons();
		initializeController();
		
		Toast toast = Toast.makeText(getApplicationContext(), "Welmcome to SetIMig!", Toast.LENGTH_SHORT);
		toast.show();
		
	}

	private void initializeFonts() {
		
		Typeface font = Typeface.createFromAsset(getAssets(), "mailrays.ttf");
		
		((TextView) findViewById(R.id.TextViewMoney)).setTypeface(font);  
		((TextView) findViewById(R.id.TextViewBet)).setTypeface(font); 
		((TextView) findViewById(R.id.TextViewSum)).setTypeface(font); 
		((TextView) findViewById(R.id.TextViewUserName)).setTypeface(font); 
		
		((Button) findViewById(R.id.ButtonDown)).setTypeface(font);
		((Button) findViewById(R.id.ButtonUp)).setTypeface(font);
		((Button) findViewById(R.id.ButtonMore)).setTypeface(font);
		((Button) findViewById(R.id.ButtonStand)).setTypeface(font);
		((Button) findViewById(R.id.ButtonSpecial)).setTypeface(font);
		
	}

	private void initializeController() {
		Dealer dealer = new Dealer();
		Player player = new Player();
		
		// TODO: Constanize
		int spriteWidth = 19 * 3;
		int spriteHeight = 25 * 3;
		
		LinearLayout layout = (LinearLayout)findViewById(R.id.LinearLayout01);
		ImageProvider imageProvider = new ImageProvider(getResources(), R.raw.naipes, spriteWidth, spriteHeight);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		
		
		controller = new Controller();
		
		controller.setDealer(dealer);
		controller.setPlayer(player);
		controller.setLayout(layout);
		controller.setTextViewMoney((TextView)findViewById(R.id.TextViewMoney));
		controller.setTextViewSum((TextView)findViewById(R.id.TextViewSum));
		controller.setTextViewBet((TextView)findViewById(R.id.TextViewBet));
		controller.setTextViewUserName((TextView)findViewById(R.id.TextViewUserName));
		controller.setButtonEspecial((Button)findViewById(R.id.ButtonSpecial));
		
		controller.setAssetsManager(getAssets());
		controller.setPreferences(preferences);
		controller.setImageProvider(imageProvider);

		controller.setLevel(0); // TODO: Select from a screen
		
		controller.init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    
		AlertDialog.Builder builder;
		AlertDialog alert;
		
		// Handle item selection
	    switch (item.getItemId()) {
	    case R.id.new_game:
	    	Toast toast = Toast.makeText(getApplicationContext(), "Jejeje", Toast.LENGTH_SHORT);
			toast.show();
	        return true;
	    case R.id.help:

	    	builder = new AlertDialog.Builder(this);
	    	builder.setMessage(R.string.instructions);
	    	builder.setTitle("Help");
	    	builder.setCancelable(false)
	    	       .setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	           }
	    	       });
	    	alert = builder.create();
	    	alert.show();
	    	
	    	return true;
	    case R.id.about:

	    	builder = new AlertDialog.Builder(this);
	    	builder.setMessage(R.string.about);
	    	builder.setTitle("About");
	    	builder.setCancelable(false)
	    	       .setPositiveButton("Great!", new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	           }
	    	       });
	    	alert = builder.create();
	    	alert.show();
	    	
	    	return true;
	    case R.id.preferences:
	    	startActivity(new Intent("com.fcsonline.android.PreferencesScreen"));
	        return true;
	    case R.id.quit:
	    	
	    	builder = new AlertDialog.Builder(this);
	    	builder.setMessage("Are you sure you want to exit?")
	    	       .setCancelable(false)
	    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                MainScreen.this.finish();
	    	           }
	    	       })
	    	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                dialog.cancel();
	    	           }
	    	       });
	    	alert = builder.create();
	    	alert.show();
	    	
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	private void initializeButtons() {

		LinearLayout layout = (LinearLayout)findViewById(R.id.LinearLayout01);
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(getApplicationContext(), "Jejeje", Toast.LENGTH_SHORT);
				toast.show();
			}
		});
		
		Button buttonUp = (Button)findViewById(R.id.ButtonUp);
		
		buttonUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.up();
			}
		});
		
		Button buttonDown = (Button)findViewById(R.id.ButtonDown);
		
		buttonDown.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.down();
			}
		});
		
		Button buttonStand = (Button)findViewById(R.id.ButtonStand);
		
		buttonStand.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					controller.stand();
					controller.paint();
				} catch (Exception e) {
					Toast toast = Toast.makeText(getApplicationContext(), "Stand:" + e.getMessage(), Toast.LENGTH_SHORT);
					toast.show();
					e.printStackTrace();
				}
			}
		});
		
		Button buttonMore = (Button)findViewById(R.id.ButtonMore);
		
		buttonMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					controller.next();
					controller.paint();
				} catch (Exception e) {
					Toast toast = Toast.makeText(getApplicationContext(), "More:" + e.getMessage(), Toast.LENGTH_SHORT);
					toast.show();
					e.printStackTrace();
				}
			}
		});

		Button buttonSpecial = (Button)findViewById(R.id.ButtonSpecial);
		
		buttonSpecial.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					controller.especial();
					controller.paint();
				} catch (Exception e) {
					Toast toast = Toast.makeText(getApplicationContext(), "Special:" + e.getMessage(), Toast.LENGTH_SHORT);
					toast.show();
					e.printStackTrace();
				}
			}
		});
	}

}