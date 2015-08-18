package com.parse.starter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;


import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseApplication extends Application 
{ 
	 
	 //Timer timer;
	 //TimerTask task;
	 
  @Override
  public void onCreate() 
  {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    String App_Id="YoqCAzZdCM3mLjPdcfBA32ZIlMqEJJ8jkmNFHCYa";
    String Client_Key="qRkySLLp6txa0kHzHpYOxxmnDp2X1rWnd4N2Vxt5";
    
    Parse.initialize(this, App_Id, Client_Key);
   // Parse.initialize(this);

    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    
    // Optionally enable public read access.
    defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
   
  }
  
}
