package com.fcsonline.android.core;

import java.util.Stack;


public class Game {

	public static final int PLAYING = 0;
	public static final int STAND = 1;
	public static final int NOPLAY = 4;
	public static final int DEAD = 5;
	
	private Stack<Card> cards = new Stack<Card>();
	
	private int bet, sum, status;

	public Game() {

		bet = 0;
		sum = 0;
		status = 0;

	}

	public void addCard(Card card) throws Exception {

		if (cards.size() > 15)
			throw new Exception("Limit 15!");
		
		if (sum > 75)
			throw new Exception("Limit 7,5!");

		cards.push(card);
		
		sum += card.getCardValue();
		
	}

	public Card popLastCard() {
		
		Card card = cards.pop();

		sum -= card.getCardValue();
		
		return card;
	}

	public void setStand() throws Exception {
		if (status == PLAYING) {
			status = STAND;
		} else {
			throw new Exception ("Invalid Status!");
		}
	}

	public void setNoplay() throws Exception {
		if (isNoPlay()) {
			status = NOPLAY;
		} else {
			throw new Exception ("Invalid Status!");
		}
	}


	public void setDead() throws Exception {
		if (isDead()) {
			status = DEAD;
		} else {
			throw new Exception ("Invalid Status!");
		}
	}
	
	public int getStatus() {
		return status;
	}

	public Card getCardAt(int i) {
		return cards.get(i);
	}
	
	public void setBet(int b) {
		bet = b;
	}

	public int getBet() {
		return bet;
	}

	public int getSum() {
		return sum;
	}
	
    public int getSize() {
		return cards.size();
	}

	public boolean isLost() {
		return (sum > 75);
	}
    
	public boolean isDead() { // 7 ?
		return (getSize() == 1 && sum == 70);
	}

	public boolean isDeadEnd() {
		return (getSize() == 2 && sum == 75 && cards.get(0).getCardValue() == 70 &&  cards.get(1).getCardValue() == 5);
	}

	public boolean isHalf() { // 0.5 ?
		return (getSize() == 1 && sum == 5);
	}

	public boolean isSplitable() { // 0.5 & 0.5 ?
		return (getSize() == 2 && sum == 10 && cards.get(0).getCardValue() == 5 &&  cards.get(1).getCardValue() == 5);
	}
	
	public boolean isNoPlay() { // 0.5 ?
		return (getSize() == 1 && sum == 30);
	}

	public boolean isChangeCard() { // 0.5 ?
		return (getSize() == 1 && sum == 40);
	}

}
