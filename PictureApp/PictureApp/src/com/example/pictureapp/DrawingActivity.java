package com.example.pictureapp;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class DrawingActivity extends Activity implements OnTouchListener
{
	//drawing path
	Path drawPath;
	//drawing and canvas paint
	Paint drawPaint, canvasPaint;
	//initial color
	int paintColor = Color.RED; //0xFF000000;//black color;
	//canvas
	Canvas drawCanvas;
	//canvas bitmap
	Bitmap canvasBitmap;
	ImageView myimageView;
	Button DrawingButton, ComentButton;	
	Bitmap myBitmap;
	int ClickedItemId=0;
	Boolean mBooleanIsPressed=false;
	String UserComments="";	
	final Context context = this;
	float touchX=0;
	float touchY=0;
			
	//Called when the activity is first created. //
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		
		    // TODO Auto-generated method stub	    
		    // Show the Up button in the action bar.
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        setContentView(R.layout.drawing_view);        
	        
	        LoadImage();	                     
	}
	
	public void LoadImage()
	{		
		 // get intent data
       Intent i = getIntent();         
       String imageDir = i.getStringExtra(MainActivity.File_Path);
		
       //get the width and height of the screen
       Display display = getWindowManager().getDefaultDisplay();
       Point size = new Point();
       display.getSize(size);
       int targetW = size.x;
       int targetH = size.y;
       //Toast.makeText(getApplicationContext(), targetW+"x"+targetH, Toast.LENGTH_LONG).show();
       Bitmap myBitmap1 = BitmapFactory.decodeFile(imageDir);
       //153, 204=original image size
       //768,1184=screen size
       //612,816
       //myBitmap = Bitmap.createScaledBitmap(myBitmap1, 459, 612, false);     
       myBitmap = Bitmap.createScaledBitmap(myBitmap1, targetW-156, targetH-368, false);
       if(myBitmap!=null)
       {  
    	   myimageView = (ImageView) findViewById(R.id.imageView1);
    	   myimageView.setImageBitmap(myBitmap); 
    	  setupCanvas();
       }             
       
	}
	
	//setup drawing
  	private void setupCanvas()
  	{  		
  		drawPath = new Path();
  		drawPaint = new Paint();
  		drawPaint.setColor(paintColor);
  		drawPaint.setAntiAlias(true);
  		drawPaint.setStrokeWidth(5);
  		drawPaint.setStyle(Paint.Style.STROKE);
  		drawPaint.setStrokeJoin(Paint.Join.ROUND);
  		drawPaint.setStrokeCap(Paint.Cap.ROUND);  		
  		canvasPaint = new Paint(Paint.DITHER_FLAG);  			
  		
  		//Create a new image bitmap
        canvasBitmap=Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getWidth(), Bitmap.Config.ARGB_8888);
        
        //attach a brand new canvas to it
        drawCanvas = new Canvas(canvasBitmap);
        
        //Draw the image bitmap into the canvas
        drawCanvas.drawBitmap(myBitmap, 0, 0, canvasPaint);  
        
       //myimageView.setImageBitmap(canvasBitmap);
       myimageView.setImageDrawable(new BitmapDrawable(getResources(), canvasBitmap));
       myimageView.setOnTouchListener(this);  		
  	} 
  	
  	private final Handler handler = new Handler();
  	private final Runnable runnable = new Runnable() 
  	{
  	    public void run() 
  	    {
  	        //create and show custom dialog box
  	    	ShowDialogBox();
  	    }
  	};
  	
  	public void ShowDialogBox()
  	{ 
  		if(mBooleanIsPressed)
	  	{  			
  			// get dialog view
			LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.comment_dialog, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

			// set dialog to alertdialog builder
			alertDialogBuilder.setView(promptsView);

			final EditText txtComments = (EditText) promptsView.findViewById(R.id.editText1);
			
			// set dialog message
			alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					// get user input and set it to result
					// edit text
				    	UserComments=txtComments.getText().toString();
				    	Paint textPaint = new Paint();
				    	textPaint.setColor(Color.BLUE);
				    	textPaint.setTextSize(36);
				    	textPaint.setLetterSpacing((float) 0.05);
				    	textPaint.setAntiAlias(true);
				    	textPaint.setStrokeWidth(2);
				    	textPaint.setStyle(Paint.Style.STROKE);
				  		//drawPaint.setStrokeJoin(Paint.Join.ROUND);
				  		//drawPaint.setStrokeCap(Paint.Cap.ROUND);
				    	drawCanvas.drawText(UserComments, touchX, touchY, textPaint);
				    	dialog.dismiss();
				    }
				  })
				.setNegativeButton("CANCEL",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				    }
				  });

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
			
	  	}
  	}
  	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		//detect user touch   view.getLeft()  
		touchX = event.getX();
		touchY = event.getY();
		//respond to down, move and up events
		switch (action) 
		{
		case MotionEvent.ACTION_DOWN:			
			drawPath.moveTo(touchX, touchY);
			// Execute your Runnable after 1500 milliseconds = 1.5 seconds.
	       handler.postDelayed(runnable, 1500);
	       mBooleanIsPressed = true;
			break;
		case MotionEvent.ACTION_MOVE:			
			drawPath.lineTo(touchX, touchY);
			break;			
		case MotionEvent.ACTION_UP:
			drawPath.lineTo(touchX, touchY);
			drawCanvas.drawPath(drawPath, drawPaint);
			drawPath.reset();
			
			if(mBooleanIsPressed) 
			{
	            mBooleanIsPressed = false;
	            handler.removeCallbacks(runnable);
	        }					
			
			break;
		default:
			return false;
		}
		//redraw		
		myimageView.invalidate();		
		return true;
	}	

}
