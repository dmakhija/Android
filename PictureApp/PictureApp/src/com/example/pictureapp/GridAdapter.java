package com.example.pictureapp;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter
{

	private Context context;
	
	// Keep all Images in array
    public ArrayList<File> ImageList;

	public GridAdapter(Context c) 
    {
        context = c;
        ImageList=MainActivity.list;
    }
	
	 //---returns the number of images---
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ImageList.size();
	}

	//---returns the ID of an item--- 
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ImageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	//---returns an ImageView view---
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub	
		ImageView imageView;
		if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);
        } 
		else 
        {
            imageView = (ImageView) convertView;
        }            
		imageView.setImageURI(Uri.parse(getItem(position).toString()));
        return imageView;
	}

}
