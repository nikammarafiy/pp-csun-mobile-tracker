package edu;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.AddressClass;
import net.Client;
import net.WrapperClass;
import net.WrapperClass.wrapperTypes;
import net.sockets.MyClientSocket;
import net.sockets.SocketFailure;

public class MobileTracker extends Applet implements ActionListener {
	/**
	 * Socket to server
	 * 
	 */
	protected MyClientSocket socket;
	/**
	 * Thread flags
	 */
	protected boolean waitingOnGUI;
	/**
	 * Stop flag
	 */
	protected boolean stop;

	ImageIcon icon;
	TextField inputLine1 = new TextField(50);
	TextField inputLine2 = new TextField(50);
	JLabel label1 = new JLabel(
	"Enter a Start Address: (eg. Street, City, State, Zip) ");
	JLabel label2 = new JLabel(
	"Enter a Finish Address: (eg. Street, City, State, Zip) ");

	Button clear;
	Button enter;

	public void init() {
		clear = new Button("Clear");
		add(clear);

		enter = new Button("Submit");
		add(enter);
	}

	public MobileTracker() {
		add(label1);
		add(inputLine1);
		add(label2);
		add(inputLine2);
		
		inputLine1.addActionListener(this);
		inputLine2.addActionListener(this);

	}

	public boolean action(Event e, Object args) {
		String s1 = "";
		String s2 = "";
		String street;
		String city;
		String state;
		String zip;
		String street2;
		String city2;
		String state2;
		String zip2;

		if (e.target == clear) {
			inputLine1.setText("");
			inputLine2.setText("");
		}
		if (e.target == enter) {
			s1 = inputLine1.getText();
			s2 = inputLine2.getText();
			
			doSetup();

			String[] piecesofdata1;
			String[] piecesofdata2;

			piecesofdata1 = s1.split(",");
			piecesofdata2 = s2.split(",");

			street = piecesofdata1[0];
			city = piecesofdata1[1];
			state = piecesofdata1[2];
			zip = piecesofdata1[3];

			street2 = piecesofdata2[0];
			city2 = piecesofdata2[1];
			state2 = piecesofdata2[2];
			zip2 = piecesofdata2[3];

			AddressClass add1 = new AddressClass();
			add1.setStartAddress(street, city, state, zip);
			this.sendData(add1, wrapperTypes.ADDRESS);
			add1 = new AddressClass();
			add1.setEndAddress(street2, city2, state2, zip2);
			this.sendData(add1, wrapperTypes.ADDRESS);

			WrapperClass tmpWrap = this.getData();
			//String theMapURL = (String) tmpWrap.getData();
			byte[] tmpByte = (byte[])tmpWrap.getData();
			
			icon = new ImageIcon(tmpByte);
			label1 = new JLabel("Image and Text", icon, JLabel.CENTER);
			add(label1);
			
			//inputLine1.setText(theMapURL);
			
			closeConn();
		}

		return true;
	}

	public void actionPerformed(ActionEvent e) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Sets up client
	 */
	private void doSetup() {
		// Attempt to connect to the server
		try {
			socket = new MyClientSocket("localhost");
		} catch (Exception e) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE,
					"Error connectiong to server:{0}\n", e);
			return;
		}

		// Inform user
		Logger.getLogger(Client.class.getName()).log(Level.INFO,
		"Connection accepted by server.\n");
	}
	
	private void closeConn()
	{
		// Close the socket
		socket.closeSocket();
		Logger.getLogger(Client.class.getName()).log(Level.INFO,
		"Client Exiting.\n");
	}

	/**
	 * Send Data utility method Child objects call this method using the
	 * reference they were given when instantiated
	 * 
	 * @param data
	 *            Data to send
	 * @param dType
	 *            Data type
	 */
	public void sendData(Object data, WrapperClass.wrapperTypes dType) {
		WrapperClass tmpWrap = new WrapperClass();
		tmpWrap.setData(data);
		tmpWrap.setDType(dType);

		socket.sendData(tmpWrap);
	}

	/**
	 * Gets data from the socket
	 * 
	 * @return Data from socket
	 */
	public WrapperClass getData() {
		try {
			return socket.readData();
		} catch (SocketFailure e) {
			socket.closeSocket();
			System.exit(1);
			return null;
		}
	}

	/**
	 * Sets the stop flag
	 * 
	 */
	public void stop() {
		stop = true;
		waitingOnGUI = false;
	}

	/**
	 * Sets the GUI flag
	 * 
	 */
	public void guiDone() {
		waitingOnGUI = false;

	}

}