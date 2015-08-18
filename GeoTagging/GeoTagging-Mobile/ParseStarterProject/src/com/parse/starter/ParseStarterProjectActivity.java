package com.parse.starter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

 public class ParseStarterProjectActivity extends Activity {
	
	 LocationManager myLocationManager;
	 String PROVIDER = "";
	 Location location;	
		
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ParseAnalytics.trackAppOpenedInBackground(getIntent());			
		
		//Retrieve location based on the cell network or Wi-fi
		 PROVIDER = LocationManager.NETWORK_PROVIDER;//.GPS_PROVIDER;
		 myLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		    
		 //get last known location, if available  		
		 location=getLocation(PROVIDER);
		 saveMyLocation(location);
		 
		 //ParseObject.createWithoutData("MyLocation", "hKXGHSkSRH").deleteEventually(); 	
	}	
	
	  public Location getLocation(String provider) 
	  {
			if (myLocationManager.isProviderEnabled(provider))
			{
				myLocationManager.requestLocationUpdates(provider, 0, 0, myLocationListener);
				if (myLocationManager != null)
				{
					location = myLocationManager.getLastKnownLocation(provider);
					return location;
				}
			}
			return null;
		}
	  
	  private void saveMyLocation(Location l)
	  {
		  String lat="0";
		  String lon="0";
		  String time="0";
		  
		  if(l != null)
		  {
			 //if location object is not null,
			 //then convert the latitude, longitude and timestamp into strings
			 //and save in the corresponding variables.
		  	lat=String.valueOf(l.getLatitude());
		  	lon=String.valueOf(l.getLongitude());	
		  	time=String.valueOf(l.getTime());
			  
			//Create a parseobject named MyLocation
		  	//store all these location details in it
			//and upload to the cloud.
			ParseObject myLocation = new ParseObject("MyLocation");
			myLocation.put("Latitude", lat);
			myLocation.put("Longitude", lon);
			myLocation.put("TimeStamp", time);
			myLocation.saveInBackground();		  	
		  }	
		  
		  // display latitude
		  TextView txtLat= (TextView) findViewById(R.id.Latitude2);
		  txtLat.setText(lat);
		  
		  // display longitude
		  TextView txtLon= (TextView) findViewById(R.id.Longitude2);
		  txtLon.setText(lon);
		  
		  // display timestamp
		  TextView txtTime= (TextView) findViewById(R.id.Time2);
		  txtTime.setText(time);			  
	  }  
	  
	  private final LocationListener myLocationListener = new LocationListener()
	  {
	      @Override
	      public void onLocationChanged(final Location location) {
	          saveMyLocation(location);
	      }
	      @Override
	      public void onProviderDisabled(String provider) {
	          // TODO Auto-generated method stub
	      }
	      @Override
	      public void onProviderEnabled(String provider) {
	          // TODO Auto-generated method stub
	      }
	      @Override
	      public void onStatusChanged(String provider, int status, Bundle extras) {
	          // TODO Auto-generated method stub
	      }
	  };	
	
}
