package net;

import java.io.Serializable;

public class ParamClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double loiteringTime, deviationFeet;
	
	public ParamClass()
	{
		//
	}
	
	public ParamClass( String loiteringTime, String deviationFeet)
	{
		//
		this.loiteringTime = Double.parseDouble(loiteringTime);
		this.deviationFeet = Double.parseDouble(deviationFeet);
	}
	
	public ParamClass( double loiteringTime, double deviationFeet)
	{
		//
		this.loiteringTime = loiteringTime;
		this.deviationFeet = deviationFeet;
	}
	
	public void setLoiteringTime(String loiteringTime)
	{
		//
		this.loiteringTime = Double.parseDouble(loiteringTime);
	}
	
	public void setDeviationFeet(String deviationFeet)
	{
		//
		this.deviationFeet = Double.parseDouble(deviationFeet);
	}
	
	public void setLoiteringTime(double loiteringTime)
	{
		//
		this.loiteringTime = loiteringTime;
	}
	
	public void setDeviationFeet(double deviationFeet)
	{
		//
		this.deviationFeet = deviationFeet;
	}
	
	public double getLoiteringTime()
	{
		return loiteringTime; 
	}
	
	public double getDeviationFeet()
	{
		return deviationFeet;
	}
	
	
}
