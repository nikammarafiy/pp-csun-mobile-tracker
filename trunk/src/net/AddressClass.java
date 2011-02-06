package net;

import java.io.Serializable;

public class AddressClass implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isStartAddress;
	private String strStreet;
	private String strCity;
	private String strState;
	private int intZipCode;
	private String strCountry;
	
	public AddressClass()
	{
		//
	}
	
	public void setStartAddress(String street, String city, String state, String zipCode)
	{
		//
		isStartAddress = true;
		strStreet = street;
		strCity = city;
		strState = state;
		intZipCode = Integer.parseInt(zipCode);
		strCountry = "USA";
	}
	
	public void setEndAddress(String street, String city, String state, String zipCode)
	{
		//
		isStartAddress = false;
		strStreet = street;
		strCity = city;
		strState = state;
		intZipCode = Integer.parseInt(zipCode);
		strCountry = "USA";
	}

}
