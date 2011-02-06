//Set package
package net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sockets.*;

/**
 * Client Control Class
 *
 * @author Group 5
 */
public class Client {

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

    /**
     * Default constructor
     */
    public Client() {
        waitingOnGUI = true;
    }

    /**
     * Sets up client
     */
    private void doSetup() {
        //Attempt to connect to the server
        try {
            socket = new MyClientSocket("localhost");
        } catch (Exception e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, 
                    "Error connectiong to server:{0}\n", e);
            return;
        }

        //Inform user
        Logger.getLogger(Client.class.getName()).log(Level.INFO, 
                "Connection accepted by server.\n");

        //SHOW GUI CODE HERE
        
        //Handle
        startGUIHandling();

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
    private void startGUIHandling() {
        //Show GUI
        while (waitingOnGUI) {
            //Start the Sync thread evntually
            //Sleep
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                //
                break;
            }
        }
    }

    /**
     * Starts class
     *
     * @param arg
     */
    public static void main(String[] arg) {
        new Client().doSetup();
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
