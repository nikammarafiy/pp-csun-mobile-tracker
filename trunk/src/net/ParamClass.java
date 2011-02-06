package net;

public class ParamClass {

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
	
	
}
