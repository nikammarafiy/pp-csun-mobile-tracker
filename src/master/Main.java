package master;

import java.util.ArrayList;

import edu.GEOPoint;

public class Main {
	
	private static ArrayList<GEOPoint> lastPoints = new ArrayList<GEOPoint>(100);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
	
	public static void addGEOPoint()
	{
		//
	}

}
