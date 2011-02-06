//Set package
package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sockets.*;

/**
 * MyServer class for handling requests
 *
 */
public class MyServer {

    //The Socket Used by the Main MyServer Control Class
    private ServerSocket serverSocket;
    //
    /**
     *   @ Variable DATE_FORMAT_NOW Format for current date
     */
    public static final String DATE_FORMAT_NOW = "MM/dd/yyyy HH:mm:ss";

    /**
     * Default constructor
     *
     */
    public MyServer() {

        //create socket server and wait for connection requests
        try {
            serverSocket = new ServerSocket(MySocket.COMM_PORT);
            Logger.getLogger(MyServer.class.getName()).log(Level.INFO,
                    "Server waiting for Client on port {0}.\n",
                    serverSocket.getLocalPort());

            //Loop infinitely
            while (true) {
                //Accept connection
                MyServerSocket socket =
                        new MyServerSocket(serverSocket.accept());

                Logger.getLogger(MyServer.class.getName()).log(Level.INFO,
                        "New Client initiated a connection at - {0}.\n",
                        MyServer.now());

                //Pass it off to a new thread
                MyServerThread t = new MyServerThread(socket);
                t.start();

            }
        } catch (IOException e) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE,
                    "IO Exception occurred on Server Socket: {0}.\n",
                    e);
        } catch (Exception e) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE,
                    "General Exception occurred on Server Socket: {0}.\n",
                    e);
        }
    }

    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        //Start the server
        MyServer s = new MyServer();
    }

    /**
     * Gets the current time and date
     *
     * @return Formatted time and date
     */
    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());

    }
}
