package edu;
import java.util.ArrayList;


/**
 *
 * 
 */
public class GEORoute {

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
}
