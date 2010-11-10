package com.fcsonline.android.core;

import java.util.Vector;


public class Player {

	public static final int gWAIT = 0;
	public static final int gSTART = 1;
	public static final int gBET = 2;
	public static final int gCARD = 3;
	public static final int gDESD = 4;
	public static final int gDEAD = 5;
	public static final int gCHANGE = 6;
	public static final int gNOPLAY = 7;
	public static final int gDEALER = 8;
	
	public static final int NSTART = 10;
	public static final int NSTAND = 11;
	public static final int NEND = 12;
	
	// TODO: Configurable
	public static final int StartMoney = 50;
	public static final int MaxBet = 25;

	private String playerName;

	private Vector<Game> games;

	private Game current;
	
	private int money;

	private int cursor;

	private int status;
    
	public Player() {

		money = StartMoney;
		
		init();
	}

	public void addGame(Game game){
		games.add(game);
	}
	
	public void init() {
		
		cursor = 0;
		status = gSTART;
		
		games = new Vector<Game>();
		
		// Create a new game
		Game game = new Game();
		game.setBet(10);
		games.add(game);
		current = game;
	}
	
	public void play(Card card) throws Exception {
		current.addCard(card);
	}
	
	public void stand () throws Exception {
		
		current.setStand();
		
		int location = games.indexOf(current) + 1;
		
		if (location >= games.size()) {
			// No hay siguiente. Fin!
			status = NEND;
			current = null;
		} else {
			current = games.get(location);
		}
		
	}

	public int getStatus (){
		return status;
	}
	
	public int getGamesCount(){
		return games.size();
	}

	public Game getGame(int location){
		return games.get(location);
	}

	public void nextGame() throws Exception {

		cursor++;

		if (cursor == games.size()) {
			// Final de joc

			if (!checkOption(4))
				status = gWAIT;

			cursor = 0;
		} else {

			Game current = games.get(cursor);
			
			current.setBet(Math.min(current.getBet(), SufMoney()));

			if (checkOption(1)) 
				current.setBet(Math.min(games.get(cursor-1).getBet(), SufMoney()));

			status = gBET;
			if (current.isDead()) 
				status = gDEAD;
		}

	}

	public boolean IsSufMoney() {
		return SufMoney() > 0;
	}

	public int SufMoney() {
		int sum = 0;
		for (int i = 0; i < games.size() && i < cursor; i++)
			sum += games.get(i).getBet();

		sum += (games.size() - cursor - 1);

		return money - sum;
	}

    public String getName(){
        return playerName;
    }
    
    // TODO: Completar
    public boolean checkOption(int i){
        return false;
    }
    
	
	public void setMoney(int m) {
		money = m;
	}

	public int getMoney() {
		return money;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Game getCurrent() {
		return current;
	}

	public void setCurrent(Game current) {
		this.current = current;
	}

	public int getCurrentCursor() {
		return games.indexOf(current);
	}
	
}

