package edu;

import java.util.ArrayList;


/**
 * Class that holds route data for the specified path
 * 
 */
public class GEORoute {
	
	//Google url for map image
	private final static String googURL = "http://maps.google.com/maps/api/staticmap?sensor=false&path=weight:3|color:green";

	//Storage for the GEOPoints
    private ArrayList<GEOPoint> myRoute;

    /**
     * Default Constructor
     */
    public GEORoute()
    {
        myRoute = new ArrayList<GEOPoint>();
    }

    /**
     * Get the size of the route
     * @return The size of the route
     */
    public int getLength()
    {
        return myRoute.size();
    }

    /**
     * Adds a GEOPoint to the end of the route
     * @param tmpPoint The GEOPoint to add
     * @return Success flag
     */
    public boolean addPoint(GEOPoint tmpPoint)
    {
        myRoute.add(tmpPoint);
        return true;
    }

    /**
     * Returns a GEOPoint at the specified index
     * @param i The index to get the GEOPoint from
     * @return The requested GEOPoint
     */
    public GEOPoint getPoint(int i)
    {
        if( i > myRoute.size() || i < 0)
        {
            return null;
        }
        
        return myRoute.get(i);
    }
    
    /**
     * Gets a static map url for this route
     * @return A static map URL for this route
     */
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
