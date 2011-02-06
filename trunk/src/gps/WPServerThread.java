package gps;

import java.util.Calendar;

import io.MakeFile;
import master.Main;
import net.AddressClass;
import net.ParamClass;
import net.WrapperClass;
import net.WrapperClass.wrapperTypes;
import net.sockets.MyServerSocket;
import net.sockets.SocketFailure;
import edu.GEOPoint;
import edu.GEORoute;
import edu.GoogleGPS;

public class WPServerThread {
	

	/**
	 * The server socket for this thread
	 */
	protected MyServerSocket socket; // Socket
	 private MakeFile myFile;
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
		
		myFile = new MakeFile();
		
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
		socket.setTimeout(2000);
		
		boolean runFlag = true;
		while (runFlag) {
			try {
				tmpWrapper = new String(socket.readRawData()); // Read the data
				System.out.println(tmpWrapper);
				parseGPS(tmpWrapper);
				
			} catch (Exception e) {
				System.out.println("Exception reading/writing  Streams: " + e);
				break;
			}
		}
	}
	
	private void parseGPS(String httpData)
	{
		//
		int startIdx, endIdx;
		String startDelim = "GET ";
		String endDelim = "HTTP/1.1";
		String tmpStr, strLat, strLong;
		Double x, y;
		GEOPoint tmpPoint;
		
		startIdx = httpData.indexOf(startDelim);
		endIdx = httpData.indexOf(endDelim);
		
		if( startIdx > -1 && endIdx > -1 )
		{
			//Ok
			tmpStr = httpData.substring(startIdx+4, endIdx);
			startIdx = tmpStr.indexOf("Long");
			endIdx = startIdx + 4;
			strLat = tmpStr.substring(tmpStr.indexOf("Lat") + 3,startIdx-1);
			strLong = tmpStr.substring(tmpStr.indexOf("Long") + 4,tmpStr.length()-1);
			
			System.out.println("\t" + strLat);
			System.out.println("\t" + strLong);
			
			//Save the Lat and Long, and add it to the Main ArrayList
			x = Double.parseDouble(strLat);
			y = Double.parseDouble(strLong);
			
			myFile.input(x, y, Long.toString(System.currentTimeMillis()));
			tmpPoint = new GEOPoint(x,y);
			Main.addGEOPoint(tmpPoint);
		}
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
	
	protected void finalize() throws Throwable {
	    try {
	        myFile.closefile();        // close open files
	    } finally {
	        super.finalize();
	    }
	}
}

