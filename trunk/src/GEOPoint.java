/**
 * Class that encapsulates a GPS coordinate.
 *
 */
public class GEOPoint implements Comparable {

    //Coordinates
    private float latitude;
    private float longitude;

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
    public GEOPoint(float latitudeE6, float longitudeE6) {
        latitude = latitudeE6;
        longitude = longitudeE6;
    }

    /**
     *
     * @return The Lattitude
     */
    public float getLattitude() {
        return latitude;
    }

    /**
     *
     * @return The longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Sorting
     * @param o
     * @return
     */
    public int compareTo(Object o) {
        if (o instanceof GEOPoint) {
            GEOPoint tmpGEO = (GEOPoint) o;
            return Float.compare(tmpGEO.getLattitude()
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
