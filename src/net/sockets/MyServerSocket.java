package net.sockets;

import java.io.IOException;
import java.net.Socket;



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
}
