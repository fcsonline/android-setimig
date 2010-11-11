package com.fcsonline.android.core;

import java.util.Vector;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fcsonline.android.ImageProvider;
import com.fcsonline.android.R;

public class Controller {

	Player player;
	Dealer dealer;
	
	LinearLayout layout;
	
	ImageProvider imageProvider;
	
	TextView textViewMoney;
	TextView textViewSum;
	TextView textViewBet;
	TextView textViewUserName;
	
	Button buttonUp;
	Button buttonDown;
	Button buttonEspecial;
	
	AssetManager assetsManager;
	SharedPreferences preferences;
	
	OnClickListener listenerLayout;

	Vector<LinearLayout> gamesLayouts;
	
	int level;
	
	public Controller() {
		super();
	}

	public void init () {
		
		layout.setOnClickListener(listenerLayout);
		
		player.setPlayerName(preferences.getString("usernamePref", "User"));
		textViewUserName.setText(player.getName());
		reset();
		
		try {
			next();
			paint();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Some card please!
	}
	
	public void paint() throws Exception {
		
		for (int i = 0; i < player.getGamesCount(); i++){
			LinearLayout gameLayout =  gamesLayouts.get(i);
			Game game = player.getGame(i);
			
			gameLayout.removeAllViews();
			
			for (int j = 0; j < game.getSize(); j++) {
				Card card = game.getCardAt(j);
				
				Bitmap bm = imageProvider.getImage(card.getId());
				ImageView imageView = new ImageView(layout.getContext());
				imageView.setImageBitmap(bm);
				imageView.setMaxHeight(25*3);
				imageView.setMaxWidth(19*3);
				gameLayout.addView(imageView);
			}
			
			if (player.getCurrent() != null && player.getCurrent().equals(game)) {
				ImageView imageView = new ImageView(layout.getContext());
				imageView.setImageResource(R.drawable.indicator_code_lock_drag_direction_red_up);
				gameLayout.addView(imageView);
			}

		}
		
	}
	
	public void reset () {
		
		player.init();
		
		dealer.reset();
		layout.removeAllViews();
		
		gamesLayouts = new Vector<LinearLayout>();

		// Start game layout
		LinearLayout gameLayout = new LinearLayout(layout.getContext());
		gameLayout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(gameLayout);
		layout.setOnClickListener(listenerLayout);
		gamesLayouts.add(gameLayout);
		
	}
	
	public void next() throws Exception {
		
		if (player.getStatus() == Player.NEND) {
			reset();
		}
		
		Card card = dealer.next();
		
		player.play(card);
		
		Game game = player.getCurrent();
		
		textViewMoney.setText("$" + player.getMoney());
		textViewSum.setText("" + game.getSum());
		textViewBet.setText("$" + game.getBet());
		buttonUp.setEnabled(game.getBet() < 25);
		buttonDown.setEnabled(game.getBet() > 1);
		
		if (game.isLost()) {
			stand();
		} else {
			
			buttonEspecial.setEnabled(false);
			if (game.isChangeCard()) {
				buttonEspecial.setText("Change");
				buttonEspecial.setEnabled(true);
			} else if (game.isNoPlay()) {
				buttonEspecial.setText("No play");
				buttonEspecial.setEnabled(true);
			} else if (game.isDead()) {
				buttonEspecial.setText("Dead");
				buttonEspecial.setEnabled(true);
			}  else if (game.isSplitable()) {
				buttonEspecial.setText("Split");
				buttonEspecial.setEnabled(true);
			} 
		}
		
	}
	
	public void stand() throws Exception {
		player.stand();
		
		if (player.getStatus() == Player.NEND) {
			
			boolean allLost = true;
			
			for (int i = 0; i < player.getGamesCount(); i++) {
				Game game = player.getGame(i);
				allLost &= game.isLost();
			}
			
			Game dgame = null;
			
			if (!allLost) {
				dgame = playDealer();
			}
			
			checkBets(dgame);
			
			if (player.getMoney() <= 0) {
				Toast toast = Toast.makeText(layout.getContext(), "Ohhhhhhhhh Bye Bye!", Toast.LENGTH_SHORT);
				toast.show();
			} else if (allLost) {
				Toast toast = Toast.makeText(layout.getContext(), "CPU Wins!", Toast.LENGTH_SHORT);
				toast.show();
			}
			
			checkRecords();
		} else {
			Game game = player.getCurrent();
			
			if (game != null){
				buttonUp.setEnabled(game.getBet() < 25);
				buttonDown.setEnabled(game.getBet() > 1); 
			}
		}
	}
	

	private void checkRecords() {
		// TODO Auto-generated method stub
		
	}

	private void checkBets(Game dgame) {

		int lastmoney = 0;

		for (int i = 0; i < player.getGamesCount(); i++) {
			
			Game game = player.getGame(i);
			int partial = 0;
			
			if (dgame == null) {
				partial -= game.getBet();
			} else if (dgame.isLost()) {

				if (game.getStatus() == Game.STAND) {

					if (game.getSum() != 75)
						partial += game.getBet();
					else if (game.isDeadEnd())
						partial += (3 * game.getBet());
					else
						partial += (2 * game.getBet());

				}

			} else {

				if (game.getSum() > dgame.getSum()) {
					if (game.getSum() != 75)
						partial += game.getBet();
					else if (game.isDeadEnd())
						partial += (3 * game.getBet());
					else
						partial += (2 * game.getBet());
				} else { 
					partial -= game.getBet();
				}
				
			}
			
			lastmoney += partial;
			/*
			LinearLayout gameLayout =  gamesLayouts.get(i);
			TextView labelScore = new TextView(layout.getContext());
			labelScore.setText("asd" + partial);
			labelScore.setTextColor(0x0000000F);
			gameLayout.addView(labelScore);
			*/
		}
		
		player.setMoney(player.getMoney() + lastmoney);
		textViewMoney.setText("$" + player.getMoney());
	}

	public void up() {
		Game game = player.getCurrent();
		game.setBet(Math.min(game.getBet() + 5, 25));
		textViewBet.setText("$" + game.getBet());
		
		buttonUp.setEnabled(game.getBet() < 25);
		buttonDown.setEnabled(game.getBet() > 1); 
	}

	public void down() {
		Game game = player.getCurrent();
		game.setBet(Math.max(game.getBet() - 1, 1));
		textViewBet.setText("$" + game.getBet());
		
		buttonUp.setEnabled(game.getBet() < 25);
		buttonDown.setEnabled(game.getBet() > 1); 
	}
	
	public void especial() throws Exception {
		Game game = player.getCurrent();
		
		if (game.isChangeCard()) { 
			game.popLastCard();
			next();
		} else if (game.isNoPlay()) {
			game.setNoplay();
		} else if (game.isDead()) {
			game.setDead();
			next();
		} else if (game.isSplitable()) {
			
			Card popedCard = game.popLastCard();
			Game splitedGame = new Game();
			splitedGame.addCard(popedCard);
			splitedGame.setBet(game.getBet());
			player.addGame(splitedGame);
			
			Toast toast = Toast.makeText(layout.getContext(), "Splited!", Toast.LENGTH_SHORT);
			toast.show();
			
			// Splitted game layout
			LinearLayout gameLayout = new LinearLayout(layout.getContext());
			gameLayout.setOrientation(LinearLayout.VERTICAL);
			layout.addView(gameLayout);
			layout.setOnClickListener(listenerLayout);
			gamesLayouts.add(gameLayout);
		}
	}
	
	private Game playDealer() throws Exception {

		Game dgame = new Game();
		int suma = 0;
		int ponderat = 0;

		// Calculate target value for dealer
		for (int i = 0; i < player.getGamesCount(); i++) {
			Game game = player.getGame(i);
			
			if (game.getStatus() == Game.STAND) {
				ponderat += (game.getBet() * game.getSum());
				suma += game.getBet();
			}
		}
		
		if (suma == 0) suma++;

		ponderat = ponderat / suma;

		switch (level) {
			case 0:
				
				//Strategy Level 0: When dealer reaches more than 5, it stands 
				
				while (dgame.getSum() <= 50 )
					dgame.addCard(dealer.next());
	
				break;
			case 1:
	
				// Strategy Level 1: Dealer plays always it doesn't reach the ponderate value of all games of the player
				
				while (dgame.getSum() < ponderat && dgame.getSum() <= 75)
					dgame.addCard(dealer.next());
	
				break;
			case 2:
	
				// Strategy Level 2: Like strategy level 1, but it knows the next card. ;) 
				
				Card card = dealer.next();
	
				while (dgame.getSum() < ponderat && (dgame.getSum() + card.getCardValue() <= 75)) {
					dgame.addCard(card);
					card = dealer.next();
				}
	
				break;
			default:
		}

		return dgame;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public LinearLayout getLayout() {
		return layout;
	}

	public void setImageProvider(ImageProvider imageProvider) {
		this.imageProvider = imageProvider;
	}
	
	public void setLayout(LinearLayout layout) {
		this.layout = layout;
	}

	public void setTextViewMoney(TextView textViewMoney) {
		this.textViewMoney = textViewMoney;
	}

	public void setTextViewSum(TextView textViewSum) {
		this.textViewSum = textViewSum;
	}

	public void setTextViewBet(TextView textViewBet) {
		this.textViewBet = textViewBet;
	}

	public void setTextViewUserName(TextView textViewUserName) {
		this.textViewUserName = textViewUserName;
	}

	public void setButtonUp(Button buttonUp) {
		this.buttonUp = buttonUp;
	}

	public void setButtonDown(Button buttonDown) {
		this.buttonDown = buttonDown;
	}

	public void setButtonEspecial(Button buttonEspecial) {
		this.buttonEspecial = buttonEspecial;
	}

	public void setAssetsManager(AssetManager assetsManager) {
		this.assetsManager = assetsManager;
	}

	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setOnClickListenerLayout(OnClickListener onClickListener) {
		listenerLayout = onClickListener;
	}
	
}
