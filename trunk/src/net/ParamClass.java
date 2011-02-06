package net;

import java.io.Serializable;

	/*
	 * Purpose of this class is to keep the configuration initialized by parents.
	 */

public class ParamClass implements Serializable {

	/**
	 *  @variable  loiteringTime Maximum number of seconds that a child must stay in a same spot.
	 *  @variable  deviationFeet Maximum range of distance that a child can be apart from the route.
	 */
	private static final long serialVersionUID = 1L;
	double loiteringTime, deviationFeet;
	
	public ParamClass()
	{
		//
	}
	
	/*
	 * Constructor 1: Takes the maximum loitering time and deviation range. 
	 */
	public ParamClass( String loiteringTime, String deviationFeet)
	{
		this.loiteringTime = Double.parseDouble(loiteringTime);
		this.deviationFeet = Double.parseDouble(deviationFeet);
	}
	
	/*
	 * Constructor 2: Takes the maximum loitering time and deviation range.
	 */
	public ParamClass( double loiteringTime, double deviationFeet)
	{
		this.loiteringTime = loiteringTime;
		this.deviationFeet = deviationFeet;
	}
	
	/*
	 * setLoiteringTime method lets parent modify the new loitering time.
	 */
	public void setLoiteringTime(String loiteringTime)
	{
		this.loiteringTime = Double.parseDouble(loiteringTime);
	}
	
	/*
	 * setDeviationFeet method lets parent to modify the deviation range.
	 */
	public void setDeviationFeet(String deviationFeet)
	{
		this.deviationFeet = Double.parseDouble(deviationFeet);
	}
	
	/*
	 * setLoiteringTime method lets parent modify the new loitering time.
	 */
	public void setLoiteringTime(double loiteringTime)
	{
		this.loiteringTime = loiteringTime;
	}
	
	/*
	 * setDeviationFeet method lets parent modify the new deviation range.
	 */
	public void setDeviationFeet(double deviationFeet)
	{
		this.deviationFeet = deviationFeet;
	}
	
	/*
	 * getLoiteringTime method returns the time that the child has been loitering in a current spot.
	 */
	public double getLoiteringTime()
	{
		return loiteringTime; 
	}
	
	/*
	 * getDeviationFeet method returns how far the child is from nearest route point.
	 */
	public double getDeviationFeet()
	{
		return deviationFeet;
	}
	
	
}
