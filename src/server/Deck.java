package server;
// Name: Tiancheng Dong   Student ID: 2471036d

import java.util.Random;

/* Class used to store and operate a deck of cards (52)
 * Suits: "Spade", "Heart", "Diamond", "Club"
 * Values: "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"
 */
public class Deck {
	// Create an array of deck cards, values and suits
	private Card[] cards = new Card[52];
	private String[] suits = {"Spade", "Heart", "Diamond", "Club"};
	private String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
	// Index of the deck used to check the next card
	private int cardIndex;
	
	// Constructor of Deck
	public Deck() {
		cardIndex = -1;
		int count = 0;
		// Generate a new cards deck
		for(String suit : suits) {
			for(String value : values) {
				cards[count] = new Card(suit, value);
				count++;
			}
		}
	}
	
	// Method to shuffle card randomly 3 times
	public void shuffleCard() {
		Random random = new Random();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < cards.length; j++) {
				int p = random.nextInt(cards.length);
				Card temp = cards[j];
				this.cards[j] = cards[p];
				this.cards[p] = temp;
			}
		}
	}
	
	// Getter of the next card which is decided by cardIndex
	public Card getNextCard() {
		cardIndex++;
		return cards[cardIndex];
	}
}
