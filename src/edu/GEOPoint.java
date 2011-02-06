package edu;
/**
 * Class that encapsulates a GPS coordinate.
 *
 */
public class GEOPoint implements Comparable {

    //Coordinates
    private double latitude;
    private double longitude;

    /**
     * Constructor that takes a latitude and longitude
     * as a String and casts them to a float.
     * @param latitudeE6 A String representing a latitude
     * @param longitudeE6 A String representing a longitude
     */
    public GEOPoint(String latitudeE6, String longitudeE6) {
        //
        try {
            latitude = Float.parseFloat(latitudeE6);
            longitude = Float.parseFloat(longitudeE6);
        } catch (Exception e) {
            //
            latitude = -1;
            longitude = -1;
        }
    }

    /**
     * Constructor that takes a latitude and longitude
     * as two floats.
     * @param latitudeE6 A float representing a latitude
     * @param longitudeE6 A float representing a longitude
     */
    public GEOPoint(double latitudeE6, double longitudeE6) {
        latitude = latitudeE6;
        longitude = longitudeE6;
    }

    /**
     *
     * @return The Latitude
     */
    public double getLattitude() {
        return latitude;
    }

    /**
     *
     * @return The longitude
     */
    public double getLongitude() {
        return longitude;
    }
    
    /**
     * ToString Method that returns a String as 
     * Latitude,Longitude
     */
    public String toString()
    {
    	return latitude + "," + longitude;
    }
    
    /**
     * getData Method that returns a String as 
     * Latitude,Longitude
     */
    public String getData()
    {
    	return latitude + "," + longitude;
    }

    /**
     * Sorting
     * @param o
     * @return
     */
    public int compareTo(Object o) {
        if (o instanceof GEOPoint) {
            GEOPoint tmpGEO = (GEOPoint) o;
            return Double.compare(tmpGEO.getLattitude()
                    + tmpGEO.getLongitude(), latitude + longitude);
        } else {
            return -1;
        }
    }

    /**
     * Comparison
     * @param obj
     * @return
     */
    public boolean equals(Object obj) {
        //
        if (compareTo(obj) == 0) {
            return true;
        } else {
            return false;
        }
    }
}
