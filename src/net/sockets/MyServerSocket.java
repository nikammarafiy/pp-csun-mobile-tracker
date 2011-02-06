package net.sockets;

import java.io.*;
import java.net.Socket;
import java.io.ByteArrayOutputStream;



/**
 * Socket Class for Server, inherits
 * from MyScoket.
 */
public class MyServerSocket extends MySocket {

    /**
     * Default Constructor
     * @param newSocket A Socket reference spawned by ServerSocket.accept()
     * @throws IOException if there is an underlying Socket error
     */
    public MyServerSocket(Socket newSocket) throws IOException {
        super();
        theSocket = newSocket;
        openSocket();
    }
    
    public MyServerSocket(Socket newSocket, boolean isRaw) throws IOException {
        super();
        theSocket = newSocket;
        //openSocket();
    }

    /**
     * Sets up the socket, overridden in case the Server needed to
     * do Server specific config
     * @return A flag to indicate if config went okay
     * @throws IOException
     */
    protected boolean doConfig()
            throws IOException {

        //Everything went okay
        return super.doSetup();
    }
    
    public byte[] readRawData()
    {
    	//
    	try {
			InputStream is = super.theSocket.getInputStream();
			ByteArrayOutputStream tmpBOS = new ByteArrayOutputStream();
			 byte[] byteChunk = new byte[8]; // Or whatever size you want to read in at a time.
	    	  int n, tmpCt = 0;
	    	  

	    	  while ( (n = is.read(byteChunk)) > 0 ) {
	    		  tmpBOS.write(byteChunk, 0, n);
	    		  
	    		  if( ++tmpCt > 50 ) break;
	    	  }
	    	  
	    	  return tmpBOS.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
    	
    }
    
    public Object readRawData2()
    {
    	try {
			return super.socketIn.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
    }
}
