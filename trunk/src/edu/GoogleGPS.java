package edu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import json.JSONArray;
import json.JSONException;
import json.JSONObject;


public class GoogleGPS {

	private final static String googURL = "http://maps.googleapis.com/maps/api/directions/json?origin=%START%&destination=%END%&sensor=false&mode=walking";

	public double getDistance(GEORoute theRoute, GEOPoint curLoc) {
		//
		double smallestDistance = -1;
		double tmpDistance;
		GEOPoint smallestPoint = null;
		GEOPoint tmpPoint = null;

		for (int i = 0; i < theRoute.getLength(); i++) {
			tmpPoint = theRoute.getPoint(i);
			tmpDistance = calcDistance(tmpPoint, curLoc);

			if (i == 0) {
				smallestDistance = tmpDistance;
				smallestPoint = tmpPoint;
			} else if (tmpDistance < smallestDistance) {
				// Save
				smallestDistance = tmpDistance;
				smallestPoint = tmpPoint;
			}
		}

		return smallestDistance;
	}

	public GEORoute createRoute(String startLocation, String endLocation) {
		//
		GEORoute tmpRoute = new GEORoute();
		String strURL = GoogleGPS.googURL;
		strURL = strURL.replace("%START%", startLocation);
		strURL = strURL.replace("%END%", endLocation);

		try {
			String strJSON = convertStreamToString(new URL(strURL).openStream());
			
			//System.out.println(strJSON);
			
			JSONObject tmpJSON = new JSONObject(strJSON);
			JSONObject jsonRoute = tmpJSON.getJSONArray("routes")
					.getJSONObject(0);
			JSONObject jsonLegs = jsonRoute.getJSONArray("legs").getJSONObject(
					0);
			JSONArray jsonSteps = jsonLegs.getJSONArray("steps");

			for (int i = 0; i < jsonSteps.length(); i++) {
				//
				JSONObject curStep = jsonSteps.getJSONObject(i);
				JSONObject curStart = curStep.getJSONObject("start_location");
				GEOPoint tmpGeo = new GEOPoint(curStart.getString("lat"),
						curStart.getString("lng"));
				tmpRoute.addPoint(tmpGeo);
				JSONObject curEnd = curStep.getJSONObject("end_location");
				tmpGeo = new GEOPoint(curEnd.getString("lat"),
						curEnd.getString("lng"));
				tmpRoute.addPoint(tmpGeo);
			}
		} catch (JSONException ex) {
			return null;
		} catch (IOException ex) {
			return null;
		}

		return tmpRoute;
	}
	
	public double distBetweenPoints(GEOPoint point1, GEOPoint point2)
	{
		//
		double earthRadius = 3959;		//3959 Miles, 6,371km
		double lat1, lat2;
		double long1, long2;
		
		lat1 = point1.getLattitude();
		lat2 = point2.getLattitude();
		
		long1 = point1.getLongitude();
		long2 = point2.getLongitude();
		
		double dLat = Math.toRadians( lat1 - lat2 );
		double dLong = Math.toRadians( long1 - long2 );
		
		double result1 = Math.sin(dLat/2) * Math.sin(dLat/2);
		double result2 = Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		double result3 = Math.sin(dLong/2) * Math.sin(dLong/2);
		
		double tmpResult = result1 + result2 * result3;
		double result4 = Math.atan2(Math.sqrt(tmpResult), Math.sqrt(1-tmpResult));
		double theResult = earthRadius * result4;
		
		return theResult * 5280;			//Convert to feet
	}

	private double calcDistance(GEOPoint point1, GEOPoint point2) {
		//
		double lat1, long1;
		double lat2, long2;
		double lats, longs;
		double tmpDist = 0;

		lat1 = point1.getLattitude();
		long1 = point1.getLongitude();

		lat2 = point2.getLattitude();
		long2 = point2.getLongitude();

		lats = (lat1 - lat2);
		longs = (long1 + long2);

		tmpDist = Math.sqrt(Math.pow(lats, 2) + Math.pow(longs, 2));

		return tmpDist;
	}

	private String convertStreamToString(InputStream is) throws IOException {

		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

}
