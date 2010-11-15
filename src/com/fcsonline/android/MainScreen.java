package com.fcsonline.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

	protected static final long DOUBLE_CLICK_MILLIS = 500;

	Controller controller;
	
	long lastClick;
	int  countClick;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		initialize();
	}

	private void initialize() {
		initializeFonts();
		initializeButtons();
		initializeController();
	}
	
	private void initializeFonts() {
		
		Typeface font = Typeface.createFromAsset(getAssets(), "mailrays.ttf");
		
		((TextView) findViewById(R.id.TextViewMoney)).setTypeface(font);
		((TextView) findViewById(R.id.TextViewMoneyGame)).setTypeface(font);  
		((TextView) findViewById(R.id.TextViewBet)).setTypeface(font); 
		((TextView) findViewById(R.id.TextViewSum)).setTypeface(font); 
		((TextView) findViewById(R.id.TextViewUserName)).setTypeface(font); 
		
		((Button) findViewById(R.id.ButtonDown)).setTypeface(font);
		((Button) findViewById(R.id.ButtonUp)).setTypeface(font);
		((Button) findViewById(R.id.ButtonStand)).setTypeface(font);
		((Button) findViewById(R.id.ButtonSpecial)).setTypeface(font);
		
	}

	private void initializeController() {

		lastClick = 0;
		
		// Core objects
		Dealer dealer = new Dealer();
		Player player = new Player();
		
		// Sprite Cards
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inDensity = 1;
		opts.inTargetDensity = 0;
		opts.inScreenDensity = 1;
		opts.inScaled = false;

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.naipes, opts);
		int spriteWidth = bitmap.getWidth() / 13; // A, 2, ..., Q, K
		int spriteHeight = bitmap.getHeight() / 4; // h, c, d, s 
		
		LinearLayout layout = (LinearLayout)findViewById(R.id.LinearLayout01);
		ImageProvider imageProvider = new ImageProvider(bitmap, spriteWidth, spriteHeight);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		controller = new Controller();
		
		controller.setDealer(dealer);
		controller.setPlayer(player);
		controller.setLayout(layout);
		controller.setTextViewMoney((TextView)findViewById(R.id.TextViewMoney));
		controller.setTextViewMoneyGame((TextView)findViewById(R.id.TextViewMoneyGame));
		controller.setTextViewSum((TextView)findViewById(R.id.TextViewSum));
		controller.setTextViewBet((TextView)findViewById(R.id.TextViewBet));
		controller.setTextViewUserName((TextView)findViewById(R.id.TextViewUserName));
		
		
		
		controller.setButtonUp((Button)findViewById(R.id.ButtonUp));
		controller.setButtonDown((Button)findViewById(R.id.ButtonDown));
		controller.setButtonEspecial((Button)findViewById(R.id.ButtonSpecial));
		
		controller.setAssetsManager(getAssets());
		controller.setPreferences(preferences);
		controller.setImageProvider(imageProvider);

		int level = 1;
		
		Bundle extras = getIntent().getExtras(); 
		if(extras != null) {
			level = extras.getInt(LevelScreen.INTENT_LEVEL_KEY);
		} 
		
		controller.setLevel(level);
		
		controller.setOnClickListenerLayout(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					long newClick = System.currentTimeMillis();
					
					if ((newClick - lastClick) < DOUBLE_CLICK_MILLIS) {
						countClick++;
					} else {
						countClick = 1;
					}
					
					lastClick = newClick;
					
					// DoubleClick!
					if (countClick == 2) {
						controller.next();
						controller.paint();
						countClick = 1;
					}
					
				} catch (Exception e) {
					Toast toast = Toast.makeText(getApplicationContext(), "More:" + e.getMessage(), Toast.LENGTH_SHORT);
					toast.show();
					e.printStackTrace();
				}
			}
		});
		
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
	    	
	    	builder = new AlertDialog.Builder(this);
	    	builder.setMessage("Are you sure you want to end this hand?")
	    	       .setCancelable(false)
	    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	        	   initialize();
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
	    case R.id.scores:
	    	startActivity(new Intent("com.fcsonline.android.ScoreScreen"));
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