package wp7test;

import net.AddressClass;
import net.ParamClass;
import net.WrapperClass;
import net.WrapperClass.wrapperTypes;
import net.sockets.MyServerSocket;
import net.sockets.SocketFailure;
import edu.GEORoute;
import edu.GoogleGPS;

public class WPServerThread {
	

	/**
	 * The server socket for this thread
	 */
	protected MyServerSocket socket; // Socket
	/**
	 * Wrapper class for sending and receiving data
	 */
	private String tmpWrapper;
	
	private AddressClass startAddr, endAddr;
	private ParamClass curParams;
	private GEORoute curRoute;

	/**
	 * Constructor We need to pass the database through this constructor
	 * 
	 * @param socket
	 *            The socket this Thread will use
	 */
	public WPServerThread(MyServerSocket socket) {
		this.socket = socket;
		startAddr = null;
		endAddr = null;
		curParams = null;
		curRoute = null;
	}

	/**
	 * Thread Invoked
	 */
	public void run() {
		System.out.println("Thread started for client: "
				+ socket.getInetAddress() + ".");
		// Perform Read
		doRead();

		// Close the Socket
		try {
			socket.closeSocket();
		} catch (Exception e) {
			System.out.println("Other Socket Close Exception: " + e);
		}
	}

	/**
	 * Private utility function used to get data from the socket
	 */
	private void doRead() {
		// Read a Wrapper Infinitely long
		boolean runFlag = true;
		while (runFlag) {
			try {
				tmpWrapper = new String(socket.readRawData()); // Read the data
				System.out.println(tmpWrapper);
				//runFlag = unWrap(); // Unwrap the data
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
	 *//*
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
				break;

			default:
				// Something weird happened
				break;

			}

		}

		return true;
	}*/
	
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
		}
	}
	
	private void handleParam(Object tmpObj)
	{
		//
		ParamClass tmpParam = (ParamClass)tmpObj;
		curParams = tmpParam;
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

