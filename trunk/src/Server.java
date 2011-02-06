import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Server class for handling requests
 *
 */
public class Server extends Thread {

    //The Socket Used by the Main Server Control Class
    private DatagramSocket serverSocket;
    //
    /**
     *
     */
    public static final String DATE_FORMAT_NOW = "MM/dd/yyyy HH:mm:ss";

    /**
     * Default constructor
     *
     */
    public Server() {
    	//Init vars
    }
    
    public void run() {
    	//create socket server and wait for connection requests
    	byte[] recBuffer;
    	
        try {
            serverSocket = new DatagramSocket(1025);
            
            
            Logger.getLogger(Server.class.getName()).log(Level.INFO,
                    "Server waiting for Client on port {0}.\n",
                    serverSocket.getLocalPort());

            //Loop infinitely
            while (true) {
                //Accept connection
            	recBuffer = new byte[2048];
                DatagramPacket thePacket = new DatagramPacket(recBuffer, recBuffer.length);
                serverSocket.receive(thePacket);

                Logger.getLogger(Server.class.getName()).log(Level.INFO,
                        "New Client initiated a connection at - {0}.\n",
                        Server.now());

                //Pass it off to a new thread
                ServerThread t = new ServerThread(serverSocket, thePacket);
                t.start();

            }
        } catch (IOException e) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE,
                    "IO Exception occurred on Server Socket: {0}.\n",
                    e);
        } catch (Exception e) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE,
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
        Server s = new Server();
        s.run();
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
