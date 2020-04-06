package client;
// Name: Tiancheng Dong   Student ID: 2471036d

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// Model of the Client
public class ClientModel implements Runnable {
	// Variable with name and balance of the player
	private String name;
	private int balance = 100;
	// Variables for network communication with server
	private Socket server = null;
	private DataInputStream dis;
	private DataOutputStream dos;
	private ClientView clientView;
	
	// Constructor of the class which takes View as attribute
	public ClientModel(ClientView cv) {
		try {
			// Initialize the network connection
			clientView = cv;
			server = new Socket("127.0.0.1", 8765);
			dis = new DataInputStream(server.getInputStream());
			dos = new DataOutputStream(server.getOutputStream());
			// Receive player name from server
			name = dis.readUTF();
			clientView.setCurPlayerInfo(name, balance);
			// Run a thread of this class using run() method
			new Thread(this).start();
		}catch(IOException e) {
			// Print the error info if fail to connect to the server
			e.printStackTrace();
		}
	}
	
	// Method to send button number (integer 1-5) to the server
	public void sendButton(int n) {
		try {
			dos.writeInt(n);
			dos.flush();
		} catch (IOException e) {
			// Print the error info if fail to send int
			e.printStackTrace();
		}
	}
	
	// Method to close connection with the server
	public void closeConnection() {
		try {
			server.close();
			System.out.println("Connection closed.");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}
	
	/* The method is called while Thread is created
	 * It can receive game info (String) from the server
	 */
	public void run() {
		try {
			while(true) {
				// Read and parse the info by spliting it with symbols
				String infostring = "" + dis.readUTF();
				System.out.println(infostring);
				String[] info = infostring.split(";");
				String otherPInfo = "";
				
				int i = 0;
				for(String str : info) {
					if(i == 0) {
						String[] basInfo = str.split(",");
						String currentActive = "";
						// If the player is broke (balance < 0) then close the connection 
						if(basInfo[0].equals("broke")) {
							closeConnection();
							clientView.setBroke();
						}else {
							// balance and current active player
							balance = Integer.parseInt(basInfo[0]);
							currentActive = basInfo[1];
							clientView.setCurPlayerInfo(name, balance);
						}
						
						// Check the active player
						if(currentActive.equals(name)) {
							clientView.setHitEnabled();
							clientView.setStandEnabled();
						// Initialize the GUI if a new round is ready to start
						}else if(currentActive.equals("ready")) {
							clientView.initCards();
						}else if(currentActive.equals("waiting") && !basInfo[0].equals("broke")) {
							clientView.setBetEnabled();
						}
					}else if(i == 1){
						// Get and update the player's cards
						String[] playersInfo = str.split(":");
						if(playersInfo.length > 1) {
							String[] cards = playersInfo[1].split(",");
							clientView.updateCards(cards, 2);
						}
					}else if(i == 2){
						// Get and update the banker's cards
						String[] bankerInfo = str.split(":");
						if(bankerInfo.length > 1) {
							String[] cards = bankerInfo[1].split(",");
							clientView.updateCards(cards, 1);
						}
					}else {
						// Get and update other players' balance
						String[] playersInfo = str.split(":");
						String name = playersInfo[0];
						String balance = playersInfo[1];
						otherPInfo += name + ": " + balance + "  ";
					}
					i++;
				}

				clientView.setOtherPlayers(otherPInfo);
			}
			
		}catch(IOException e) {
			// Print the error info if fail to receive info from server
			e.printStackTrace();
		}
	}
}
