package net.sockets;

/**
 * Custom Socket Exception class
 *
 */
public class SocketFailure extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Default Constructor
     *
     */
    public SocketFailure() {
        super();
    }

    /**
     * Message Constructor
     *
     * @param arg Message to print
     */
    public SocketFailure(String arg) {
        super(arg);
    }
}
