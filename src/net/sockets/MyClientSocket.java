package net.sockets;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Client Side Socket
 */
public class MyClientSocket extends MySocket {

    /**
     * The address of the server
     *
     */
    protected String destDevice;

    /**
     * Default Constructor
     *
     * @param destinationDevice The address of the server
     * @throws IOException On socket error
     */
    public MyClientSocket(String destinationDevice) throws IOException {
        super();
        destDevice = destinationDevice;
        openSocket();
    }

    /**
     * Sets up socket and
     * streams
     *
     * @return Success flag
     */
    protected boolean doConfig() {
        //TODO Bounds Checking
        try {
            //Open the socket
            theSocket = new Socket(destDevice, commPort);
        } catch (UnknownHostException e) {
            //TODO Exception Handling
            Logger.getLogger(MyClientSocket.class.getName()).log(Level.SEVERE,
                    "The Host address was malformed, {0}\n", e);
            return false;
        } catch (IOException e) {
            Logger.getLogger(MyClientSocket.class.getName()).log(Level.SEVERE,
                    "IO Exception occurred, {0}\n", e);
            return false;
        } catch (Exception e) {
            Logger.getLogger(MyClientSocket.class.getName()).log(Level.SEVERE,
                    "General Exception occurred, {0}\n", e);
            return false;
        }

        //Everything went okay, set up config
        return true && super.doSetup();
    }
}
