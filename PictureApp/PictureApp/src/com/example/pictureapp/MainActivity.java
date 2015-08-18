package com.example.pictureapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.pictureapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final int REQUEST_IMAGE_CAPTURE = 1;
	File storageDir;
	GridView gv;
	public static ArrayList<File> list;
	ListAdapter gAdapter;
	public final static String File_Path = "com.example.pictureapp.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 
		
		//Storage directory
        storageDir = new File(Environment.getExternalStorageDirectory() + "/PictureApp");
        storageDir.mkdirs();  
        list=imageReader(storageDir);
        SetGallery();		
	}
	
	public void SetGallery()
	{
		//list=imageReader(storageDir);
		
		gv=(GridView) findViewById(R.id.gridView1);
		gAdapter=new GridAdapter(this);
		gv.setAdapter(gAdapter);		
		gv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parentView, View iv, int position, long id)
			{				
				// Sending image id to FullScreenActivity
								
				Intent intent = new Intent(MainActivity.this, DrawingActivity.class);
				String path= gAdapter.getItem(position).toString();
				intent.putExtra(File_Path, path);
		        startActivity(intent);					
			}
		});
		
	}
	
    public ArrayList<File> imageReader(File root)
	{
		ArrayList<File> a=new ArrayList<File>();
		
		File[] files=root.listFiles();
		for(int i=0; i<files.length; i++)
		{
			if(files[i].isDirectory())
			{
				//it is a directory, not a file
			}
			else
			{
				//it is a file, check the extension
				if(files[i].getName().endsWith(".jpg"))
				{
					//this is a valid jpeg file, add it to the displaylist
					a.add(files[i]);
				}
			}	
		}		
		return a;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater= getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;		
	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if (id == R.id.action_camera)
		{
			LaunchCamera();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}	
	
	public void LaunchCamera()
    {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);	
    } 
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) 
	    {
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data"); 
	        
	        // Create an image file name
		    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		    String imageFileName = "JPEG_" + timeStamp + ".jpg";
		    
		    File imageFile=null;
		    try 
		    {
		    	imageFile = new File(storageDir, imageFileName);
				// Continue only if the File was successfully created
		        if (imageFile != null)
		        {					
					FileOutputStream out;
					try 
					{
						out = new FileOutputStream(imageFile);
						//imageBitmap.getWidth();
						//imageBitmap.getHeight();
						//Toast.makeText(getApplicationContext(), imageBitmap.getWidth()+"x"+imageBitmap.getHeight(), Toast.LENGTH_LONG).show();
						imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
						out.flush();
						out.close();
						list=imageReader(storageDir);
						SetGallery();
					} 
					catch (FileNotFoundException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
					
		        }		        
			} 
		    catch (IOException e) 
		    {
				// TODO Auto-generated catch block
				e.printStackTrace();		    	
			} 
	    }
	}
	
}
