package net.sockets;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.WrapperClass;

/**
 * Socket base class
 * This class cannot be instantiated
 * directly, it must be inherited.
 */
public abstract class MySocket {

    /**
     * Port to connect with
     */
    public static final int COMM_PORT = 2794;
    /**
     * Timeout
     */
    public static final int SOC_TIMEOUT = 30000;
    /**
     * Underlying socket
     */
    protected Socket theSocket;
    /**
     * Socket Output Stream
     */
    protected ObjectOutputStream socketOut;
    /**
     * Socket Input Stream
     */
    protected ObjectInputStream socketIn;
    /**
     * Wrapper Class
     */
    protected WrapperClass myData;
    /**
     * Local versions of global vars
     */
    protected int commPort, socTimeout;

    /**
     * Default Constructor
     * Starts a socket on the
     * port specified above
     *
     * @throws IOException If the socket cannot be opened
     */
    public MySocket() throws IOException {
        commPort = COMM_PORT;
        socTimeout = SOC_TIMEOUT;
    }

    /**
     * Constructor that starts a
     * socket on the port specified
     * in the constructor and not
     * the default port.
     *
     * @param thePort Port to open
     * @throws IOException If the socket cannot be opened
     */
    public MySocket(int thePort) throws IOException {
        //TODO Bounds Checking
        commPort = thePort;
        socTimeout = SOC_TIMEOUT;
    }

    /**
     * Used to make sure the sockets
     * close gracefully.
     * @Override finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        //
        if (theSocket != null) {
            //
            socketIn.close();
            socketOut.close();
            theSocket.close();
        }

        super.finalize();
    }

    /**
     * Must be implemented in child class
     *
     * @return Success flag
     * @throws IOException If socket config failed
     */
    protected abstract boolean doConfig() throws IOException;

    /**
     * Sets up various
     * connection settings
     *
     * @return Success flag
     */
    protected boolean doSetup() {
        //
        try {
            //Set the timeout
            theSocket.setSoTimeout(socTimeout);

            //Set up streams
            socketOut = new ObjectOutputStream(theSocket.getOutputStream());
            socketOut.flush();
            socketIn = new ObjectInputStream(theSocket.getInputStream());

            //TODO Shutdown Logic
            //Runtime.getRuntime().addShutdownHook(null);


        } catch (UnknownHostException e) {
            //TODO Exception Handling
            Logger.getLogger(MySocket.class.getName()).log(Level.SEVERE,
                    "The Host address was malformed, {0}\n", e);
            return false;
        } catch (IOException e) {
            Logger.getLogger(MySocket.class.getName()).log(Level.SEVERE,
                    "IO Exception occurred, {0}\n", e);
            return false;
        } catch (Exception e) {
            Logger.getLogger(MySocket.class.getName()).log(Level.SEVERE,
                    "General Exception occurred, {0}\n", e);
            return false;
        }

        //Everything went okay
        return true;
    }

    /**
     * Warning
     *  When called, this method will be
     *  blocked until it receives data.
     *  Make sure that the timeout has
     *  been set for this socket if you
     *  are unsure if data will arrive.
     *
     *  As of version 0.01, the timeout
     *  has been set to 10,000ms (10 Seconds)
     *
     * @return WrapperClass containing the data read from the socket
     * @throws SocketFailure If there was a socket error
     */
    public WrapperClass readData() throws SocketFailure {
        Object tmpObject;

        //Attempt to read the data
        try {
            //Read the data and cast it
            tmpObject = socketIn.readObject();

            if (tmpObject instanceof WrapperClass) {
                //Return it
                myData = (WrapperClass) tmpObject;
                return myData;
            } else {
                return null;
            }

        } catch (InterruptedIOException e) {
            //Error, timeout
            System.out.println("Timeout error: " + e);
            return null;
        } catch (EOFException e) {
            //Bad data
            throw new SocketFailure();
        } catch (SocketException e) {
            //Socket died
            throw new SocketFailure();
        } catch (IOException e) {
            //Other IO Error
            System.out.println("Other IO Error: " + e);
            return null;
        } catch (ClassNotFoundException e) {
            //Snap
            System.out.println("Class not found: " + e);
            return null;
        }
    }

    /**
     * Sends a WrapperClass over the socket
     *
     * @param data WrapperClass to send
     * @return Success flag
     */
    public boolean sendData(WrapperClass data) {

        //Attempt to send
        try {
            //Send the data
            socketOut.writeObject(data);
            //Flush the stream
            socketOut.flush();
            //All went well
            return true;
        } catch (IOException e) {
            //
            System.out.println("Send data IO Exception: " + e);
            return false;
        } catch (Exception e) {
            //Some other Exception
            System.out.println("Some Other Exception: " + e);
            return false;
        }
    }

    /**
     * Closes the socket.
     * Should be called in case
     * finalize() does not work.
     *
     */
    public void closeSocket() {
        //Check to see if there is an open socket
        if (theSocket != null) {
            try {
                //Attempt to close everything
                socketIn.close();
                socketOut.flush();
                socketOut.close();
                theSocket.close();
            } catch (IOException e) {
                //Nothing to see here
            } catch (Exception e) {
                //srvSocket might be null, but we don't care
            }
        }
    }

    /**
     * Opens a socket
     *
     * @throws IOException On Error
     */
    public void openSocket() throws IOException {
        doConfig();
    }

    /**
     * Returns connection state.
     *
     * @return Connection state
     */
    protected boolean isConnected() {
        //
        if (theSocket != null) {
            if (theSocket.isConnected()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Gets the buffer size from
     * the underlying socket
     *
     * @return The buffer size
     */
    public int getSendBufferSize() {
        //
        try {
            return theSocket.getSendBufferSize();
        } catch (SocketException e) {
            //
            return -1;
        }
    }

    /**
     * Returns the PTR DNS record
     * for the client this socket
     * is connected to.
     *
     * @return DNS record of client
     */
    public String getInetAddress() {
        return theSocket.getInetAddress().getHostName();
    }
}
