package master;

import java.util.ArrayList;


import net.MyServer;

import edu.GEOPoint;
import gps.WPServer;

public class Main {
	
	private static ArrayList<GEOPoint> lastPoints = new ArrayList<GEOPoint>(75);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Start an Applet Server
		MyServer appletSrv = new MyServer();
		appletSrv.start();
		
		//Start a GPS Server
		WPServer gpsSrv = new WPServer();
		gpsSrv.start();

	}
	
	public static GEOPoint getLastPosition()
	{
		//
		return lastPoints.get(0);
	}
	
	public static ArrayList<GEOPoint> getLastPositions( int number)
	{
		//
		ArrayList<GEOPoint> tmpList = new ArrayList<GEOPoint>(number);
		
		for( int i = 0; i < number; i++ )
		{
			tmpList.add(lastPoints.get(i));
		}
		
		return tmpList;
	}
	
	public static void addGEOPoint(GEOPoint point)
	{
		//
		lastPoints.add(0, point);
		
		if( lastPoints.size() > 74)
		{
			lastPoints.remove(74);
		}
	}

}
