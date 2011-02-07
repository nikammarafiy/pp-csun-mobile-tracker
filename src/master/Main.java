package master;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;



import net.MyServer;

import edu.GEOPoint;
import edu.GoogleGPS;
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
		
		//
		
	}
	
	public static GEOPoint getLastPosition()
	{
		//
		if( lastPoints.size() == 0) return null;
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
	
	public static double checkLoitering()
	{
		//
		double tmpDist = 0, tmpTime = 0;
		GoogleGPS tmpGoog = new GoogleGPS();
		GEOPoint tmpGEO1, tmpGEO2;
		
		//Loop through last known points and determine loitering
		for( int i = 0; i < lastPoints.size()-1; i++)
		{
			//
			tmpGEO1 = lastPoints.get(i);
			tmpGEO2 = lastPoints.get(i+1);
			
			tmpDist = tmpGoog.distBetweenPoints(tmpGEO1, tmpGEO2);
			
			System.out.println("Distance between: " + tmpDist);
			
			if( tmpDist < 30.0)
			{
				tmpTime += 5.0;
			}
		}
		
		return tmpTime;	//
	}
	
	/*
	public static double milliToMins(long millis)
	{
		return String.format("%d min, %d sec", 
			    TimeUnit.MILLISECONDS.toMinutes(millis),
			    TimeUnit.MILLISECONDS.toSeconds(millis) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
			);
	}*/

}
