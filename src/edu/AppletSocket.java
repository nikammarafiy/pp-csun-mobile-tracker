package edu;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.Client;
import net.WrapperClass;
import net.WrapperClass.wrapperTypes;
import net.sockets.MyClientSocket;
import net.sockets.SocketFailure;

public class AppletSocket extends Thread {
	
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
    
    protected String hostAddr;
    protected Object myParent;
    protected WrapperClass myData;

    /**
     * Default constructor
     */
    public AppletSocket(String theHost, Object parent) {
        waitingOnGUI = true;
        hostAddr = theHost;
        myParent = parent;
    }

    /**
	 * Thread Invoked
	 */
	public void run() {
        //Attempt to connect to the server
        try {
            socket = new MyClientSocket(hostAddr);
        } catch (Exception e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, 
                    "Error connectiong to server:{0}\n", e);
            return;
        }

        //Inform user
        Logger.getLogger(Client.class.getName()).log(Level.INFO, 
                "Connection accepted by server.\n");

        //SHOW GUI CODE HERE
        waitingOnGUI = true;
        
        //Handle
        readSocket();

        //Dispose the GUI

        //Close the socket
        socket.closeSocket();
        Logger.getLogger(Client.class.getName()).log(Level.INFO, 
                "Client Exiting.\n");
        System.exit(0);
    }

    /**
     * Starts GUI
     */
    private void readSocket() {
        //Show GUI
        while (true) {
        	//
        	WrapperClass tmpWrap = null;;
			try {
				tmpWrap = socket.readData();
			} catch (SocketFailure e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	if( tmpWrap == null )
        	{
        		myData = null;
        	}
        	else if( tmpWrap.getDType() == wrapperTypes.ALERT)
        	{
        		//We have an alert, tell the thread owner
        		String tmpStr = (String)tmpWrap.getData();
        		NotifyMe tmpTracker = (NotifyMe)myParent;
        		tmpTracker.alertNotify(tmpStr);
        		myData = null;
        	}
        	else
        	{
        		myData = tmpWrap;
        	}
        }
    }

    /**
     * Send Data utility method
     * Child objects call this method
     * using the reference they were
     * given when instantiated
     *
     * @param data Data to send
     * @param dType Data type
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
       return myData;
    }
    
    public void closeConn()
	{
		// Close the socket
		socket.closeSocket();
		Logger.getLogger(Client.class.getName()).log(Level.INFO,
		"Client Exiting.\n");
	}

}
