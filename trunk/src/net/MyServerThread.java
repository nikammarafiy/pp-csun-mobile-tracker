//Set package
package net;

//Import the socket package
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import edu.GEORoute;
import edu.GoogleGPS;
import master.Main;
import net.WrapperClass.wrapperTypes;
import net.sockets.*;

/**
 * Worker class for the Server, handles all requests after the Server class
 * passes off the connection.
 * 
 */
public class MyServerThread extends Thread {

	/**
	 * The server socket for this thread
	 */
	protected MyServerSocket socket; // Socket
	 private Main myParent;
	/**
	 * Wrapper class for sending and receiving data
	 */
	private WrapperClass tmpWrapper;
	
	private AddressClass startAddr, endAddr;
	private ParamClass curParams;
	private GEORoute curRoute;
	private GoogleGPS myGoog;
	private long routeStartTime;
	private boolean didIAlert;

	/**
	 * Constructor We need to pass the database through this constructor
	 * 
	 * @param socket
	 *            The socket this Thread will use
	 */
	public MyServerThread(MyServerSocket socket) {
		this.socket = socket;
		startAddr = null;
		endAddr = null;
		curParams = null;
		curRoute = null;
		myGoog = new GoogleGPS();
		didIAlert = false;
	}

	/**
	 * Thread Invoked
	 */
	public void run() {
		System.out.println("Thread started for client: "
				+ socket.getInetAddress() + ".");
		
		//Setup timer
		prepTimer();
		
		// Perform Read
		doRead();

		// Close the Socket
		try {
			socket.closeSocket();
		} catch (Exception e) {
			System.out.println("Other Socket Close Exception: " + e);
		}
	}
	
	private void prepTimer()
	{
		int delay = 20000; //10 Seconds
		  ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		          checkParams();
		      }
		  };
		  new Timer(delay, taskPerformer).start();
	}
	
	private void checkParams()
	{
		//
		if( curRoute == null || Main.getLastPosition() == null || didIAlert ) return;
		
		//
		double feetAway = myGoog.getDistance(curRoute, Main.getLastPosition());
		double loitTime = Main.checkLoitering();
		double totalTimeMillis = System.currentTimeMillis() - routeStartTime;
		
		if( feetAway > curParams.getDeviationFeet())
		{
			sendAlert("Your child has deviated.");
			didIAlert = true;
		}else if( loitTime > curParams.getLoiteringTime())
		{
			sendAlert("Your child is loitering.");
			didIAlert = true;
		} else if( totalTimeMillis > curParams.getRouteTime())
		{
			sendAlert("Your child has not reached their destination soon enough.");
			didIAlert = true;
		}
		
		//Decide what to do
	}

	/**
	 * Private utility function used to get data from the socket
	 */
	private void doRead() {
		// Read a Wrapper Infinitely long
		boolean runFlag = true;
		while (runFlag) {
			try {
				tmpWrapper = socket.readData(); // Read the data
				runFlag = unWrap(); // Unwrap the data
			} catch (SocketFailure e) {
				//
				socket.closeSocket();
				break;
			} catch (Exception e) {
				System.out.println("Exception reading/writing  Streams: " + e);
				break;
			}
		}
	}

	/**
	 * Unwraps the data in a wrapper object and performs the appropriate action
	 * 
	 * @return A success flag
	 */
	private boolean unWrap() {
		// Type var
		WrapperClass.wrapperTypes tmpType;

		// Double check null
		if (tmpWrapper != null) {
			// Get the data type
			tmpType = tmpWrapper.getDType();

			// Do what we need to depending on the type
			switch (tmpType) {
			//
			case ADDRESS:
				// Address class
				handleAddress(tmpWrapper.getData());
				break;
			case PARAM:
				//PAram class
				handleParam(tmpWrapper.getData());
				break;
			case STRING:
				// String Debug handling
				String tmpStr = (String) tmpWrapper.getData();
				System.out.println("\nString from client: " + tmpStr + "\n");

				break;

			case OTHER:
				// Some Other Class Handling
				sendAlert( "This is a test, only a test");
				break;

			default:
				// Something weird happened
				break;

			}

		}

		return true;
	}
	
	private void handleAddress(Object tmpObj)
	{
		//
		AddressClass tmpAddr = (AddressClass)tmpObj;
		
		if( tmpAddr.isStartAddress() )
		{
			startAddr = tmpAddr;
		}
		else
		{
			endAddr = tmpAddr;
		}
		
		if( startAddr != null && endAddr != null)
		{
			//We have both addresses, root
			//TODO Routing and send back data
			GoogleGPS tmpGoog = new GoogleGPS();
			curRoute = tmpGoog.createRoute(startAddr.generateAddr(), endAddr.generateAddr());
			
			//Send back the URL to the map
			String tmpURL = curRoute.getMapURL();
			this.sendData(curRoute.getImage(tmpURL), wrapperTypes.IMAGE);
			//this.sendData(tmpURL, wrapperTypes.MAP_URL);
			
			routeStartTime = System.currentTimeMillis();
		}
	}
	
	private void handleParam(Object tmpObj)
	{
		//
		ParamClass tmpParam = (ParamClass)tmpObj;
		curParams = tmpParam;
	}
	
	public void sendAlert(String alertText)
	{
		sendData(alertText, wrapperTypes.ALERT);
	}

	/**
	 * Send Data utility method Child objects call this method using the
	 * reference they were given when instantiated
	 * 
	 * @param data
	 *            Data to wrap and send
	 * @param dType
	 *            The data type of the data we are sending
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
	 * @return WrapperClass retrieved from the socket
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
}
