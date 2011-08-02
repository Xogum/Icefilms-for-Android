package com.icefilms.info.prerelease;

import android.graphics.Bitmap;

public class RecentVideo extends RecentItem {
	
	private String uri, title, imageUri;
	private Bitmap mImage;
	
	public RecentVideo(String html)
	{
		super(R.layout.recent_vid);

		int a = html.indexOf("<a href=")+9;
		uri = html.substring( a , html.indexOf("\"",a) );
		
		a = html.indexOf("<img");
		int a2 = html.indexOf("src=",a)+5;
		imageUri = html.substring( a2 , html.indexOf("\"",a2) );
		a2 = html.indexOf("alt=",a)+5;
		if(a2 > 4)
			title = html.substring( a2 , html.indexOf("\"",a2) );
	}
	
	public RecentVideo(String u, String t, String i)
	{
		super(R.layout.recent_vid);

		title = t;
		uri = u;
		imageUri = i;
	}
	
	public String getUri() {
		return uri;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getImageUri() {
		return imageUri;
	}
	
	public Bitmap getImage() {
		return mImage;
	}
	
	public void saveImage(Bitmap img) {
		if(mImage != null && !mImage.isRecycled())
			mImage.recycle();
		mImage = img;
	}
}