package gps;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import master.Main;
import net.MyServer;
import net.MyServerThread;
import net.sockets.MyServerSocket;
import net.sockets.MySocket;

/**
 * If WP7 cannot do sockets but can open a webpage, we could use the following server like so:
 * http://kitchensync.jhl.me:2795/Lat=LATITUDE&Long=LONGITUDE
 * 
 * The server would get raw data like so: 
 * GET /Lat=LATITUDE&Long=LONGITUDE HTTP/1.1
 * Host: localhost:2795
 * Connection: keep-alive
 * Cache-Control: max-age=0
 * 
 * And we could just parse the "GET" part
 * @author Jeff
 *
 */

public class WPServer extends Thread {
	

    //The Socket Used by the Main MyServer Control Class
    private ServerSocket serverSocket;
    //
    /**
     *
     */
    public static final String DATE_FORMAT_NOW = "MM/dd/yyyy HH:mm:ss";

    /**
     * Default constructor
     *
     */
    public WPServer() {
    	//
    }
    
    public void run()
    {
    	//create socket server and wait for connection requests
        try {
            serverSocket = new ServerSocket(2795);
            Logger.getLogger(MyServer.class.getName()).log(Level.INFO,
                    "Server waiting for Client on port {0}.\n",
                    serverSocket.getLocalPort());

            //Loop infinitely
            while (true) {
                //Accept connection
                MyServerSocket socket =
                        new MyServerSocket(serverSocket.accept(),true);

                Logger.getLogger(MyServer.class.getName()).log(Level.INFO,
                        "New Client initiated a connection at - {0}.\n",
                        MyServer.now());

                //Pass it off to a new thread
                WPServerThread t = new WPServerThread(socket);
                t.run();

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


