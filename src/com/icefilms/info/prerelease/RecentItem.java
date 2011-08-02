package com.icefilms.info.prerelease;



public class RecentItem {
	private int mType;
	
	protected RecentItem(int type)
	{
		mType = type;
	}
	
	public int getType() {
		return mType;
	}
}