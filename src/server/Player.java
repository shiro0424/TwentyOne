package server;
// Name: Tiancheng Dong   Student ID: 2471036d

import java.util.ArrayList;

// This class represents a game player
public class Player {
	// Name of the player
	private String name;
	// Variable cards represents current cards of player
	// Variable isBlack is true while the point of initial 2 cards is 21
	private ArrayList<Card> cards = new ArrayList<Card>();
	private boolean isBlack = false;
	// Bet number set by the player
	private int bet;
	// Balance of the player, initially set to 100
	private int balance = 100;
	
	// Constructor of the class with an attribute of name
	public Player(String n) {
		name = n;
	}
	
	// Setter of name
	public void setName(String name) {
		this.name = name;
	}
	
	// Setter of bet number
	public void setBet(int bet) {
		this.bet = bet;
	}
	
	// Getter of bet number
	public int getBet() {
		return bet;
	}
	
	// Getter of name
	public String getName() {
		return name;
	}
	
	// Getter of balance
	public int getBalance() {
		return balance;
	}
	
	// Method to calculate point of player's cards
	public int getPoint() {
		int point = 0;
		int ace = 0;
		
		/* Loop through all the cards of player
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
	
	// Getter of cards to represent cards of player as String info
	public String getCards(){
		String cardsInfo = this.getName() + ":";
		for(Card c : cards) {
			if(c.equals(cards.get(cards.size()-1))) {
				cardsInfo += c;
			}else {
				cardsInfo += c + ",";
			}
		}
		return cardsInfo;
	}
	
	// Method to check whether the player is bust (point > 21)
	public boolean isBust() {
		int point = getPoint();
		
		point = getPoint();
		if(point > 21) {
			return true;
		}else {
			return false;
		}
	}
	
	// Method to set the player to Black
	public void setBlack() {
		isBlack = true;
	}
	
	// Method to get whether the player is Black
	public boolean isBlack() {
		return isBlack;
	}
	
	// Method to add a new card to the players' cards list
	public void addCard(Card card) {
		cards.add(card);
	}
	
	// Method to initialize the player's cards
	public void clearCards() {
		cards.clear();
		bet = 0;
		isBlack = false;
	}
	
	// Method to add balance of the player
	public void addBalance(int n) {
		this.balance += n;
	}
	
	// Method to sub balance of the player
	public void subBalance(int n) {
		this.balance -= n;
	}
}
