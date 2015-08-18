package com.example.therosterapp;

import com.example.therosterapp.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.therosterapp.widgets.lib.control.SliderControl;

public class MainActivity extends Activity implements OnItemSelectedListener, OnSeekBarChangeListener 
{
	private String[] eyeColor = {"Brown", "Black", "Green","Blue" }; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadSelections();       
    }

    public void LoadSelections()
    { 
    	//initialize sharedpreferences lib
        SharedPreferences prefs = getSharedPreferences("RosterPreferences", MODE_PRIVATE);         
        
        //get the textbox value
        String name=prefs.getString("Name", "");
        EditText txt1=(EditText)findViewById(R.id.txtName);
        txt1.setText(name);
        
        //get the checkbox value
        Boolean checkBoxValue=prefs.getBoolean("isChecked",false);
        CheckBox Chk1=(CheckBox) findViewById(R.id.chkbox);
        
        if(checkBoxValue)
        {
        	Chk1.setChecked(true);
        }
        else
        {
        	Chk1.setChecked(false);
        }
        
        
        //populate the dropdown list
	    Spinner spinnerEyeColor = (Spinner)findViewById(R.id.ddlEyeColor);
	    ArrayAdapter<String> adapter_color = new ArrayAdapter<String>(this,
	                  android.R.layout.simple_spinner_item, eyeColor);
	    adapter_color.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinnerEyeColor.setAdapter(adapter_color);
	    spinnerEyeColor.setOnItemSelectedListener(this); 	
	    
	    
	    //set the dropdown to selected value
	    int position=prefs.getInt("Position", 0);
	    spinnerEyeColor.setSelection(position);
	     
	   
	    // set current date into Date Picker
	    DatePicker date = (DatePicker) findViewById(R.id.date_picker);
	    int year=prefs.getInt("Year", 0);
	    int month=prefs.getInt("Month", 0);
	    int day=prefs.getInt("Day", 0);
	    date.init(year, month, day, null);	    
	    
	    //get the selected radio button identifier	    
	    int selectedId=prefs.getInt("Size", 0);	
	    if(selectedId==0)
	    {
	    	//by default, the first radio button in the group should be checked.
	    	RadioGroup SizeGroup=(RadioGroup)findViewById(R.id.SizeGroup);
	    	int selId = SizeGroup.getCheckedRadioButtonId();
	    	RadioButton SizeButton = (RadioButton) findViewById(selId);
	    	SizeButton.setChecked(true);
	    }
	    else
	    {
	    	RadioButton SizeButton = (RadioButton) findViewById(selectedId);
			SizeButton.setChecked(true);
	    }	
		
		//get pant size
		int pantsize=prefs.getInt("PantSize",0);
		TextView DisplayPantSize = (TextView)findViewById(R.id.displayPantSize);
		DisplayPantSize.setText(Integer.toString(pantsize));	
		
		SeekBar seekPant = (SeekBar)findViewById(R.id.seekPant);
		seekPant.setProgress(pantsize/2);   
		
		//get shirt size
		int shirtsize=prefs.getInt("ShirtSize",0);
		TextView DisplayShirtSize = (TextView)findViewById(R.id.displayShirtSize);
		DisplayShirtSize.setText(Integer.toString(shirtsize));
		
		SeekBar seekShirt = (SeekBar)findViewById(R.id.seekShirt);
		seekShirt.setProgress(shirtsize); 
		
		//get shoe size
		int shoesize=prefs.getInt("ShoeSize", 4);
		TextView DisplayShoeSize = (TextView)findViewById(R.id.displayShoeSize);
		DisplayShoeSize.setText(Integer.toString(shoesize));
				
		SeekBar seekShoe = (SeekBar)findViewById(R.id.seekShoe);
		seekShoe.setProgress(shoesize-4); 

		seekPant.setOnSeekBarChangeListener(this);	
		seekShirt.setOnSeekBarChangeListener(this);	
		seekShoe.setOnSeekBarChangeListener(this);	
		
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void Save(View view)
    {    	
    	SharedPreferences prefs = getSharedPreferences("RosterPreferences", MODE_PRIVATE);
    	SharedPreferences.Editor prefsEditor = prefs.edit();
    	
    	//save the textbox value
    	EditText txt1 = (EditText)findViewById(R.id.txtName);
    	String name = txt1.getText().toString();    	
    	prefsEditor.putString("Name", name);
    	
    	//save the checkbox value
    	CheckBox Chk1=(CheckBox) findViewById(R.id.chkbox);
        Boolean checkBoxValue = Chk1.isChecked();
        prefsEditor.putBoolean("isChecked", checkBoxValue); 
        
        
        //save the dropdown value
        Spinner spinnerEyeColor = (Spinner)findViewById(R.id.ddlEyeColor);
    	prefsEditor.putInt("Position",spinnerEyeColor.getSelectedItemPosition());
    	
    	//save the date value
    	DatePicker date = (DatePicker) findViewById(R.id.date_picker);
    	prefsEditor.putInt("Year", date.getYear());
    	prefsEditor.putInt("Month", date.getMonth());
    	prefsEditor.putInt("Day", date.getDayOfMonth());  
    	
    	//save the selected radio button value
    	RadioGroup SizeGroup=(RadioGroup)findViewById(R.id.SizeGroup);
    	int selectedId = SizeGroup.getCheckedRadioButtonId();
    	prefsEditor.putInt("Size", selectedId);

    	//save the pant size    	
    	SeekBar seekpant=(SeekBar)findViewById(R.id.seekPant);
    	int pantsize=seekpant.getProgress();
    	prefsEditor.putInt("PantSize", pantsize*2);
    	
    	//save the shirt size
    	SeekBar seekshirt=(SeekBar)findViewById(R.id.seekShirt);
    	int shirtsize=seekshirt.getProgress();
    	prefsEditor.putInt("ShirtSize", shirtsize);
    	    	
    	//save the shoe size
    	SeekBar seekshoe=(SeekBar)findViewById(R.id.seekShoe);
    	int shoesize=seekshoe.getProgress();
    	prefsEditor.putInt("ShoeSize", shoesize+4);
    	
    	prefsEditor.commit();  
	    
    	//show user preferences stored successfully pop up message
    	Toast.makeText(getBaseContext(), "Saved your preferences.", Toast.LENGTH_SHORT).show();   		    
    
    }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Spinner spinnerEyeColor = (Spinner)findViewById(R.id.ddlEyeColor);
		spinnerEyeColor.setSelection(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
		if(seekBar.getId()==R.id.seekPant)
		{	
			TextView DisplayPantSize = (TextView)findViewById(R.id.displayPantSize);
			int size=progress*2;
			//String pantsize = Integer.toString(size);
			String pantsize = String.valueOf(size);
			DisplayPantSize.setText(pantsize);
		}
		
		if(seekBar.getId()==R.id.seekShirt)
		{	
			TextView DisplayShirtSize = (TextView)findViewById(R.id.displayShirtSize);					
			DisplayShirtSize.setText(Integer.toString(progress));
		}
		
		if(seekBar.getId()==R.id.seekShoe)
		{	
			TextView DisplayShoeSize = (TextView)findViewById(R.id.displayShoeSize);
			int size=progress+4;
			//String shoesize = Integer.toString(size);
			String shoesize = String.valueOf(size);
			DisplayShoeSize.setText(shoesize);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
		
	}
    
}
