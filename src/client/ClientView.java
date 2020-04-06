package client;
// Name: Tiancheng Dong   Student ID: 2471036d

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

// View of the Client GUI
public class ClientView extends JFrame {
	// Components of the GUI
	private JLabel curPlayerInfo;
	private JLabel bankerInfo;
	private JLabel otherPlayers;
	private JPanel[] cardsPanel, bankerPanel;
	private JLabel[] cardsLabel, bankerLabel;
	private JButton bet1, bet2, bet3, hit, stand;
	
	// Constructor of the GUI
	public ClientView() {
		initGUI();
	}
	
	// Method to initialize the GUI
	public void initGUI() {
		setTitle("Twenty-One");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(50, 50);
		setResizable(false);
		setLayout(null);
		// create a container object that can contain components
		Container c = getContentPane();
		
		Font f = new Font("Times New Roman", Font.PLAIN, 20);
		Font f1 = new Font("Times New Roman", Font.PLAIN, 27);
		
		// Label to display other players' balance
		otherPlayers = new JLabel("Waiting for other players");
		otherPlayers.setBounds(50, 50, 500, 50);
		otherPlayers.setFont(f);
		c.add(otherPlayers);
		
		// Label to display the current players's name and balance
		curPlayerInfo = new JLabel("");
		curPlayerInfo.setBounds(240, 510, 300, 50);
		curPlayerInfo.setFont(f1);
		c.add(curPlayerInfo);
		
		// Label to display "Banker"
		bankerInfo = new JLabel("Banker");
		bankerInfo.setBounds(300, 95, 350, 60);
		bankerInfo.setFont(f1);
		c.add(bankerInfo);
		
		initCards();
		
		/* Buttons to bet 10, 20 or 30 balance of the player
		 * which is initially set to disabled before another player join
		 */
		bet1 = new JButton("10");
		bet1.setFont(f);
		bet1.setEnabled(false);
		bet1.setBounds(140, 310, 60, 30);
		c.add(bet1);
		
		bet2 = new JButton("20");
		bet2.setFont(f);
		bet2.setEnabled(false);
		bet2.setBounds(210, 310, 60, 30);
		c.add(bet2);
		
		bet3 = new JButton("30");
		bet3.setFont(f);
		bet3.setEnabled(false);
		bet3.setBounds(280, 310, 60, 30);
		c.add(bet3);
		
		// Button to hit card while the player is active
		hit = new JButton("Hit");
		hit.setFont(f);
		hit.setBounds(480, 310, 90, 30);
		hit.setEnabled(false);
		c.add(hit);
		
		// Button to stand and wait for other players' actions
		stand = new JButton("Stand");
		stand.setFont(f);
		stand.setBounds(580, 310, 90, 30);
		stand.setEnabled(false);
		c.add(stand);
		
		// Set the GUI visible
		this.setVisible(true);
	}
	
	// Getters of the buttons
	public JButton getButton1() {
		return bet1;
	}
	
	public JButton getButton2() {
		return bet2;
	}
	
	public JButton getButton3() {
		return bet3;
	}
	
	public JButton getButton4() {
		return hit;
	}
	
	public JButton getButton5() {
		return stand;
	}
	
	/* Method to set cards panel and label
	 * with player's cards below and banker's cards above
	 * Initially set to "Empty"
	 */
	public void initCards() {
		cardsPanel = new JPanel[5];
		cardsLabel = new JLabel[5];
		bankerPanel = new JPanel[5];
		bankerLabel = new JLabel[5];
		Font f = new Font("Times New Roman", Font.PLAIN, 20);
		Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
		
		for (int i = 0; i < 5; i++) {
			bankerPanel[i] = new JPanel(new BorderLayout());
			bankerPanel[i].setBackground(Color.YELLOW);
			bankerPanel[i].setBounds(130 + i * 110, 150, 105, 150);
			bankerLabel[i] = new JLabel("     Empty");
			bankerLabel[i].setFont(f);
			bankerPanel[i].setBorder(blackLine);
			bankerPanel[i].add(bankerLabel[i]);
			this.getContentPane().add(bankerPanel[i]);
			
			cardsPanel[i] = new JPanel(new BorderLayout());
			cardsPanel[i].setBackground(Color.YELLOW);
			cardsPanel[i].setBounds(130 + i * 110, 350, 105, 150);
			cardsLabel[i] = new JLabel("     Empty");
			cardsLabel[i].setFont(f);
			cardsPanel[i].setBorder(blackLine);
			cardsPanel[i].add(cardsLabel[i]);
			this.getContentPane().add(cardsPanel[i]);
		}
	}
	
	// Method used to update player's and banker's cards while playing
	public void updateCards(String[] cardName, int n) {
		Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
		
		// As there can be at most 5 cards, do it in a loop
		for (int i = 0; i < 5; i++) {
			// Update the bankers' cards
			if(n == 1) {
				// Check whether the current position is empty
				if(i >= cardName.length || cardName.length == 0) {
					// If current position is empty, set to "Empty"
					bankerPanel[i].setBackground(Color.YELLOW);
					bankerLabel[i].setText("     Empty");
					bankerPanel[i].setBorder(blackLine);
					bankerPanel[i].add(bankerLabel[i]);
				}else {
					// If current position is not empty, read card image from folder and display
					String name = cardName[i];
					String cardURL = "image//" + name + ".jpg";
					ImageIcon img = new ImageIcon(cardURL);
					bankerPanel[i].setBorder(BorderFactory.createEmptyBorder());
					bankerLabel[i].setIcon(img);
				}
			// Update the players' cards
			}else {
				if(i < cardName.length && cardName.length != 0) {
					String name = cardName[i];
					String cardURL = "image//" + name + ".jpg";
					ImageIcon img = new ImageIcon(cardURL);
					cardsPanel[i].setBorder(BorderFactory.createEmptyBorder());
					cardsLabel[i].setIcon(img);
				}else {
					cardsPanel[i].setBackground(Color.YELLOW);
					cardsLabel[i].setText("     Empty");
					cardsPanel[i].setBorder(blackLine);
					cardsPanel[i].add(cardsLabel[i]);
				}
			}
		}
	}
	
	// Update the player name and balance to the label
	public void setCurPlayerInfo(String name, int balance) {
		curPlayerInfo.setText(name + "     Balance: " + balance);
	}
	
	// Update other players' balance
	public void setOtherPlayers(String info) {
		otherPlayers.setText(info);
	}
	
	// Set the message if player is broke and disable buttons
	public void setBroke() {
		curPlayerInfo.setText("You're broke. Game over!");
		bet1.setEnabled(false);
		bet2.setEnabled(false);
		bet3.setEnabled(false);
		hit.setEnabled(false);
		stand.setEnabled(false);
	}
	
	// Methods used to set buttons enabled or disabled
	public void setBetEnabled() {
		bet1.setEnabled(true);
		bet2.setEnabled(true);
		bet3.setEnabled(true);
	}
	
	public void setBetDisabled() {
		bet1.setEnabled(false);
		bet2.setEnabled(false);
		bet3.setEnabled(false);
	}
	
	public void setHitEnabled() {
		hit.setEnabled(true);
	}
	
	public void setHitDisabled() {
		hit.setEnabled(false);
	}
	
	public void setStandEnabled() {
		stand.setEnabled(true);
	}
	
	public void setStandDisabled() {
		stand.setEnabled(false);
	}
}
