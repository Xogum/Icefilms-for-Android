package com.icefilms.info.prerelease;

import java.util.ArrayList;

import com.icefilms.info.prerelease.R;

import android.app.ListActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public final class SideAdapter extends ArrayAdapter<String> {
	
	private final ListActivity mContext;
	
	public SideAdapter(ListActivity context, ArrayList<String> titles) {
		super(context, R.layout.side_item, R.id.side_name, titles);
		mContext = context;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		String item = (String) getItem(position);
		View rowView = convertView;
		if(rowView == null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			rowView = inflater.inflate(R.layout.side_item, null, true);
		}
		/*if(item == "ADVERT") {
			View adView = inflater.inflate(R.layout.side_ad, null, true);
			adView.setTag("ADVERT");
			return adView;
		}*/
		TextView textView = (TextView) rowView.findViewById(R.id.side_name);
		TextView dateView = (TextView) rowView.findViewById(R.id.side_date);
		//if(item.lastIndexOf('*') < 0) {
		//	textView.setText(item);
		//}
		//else {
			if(item.startsWith("*")) {
				textView.setVisibility(View.GONE);
				dateView.setText(item.substring(1));
				dateView.setVisibility(View.VISIBLE);//setText(item.substring(item.lastIndexOf("*")+1,item.lastIndexOf("#")));
				rowView.setTag(null);
			} else {
				textView.setText(item.substring(0,item.indexOf("!?!")-7));//item.lastIndexOf("*")));
				textView.setVisibility(View.VISIBLE);
				dateView.setVisibility(View.GONE);//setText(item.substring(item.lastIndexOf("*")+1,item.lastIndexOf("#")));
				rowView.setTag(item.substring(item.indexOf("!?!")+3));
			}
		//}
	
		return rowView;
	}
	@Override 
	public boolean isEnabled(int position) 
	{ 
		String item = (String) getItem(position);
		if(item == "ADVERT") {
			return false;
		} else {
			return true;
		}
	}
}
