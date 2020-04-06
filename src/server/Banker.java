package server;
// Name: Tiancheng Dong   Student ID: 2471036d

import java.util.ArrayList;

public class Banker {
	// Variable cards represents current cards of banker
	// Variable isBlack is true while the point of initial 2 cards is 21
	private ArrayList<Card> cards = new ArrayList<Card>();
	private boolean isBlack = false;
	
	public Banker() {}
	
	// Method to calculate point of banker's cards
	public int getPoint() {
		int point = 0;
		int ace = 0;
		
		/* Loop through all the cards of banker
		*  Get the corresponding point if the card value is not "A"
		*  Otherwise, let the integer of ace plus 1 */ 
		for(Card c : cards) {
			String value = c.getValue();
			if(value == "J" || value == "Q" || value == "K") {
				point += 10;
			}else if(value == "A") {
				ace++;
			}else {
				point += Integer.valueOf(value);
			}
		}
		
		/* Set the value of ace to 1 if point is greater than 10
		*  Otherwise, the point of ace is 11. Return point at the end of method */
		while(ace != 0) {
			if(point > 10) {
				point += 1;
			}else {
				point += 11;
			}
			ace--;
		}
		return point;
	}
	
	// A method to check whether the banker is bust (point > 21)
	public boolean isBust() {
		int point = getPoint();
		if(point > 21) {
			return true;
		}else {
			return false;
		}
	}
	
	// A setter to set the banker to Black
	public void setBlack() {
		isBlack = true;
	}
	
	// A getter to return the isBlack value
	public boolean isBlack() {
		return isBlack;
	}
	
	// A getter to return the cards
	public ArrayList<Card> getCards(){
		return cards;
	}
	
	// To add a new card to banker's cards
	public void addCard(Card card) {
		cards.add(card);
	}
	
	// To initial the status of banker
	public void clearCards() {
		cards = new ArrayList<Card>();
		isBlack = false;
	}
}
