package com.icefilms.info.prerelease;

import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public final class RecentAdapter extends ArrayAdapter<RecentItem> {
	
	private final RecentActivity mContext;
	
	public RecentAdapter(RecentActivity context) {
		super( context, R.layout.recent_vid, R.id.recent_vidTitle );
		mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RecentItem item = (RecentItem) getItem(position);
		View rowView = convertView;
		if(rowView == null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			rowView = inflater.inflate(item.getType(), null, true);
		}
		//else if(item.getType() == R.layout.recent_vid)
			//((RecentVideo)rowView.getTag()).saveImage(null);
		
		switch(item.getType()) {
		
		case R.layout.recent_vid:
			TextView textView = (TextView) rowView.findViewById( R.id.recent_vidTitle );
			ImageView imgView = (ImageView) rowView.findViewById( R.id.recent_vidImage );
			imgView.setTag(((RecentVideo)item).getImageUri());
			//imgView.setTag(((RecentVideo)item).getImageUri());
			//(new ImageDownloader()).execute(imgView);
			
			if( mContext.isImageEnabled() ) {
				textView.setGravity( Gravity.CENTER );
				imgView.setImageURI(Uri.parse(((RecentVideo)item).getImageUri()));
				imgView.setVisibility( View.VISIBLE );
				//Bitmap img = ((RecentVideo)item).getImage();
				//if(img != null && !img.isRecycled())
					//imgView.setImageBitmap(img);
				//else {
					//new ImageDownloader().execute(imgView);//(RecentVideo)item);
			        //ProgressBar spinner = new ProgressBar(mContext);
			        //spinner.setIndeterminate(true);
		            //if (spinner.getIndeterminateDrawable() instanceof AnimationDrawable) {
		                //((AnimationDrawable) spinner.getIndeterminateDrawable()).start();
		            //}
		            //imgView.setImageDrawable(spinner.getIndeterminateDrawable());
				//}
			} else {
				textView.setGravity( Gravity.LEFT );
				imgView.setVisibility( View.GONE );
			}

			textView.setText( ((RecentVideo)item).getTitle() );
				
			rowView.setTag(item);
			break;
			
		case R.layout.recent_date:
			TextView dateView = (TextView) rowView.findViewById( R.id.recent_date );
			//String date = DateFormat.getDateInstance().format( ((RecentDate)item).getDate() );
			String date = ((RecentDate)item).getDate();//DateFormat.getMediumDateFormat(mContext).format( ((RecentDate)item).getTime() );
			dateView.setText( date );
			rowView.setTag(null);
			break;
		}
		return rowView;
	}
	
	@Override 
	public boolean isEnabled(int position) 
	{ 
		if( getItem( position ).getType() == R.layout.recent_vid )
			return true;
		else
			return false;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	@Override
	public int getItemViewType(int position) {
		int type = getItem( position ).getType();
		if(type == R.layout.recent_date)
			return 0;
		else
			return 1;
	}
}