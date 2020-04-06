package client;
// Name: Tiancheng Dong   Student ID: 2471036d

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;;

// Controller of the client
public class ClientController {
	private ClientModel cm;
	private ClientView cv;
	private int hitCount = 0;

	// Constructor of the Controller with attributes model and view
	public ClientController(ClientModel model, ClientView view) {
		cm = model;
		cv = view;
		initController();
	}
	
	// Method to initialize the controller
	public void initController() {
		/* Add Action Listeners to five buttons
		 * If button 1, 2 or 3 is clicked, make them disabled
		 * and send the button number to server through ClientModel
		 * Button 4 is set to be allowed to click at most 3 times at one round
		 * Also send the num 4 or 5 to the server
		 */ 
		cv.getButton1().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cm.sendButton(1);
				cv.setBetDisabled();
			}
		});
		
		cv.getButton2().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cm.sendButton(2);
				cv.setBetDisabled();
			}
		});
		
		cv.getButton3().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cm.sendButton(3);
				cv.setBetDisabled();
			}
		});
		
		cv.getButton4().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cm.sendButton(4);
				hitCount++;
				if(hitCount > 2) {
					cv.setHitDisabled();
				}
			}
		});
		
		cv.getButton5().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cm.sendButton(5);
				cv.setHitDisabled();
				cv.setStandDisabled();
				hitCount = 0;
			}
		});
	}
}
