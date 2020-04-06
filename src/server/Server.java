package server;
// Name: Tiancheng Dong   Student ID: 2471036d

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

// This class is a Server of Twenty-one game based on command line
public class Server implements Runnable {
	private ServerSocket ss;
	// Number of players and current Player ID
	private int playerNum;
	private int currentID;
	private GameModel gm;
	// List of Client Thread which stores clients connections
	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	
	// Constructor of the Server. PORT of the server is set to 8765
	public Server() {
		System.out.println("Twenty-one Server");
		playerNum = 0;
		currentID = 1;
		gm = new GameModel();
		try {
			ss = new ServerSocket(8765);
		}catch(IOException e) {
			// Print error info if server is fail to create
			e.printStackTrace();
		}
	}
	
	// Method to accept client connection
	public void run() {
		try {
			System.out.println("Waiting for connections...");
			while(true) {
				// Check whether the connection exceed limiation (5)
				Socket s = ss.accept();
				if(playerNum < 5) {
					playerNum++;
					System.out.println("Player " + playerNum + " has connected.");
					ClientThread client = new ClientThread(s, currentID);
					currentID++;
					clients.add(client);
					Thread t = new Thread(client);
					t.start();
				}else {
					System.out.println("We now have 5 players. No longer accepting connections.");
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// Method to send info to all clients
	public void sendToAll() {
		for(ClientThread ct : clients) {
			ct.sendInfo();
		}
	}
	
	// An inner class of client Thread
	private class ClientThread implements Runnable {
		// Variables for network communication with client
		private Socket socket;
		private DataInputStream dis;
		private DataOutputStream dos;
		// Player ID which starts with 1
		private int playerID;
		
		// Constructor of the class with attributes socket and playerID
		public ClientThread(Socket s, int id) {
			socket = s;
			playerID = id;
			try {
				// Initialize data input and output
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			try {
				// Send name to the client and add the new player to players list
				String name = "Player " + playerID;
				dos.writeUTF(name);
				dos.flush();
				gm.addPlayer(new Player(name));
				// While player number is greater than 1, a game can be started
				if(playerNum >= 2) {
					sendToAll();
				}
				// Receive button number (integer 1-5) from client and update game info to all clients
				while(true) {
					int btnNum = dis.readInt();
					System.out.println("Player " + playerID + " clicked button " + btnNum);
					gm.playerSel("Player " + playerID, btnNum);
					sendToAll();
				}
			}catch(IOException e) {
				// If the server fail to communicate with client, print error and disconnect
				e.printStackTrace();
				disconnect();
			}
		}
		
		// Method for disconnection of the player
		private void disconnect() {
			gm.subPlayer("Player " + playerID);
			clients.remove(this);
			playerNum--;
			System.out.println("Player " + playerID + " disconnected from the server");
		}
		
		// Get the current game info and send to client
		public void sendInfo() {
			String info = gm.getGameInfo("Player " + playerID);
			
			System.out.println(info);
			
			try {
				dos.writeUTF(info);
				dos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		Thread server = new Thread(new Server());
		server.start();
	}
}
