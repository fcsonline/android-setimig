package com.fcsonline.android.core;

import java.util.Random;

public class Dealer {
	
	private boolean[] cards = new boolean[52];

	private int availables;

	private Random random = new Random();

	public Dealer() {
		reset();
	}

	public void reset() {

		availables = 0;
		
		for (int i = 0; i < 52; i++) {
			cards[i] = (i % 13 == 7 || i % 13 == 8); // In setimig don't play width 8's and 9's
			
			if (false) {
				cards[i] = (i % 13 < 9); // For testing
			}
			
			if (!cards[i]) {
				availables++;
			}
		}

	}

	public Card next() throws Exception {
		if (availables <= 0)
			throw new Exception("No availible cards!");

		int rval;

		do {
			rval = random.nextInt();
			rval = Math.abs(rval % 52);
		} while (cards[rval]);

		cards[rval] = true;
		availables--;

		return new Card(rval);
	}
}
