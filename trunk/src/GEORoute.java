/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javagoogapitest;

import java.util.ArrayList;

/**
 *
 * @author Jeff
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
