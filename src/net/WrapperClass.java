package net;

import java.io.Serializable;

public class WrapperClass implements Serializable  {
	
	public static enum wrapperTypes
	{
		ADDRESS, PARAM, MAP_URL, MAP_REQ, IMAGE, ALERT, STRING, OTHER
	};
	
	private Object theData;
	private wrapperTypes theDataType;
	
	public WrapperClass()
	{
		//
	}
	
	public wrapperTypes getDType()
	{
		return theDataType;
	}
	
	public Object getData()
	{
		return theData;
	}
	
	public void setData(Object data)
	{
		theData = data;
	}
	
	public void setDType( wrapperTypes newType)
	{
		theDataType = newType;
	}
	

}
