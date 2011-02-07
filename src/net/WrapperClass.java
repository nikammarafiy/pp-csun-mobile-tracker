package net;

import java.io.Serializable;

/*
 * Wrapper class used to wrap objects before sending them through a socket.
 * We always know that the object will be of WrapperClass, so it makes casting easier
 * 
 */
public class WrapperClass implements Serializable  {
	
	//Enum types
	public static enum wrapperTypes
	{
		ADDRESS, PARAM, MAP_URL, MAP_REQ, IMAGE, ALERT, STRING, OTHER
	};
	
	//Data
	private Object theData;
	private wrapperTypes theDataType;
	
	public WrapperClass()
	{
		//Nothing to set up
	}
	
	/**
	 * Get the data type
	 * @return
	 */
	public wrapperTypes getDType()
	{
		return theDataType;
	}
	
	/**
	 * Get the data
	 * @return
	 */
	public Object getData()
	{
		return theData;
	}
	
	/**
	 * Set the data
	 * @param data
	 */
	public void setData(Object data)
	{
		theData = data;
	}
	
	/**
	 * Set the data type
	 * @param newType
	 */
	public void setDType( wrapperTypes newType)
	{
		theDataType = newType;
	}
	

}
