package client;
// Name: Tiancheng Dong   Student ID: 2471036d

// The Main class of Client in order to start a game as player
public class Client{
	private ClientModel model;
	private ClientView view;
	
	public Client() {
		// Initialize view, model and controller
		System.out.println("----Twenty-one Client----");
		view = new ClientView();
		model = new ClientModel(view);
		new ClientController(model, view);
	}
	
	public static void main(String[] args) {
		new Client();
	}
}