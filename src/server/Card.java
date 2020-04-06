package server;
// Name: Tiancheng Dong   Student ID: 2471036d

public class Card {
	// Values that represents attributes of a card
	private String suit;
	private String value;
	
	// Constructor the card
	public Card(String s, String v) {
		suit = s;
		value = v;
	}
	
	// Getter to return the suit
	public String getSuit() {
		return suit;
	}
	
	// Getter to return the value
	public String getValue() {
		return value;
	}
	
	// To represent a card with String (SuitValue)
	public String toString() {
		String cardString = suit + value;
		return cardString;
	}
}
