package edu;

import java.util.ArrayList;


/**
 *
 * 
 */
public class GEORoute {
	
	private final static String googURL = "http://maps.google.com/maps/api/staticmap?sensor=false&path=weight:3|color:green";

    private ArrayList<GEOPoint> myRoute;

    public GEORoute()
    {
        //
        myRoute = new ArrayList<GEOPoint>();
    }

    public int getLength()
    {
        return myRoute.size();
    }

    public boolean addPoint(GEOPoint tmpPoint)
    {
        myRoute.add(tmpPoint);
        return true;
    }

    public GEOPoint getPoint(int i)
    {
        if( i > myRoute.size() || i < 0)
        {
            return null;
        }
        
        return myRoute.get(i);
    }
    
    public String getMapURL()
    {
    	//
    	String tmpURL = GEORoute.googURL;
    	GEOPoint tmpPoint;
    	
    	for( int i = 0; i < myRoute.size();i++)
    	{
    		//
    		tmpPoint = myRoute.get(i);
    		tmpURL += tmpPoint.getLattitude() + "," + tmpPoint.getLongitude() + "|";
    	}
    	
    	return tmpURL;
    }
}
