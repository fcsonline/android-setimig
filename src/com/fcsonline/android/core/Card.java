package com.fcsonline.android.core;

public class Card  {

	private Integer id;

	public Card(Integer id) {
		super();
		this.id = id;
	}

	public int getId () {
		return id;
	}
	
	public int getCardValue() {
		int val = id % 13; // 1..10 J Q K

		val++;

		if (val >= 10)
			return 5; // 0.5
		else
			return (val * 10);
	}
	
	@Override
	public String toString() {
		
		StringBuffer text = new StringBuffer();
		
		int val = id % 13; // 1..10 J Q K
		
		val++;
		
		if (val == 11) {
			text.append('J');
		} else if (val == 12) {
			text.append('Q');
		} else if (val == 13) {
			text.append('K');
		} else if (val == 0) {
			text.append('A');
		} else {
			text.append(val);
		}
		
		// Club (c) Diamond (d) Hearts (h) Spade (s) 
		switch (id / 13) {
		case 0:	
			text.append('c');
			break;
		case 1:
			text.append('d');
			break;
		case 2:
			text.append('h');
			break;
		case 3:
			text.append('s');
			break;
		}
		
		return text.toString();
	}
	
	
	
}
