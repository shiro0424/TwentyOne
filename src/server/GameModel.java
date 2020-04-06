package server;
// Name: Tiancheng Dong   Student ID: 2471036d

import java.util.ArrayList;

// This class is the game logic of the server
public class GameModel {
	// An array list that stores players in the game server
	private ArrayList<Player> players;
	private Banker banker;
	private Deck deck;
	// Integer to count number of players that bet
	private int ready;
	// playerIndex and currentTurn is used to check and display the active player
	private int playerIndex;
	private String currentTurn;
	
	// Constructor of the class
	public GameModel() {
		// Initialize all attributes of the game round
		ready = 0;
		playerIndex = 0;
		currentTurn = "waiting";
		players = new ArrayList<Player>();
		banker = new Banker();
	}
	
	// This method is called while a new player join the game
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	// This method is called while a player broke or quit the game
	public void subPlayer(String name) {
		// Check the players' name and remove the player from list
		for(Player p : players) {
			if(p.getName().equals(name)) {
				players.remove(p);
				break;
			}
		}
	}

	/* Method to generate the game information
	 * Format of the information is:
	 * balance,currentTurn;Banker:banker cards;Player 1:player 1 cards;Player 2:balance...
	 */
	public String getGameInfo(String name) {
		String info = "";
		String otherPlayers = "";
		int count = 0;
		
		for(Player p : players) {
			if(name.equals(p.getName())) {
				if(currentTurn.equals("waiting") && p.getBalance() < 0) {
					info += "broke,";
				}else {
					info += p.getBalance() + ",";
				}
				info += currentTurn + ";";
				info += p.getCards() + ";";
			}else if(!name.equals(p.getName()) && count < players.size() - 1) {
				otherPlayers += p.getName() + ":" + p.getBalance() + ";";
			}else {
				otherPlayers += p.getName() + ":" + p.getBalance();
			}
			count++;
		}
		info += getBankerCards();
		info += ";" + otherPlayers;
		
		return info;
	}
	
	// Method to start a new round
	public void startRound() {
		// Create a new cards deck
		deck = new Deck();
		
		// Shuffle cards
		deck.shuffleCard();
		// Deal 2 cards to each player and banker
		for(int i = 0; i < 2; i++) {
			banker.addCard(deck.getNextCard());
			for(Player p : players) {
				p.addCard(deck.getNextCard());
			}
		}
		
		// Check whether banker is Black
		if(banker.getPoint() == 21) {
			banker.setBlack();
		}
		
		// Check whether player is Black
		for(Player p : players) {
			int point = p.getPoint();
			if(point == 21) {
				p.setBlack();
			}
		}
	}
	
	// Clear cards for each player and banker
	public void clearCards() {
		if(banker.getCards().size() != 0) {
			for(Player p : players) {
				p.clearCards();
			}
			banker.clearCards();
		}
	}
	
	// Actions while receiving button from a player
	public void playerSel(String name, int n) {
		int playerNum = players.size();
		
		// Check which player is the sender
		for(Player p : players) {
			if(p.getName().equals(name)) {
				// Bet balance 10, 20 or 30
				if(n == 1 || n == 2 || n ==3) {
					clearCards();
					p.setBet(n * 10);
					p.subBalance(n * 10);
					// While one player bets, one more ready for start
					ready++;
					currentTurn = "ready";
					
					/* Check whether player number equals or larger than 2
					 * If so, start a new round and generate the first player
					 */
					if(ready == playerNum && playerNum >= 2) {
						startRound();
						currentTurn = players.get(playerIndex).getName();
					}
				}else if(n == 4) {
					// Hit card action which gets next card for the player
					p.addCard(deck.getNextCard());
				}else {
					// Stand action and turn to the next player or the banker
					playerIndex++;
					if(playerIndex == players.size()) {
						currentTurn = "waiting";
						bankerAction();
						checkWinner();
					}else {
						currentTurn = players.get(playerIndex).getName();
					}
				}
			}else {
				continue;
			}
			break;
		}
	}

	// Method to enable banker to decide whether hit a card
	public void bankerAction() {
		// Get the point of banker. If smaller than 17 the hit a card
		int bankerPoint = banker.getPoint();
		while(bankerPoint < 17) {
			banker.addCard(deck.getNextCard());
			bankerPoint = banker.getPoint();
		}
	}
	
	// Method to check whether each player is win, lose or draw
	public void checkWinner() {
		for(Player p : players) {
			int bet = p.getBet();
			int playerPoint = p.getPoint();
			int bankerPoint = banker.getPoint();
			
			// If both of player and banker are Black, the player is draw and get bet returned
			if(p.isBlack() && banker.isBlack()) {
				p.addBalance(bet);
			// If player is Black but banker is not, player win 150% of the bet
			}else if(p.isBlack() && !banker.isBlack()) {
				p.addBalance(bet * 5 / 2);
			
			}else if(!p.isBust() && !p.isBlack() && banker.isBust()) {
				p.addBalance(bet * 2);
			// If banker is Black but player is not, player lose and pay 50% more bet to the banker
			}else if(!p.isBlack() && banker.isBlack()){
				p.subBalance(bet / 2);
			/* If both of player's and banker's points are smaller than 21
			 * and player's point is greater than bank's, player win and get the bet
			 */
			}else if((playerPoint <= 21) && (bankerPoint <= 21) && (playerPoint > bankerPoint)) {
				p.addBalance(bet * 2);
			// If both player and banker are Black, player is draw and get bet back
			}else if(p.isBlack() && banker.isBlack()){
				p.addBalance(bet);
			/* If both of player's and banker's points are smaller than 21
			 * and player's point is equals to bank's, player is draw and get bet back
			 */
			}else if((playerPoint <= 21) && (bankerPoint <= 21) && (playerPoint == bankerPoint)) {
				p.addBalance(bet);
			}
		}
		playerIndex = 0;
		ready = 0;
	}
	
	// Method to get banker's card list and represent as String
	public String getBankerCards() {
		String bankerCards = "Banker:";
		ArrayList<Card> cards = banker.getCards();
		
		// If the round is on playing return the first card and Back
		if(cards.size() > 0 && !currentTurn.equals("waiting")) {
			bankerCards += cards.get(0) + ",Back";
		// If round is end return all the cards of banker
		}else if(cards.size() > 0 && currentTurn.equals("waiting")) {
			for(Card c : cards) {
				if(c.equals(cards.get(cards.size() - 1))) {
					bankerCards += c;
				}else {
					bankerCards += c + ",";
				}
			}
		}
		
		return bankerCards;
	}
	
}
