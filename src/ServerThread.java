import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class ServerThread extends Thread {
	
	DatagramSocket socket = null;
    DatagramPacket packet = null;

	public ServerThread(DatagramSocket theSocket, DatagramPacket thePacket)
	{
		socket = theSocket;
		packet = thePacket;
	}
	
	public void run() {
		System.out.println(new String(packet.getData()));
	}
}