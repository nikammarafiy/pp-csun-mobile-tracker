//7
package edu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import master.Main;


/**
 * Class that holds route data for the specified path
 * 
 */
public class GEORoute {
	
	//Google url for map image
	private final static String googURL = "http://maps.google.com/maps/api/staticmap?mode=walking&sensor=false&size=383x322&maptype=roadmap&path=weight:5|color:green|";

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
    	GEOPoint curPos = Main.getLastPosition();
    	
    	for( int i = 0; i < myRoute.size();i++)
    	{
    		//
    		tmpPoint = myRoute.get(i);
    		tmpURL += tmpPoint.getLattitude() + "," + tmpPoint.getLongitude() + "|";
    	}
    	
    	tmpURL = tmpURL.substring(0, tmpURL.length() - 2);
    	
    	if( curPos != null)
    	{
    		tmpURL += "&markers=color:blue|label:C|" + curPos.getLattitude() + "," + curPos.getLongitude();
    	}
    	
    	System.out.println(tmpURL);
    	
    	return tmpURL;
    }
    
    public byte[] getImage(String theURL)
    {
    	//
    	URL newURL = null;
    	try {
			newURL = new URL(theURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	/*ArrayList<byte[]> tmpByte = new ArrayList<byte[]>();
    	byte[] tmpArr;*/
    	
    	ByteArrayOutputStream bais = new ByteArrayOutputStream();
    	InputStream is = null;
    	try {
    	  is = newURL.openStream();
    	  byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
    	  int n;

    	  while ( (n = is.read(byteChunk)) > 0 ) {
    	    bais.write(byteChunk, 0, n);
    	  }
    	}
    	catch (IOException e) {
    	  System.err.printf ("Failed while reading bytes from: %s", e.getMessage());
    	  e.printStackTrace ();
    	  // Perform any other exception handling that's appropriate.
    	}
    	finally {
    	  if (is != null) { try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} }
    	}
    	
    	return bais.toByteArray();
    }
}
