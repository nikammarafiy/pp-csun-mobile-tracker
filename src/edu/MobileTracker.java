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
import net.ParamClass;
import net.WrapperClass;
import net.WrapperClass.wrapperTypes;
import net.sockets.MyClientSocket;
import net.sockets.SocketFailure;

public class MobileTracker extends Applet implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6356834955406563726L;
	/**
	 * Socket to server
	 * 
	 */
	protected AppletSocket socket;
	/**
	 * Thread flags
	 */
	protected boolean waitingOnGUI;
	/**
	 * Stop flag
	 */
	protected boolean stop;

	ImageIcon icon;

    TextField inputAddr1 = new TextField(50);
    TextField inputAddr2 = new TextField(50);
    TextField inputLoitTime = new TextField(50);
    TextField inputDevFeet = new TextField(50);
    TextField inputRouteTime = new TextField(50);
    JLabel lblAddr1 = new JLabel("Enter a Start Address: (eg. Street, City, State, Zip) ");
    JLabel lblAddr2 = new JLabel("Enter a Finish Address: (eg. Street, City, State, Zip) ");
    JLabel lblLoitTime = new JLabel("Enter Loitering TimeOut (mins): ");
    JLabel lblDevTime = new JLabel("Enter Route Deviation Tolerance (feet): ");
    JLabel lblRouteTime = new JLabel("Enter intended route completion time (mins): ");
    JLabel lblIcon = new JLabel("", JLabel.CENTER);

	Button clear;
	Button enter;

	public void init() {
		clear = new Button("Clear");
		add(clear);

		enter = new Button("Submit");
		add(enter);
		add(lblIcon);
	}

	public MobileTracker() {
		    add(lblAddr1);
	        add(inputAddr1);
	        add(lblAddr2);
	        add(inputAddr2);
	        add(lblLoitTime);
	        add(inputLoitTime);
	        add(lblDevTime);
	        add(inputDevFeet);
	        add(lblRouteTime);
	        add(inputRouteTime);
	        inputAddr1.addActionListener(this);
	        inputAddr2.addActionListener(this);
	        inputLoitTime.addActionListener(this);
	        inputDevFeet.addActionListener(this);
	        inputRouteTime.addActionListener(this);

	}

	public boolean action(Event e, Object args) {
		String s1 ="";
	    String s2 ="";
	    String s3 ="";
	    String s4 ="";
	    String s5 ="";
		String street;
		String city;
		String state;
		String zip;
		String street2;
		String city2;
		String state2;
		String zip2;

		if (e.target == clear) {
			inputAddr1.setText("");
			inputAddr2.setText("");
			inputLoitTime.setText("");
			inputDevFeet.setText("");
			inputRouteTime.setText("");
		}
		if (e.target == enter) {
			s1 = inputAddr1.getText();
			s2 = inputAddr2.getText();
			
			s1 = s1.replace(", ", ",");
			s2 = s2.replace(", ", ",");
			
			if( socket == null)
			{
				doSetup();
			}

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
			
			String devFeet, loitTime, routeTime;
			devFeet = inputDevFeet.getText();
			loitTime = inputLoitTime.getText();
			routeTime = inputRouteTime.getText();
			
			
			ParamClass tmpParams = new ParamClass(devFeet,loitTime,routeTime);
			socket.sendData(tmpParams, wrapperTypes.PARAM);

			AddressClass add1 = new AddressClass();
			add1.setStartAddress(street, city, state, zip);
			socket.sendData(add1, wrapperTypes.ADDRESS);
			add1 = new AddressClass();
			add1.setEndAddress(street2, city2, state2, zip2);
			socket.sendData(add1, wrapperTypes.ADDRESS);			
			

			WrapperClass tmpWrap = null;
			
			while(tmpWrap==null)
			{
				tmpWrap = socket.getData();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if( tmpWrap.getDType() == wrapperTypes.IMAGE)
			{
				byte[] tmpByte = (byte[])tmpWrap.getData();
			
				icon = new ImageIcon(tmpByte);
				lblIcon.setIcon(icon);
				this.repaint();
				
			}
			tmpWrap = null;
			
			//inputAddr1.setText(theMapURL);
			
			//closeConn();
		}

		return true;
	}

	public void actionPerformed(ActionEvent e) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public void alertNotify(String alertText)
	{
		//Show alert
		JOptionPane.showConfirmDialog(this, alertText, "MobileTracker", 3, 0);
	}

	/**
	 * Sets up client
	 */
	private void doSetup() {
		// Attempt to connect to the server
		try {
			socket = new AppletSocket("kitchensync.jhl.me", this);
			socket.start();
			Thread.sleep(500);
		} catch (Exception e) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE,
					"Error connectiong to server:{0}\n", e);
			return;
		}

		// Inform user
		Logger.getLogger(Client.class.getName()).log(Level.INFO,
		"Connection accepted by server.\n");
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