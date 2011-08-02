package com.icefilms.info.prerelease;



public class RecentDate extends RecentItem {
	
	//private GregorianCalendar mDate;
	private String mDate;
	
	public RecentDate(String date)
	{
		super(R.layout.recent_date);
		mDate = date;
		//mDate = new GregorianCalendar(2011,3,3);
	}
	
	public String getDate() {
		// ((RecentDate)item).getDate() );
		return mDate;
	}
	/*
	public Date getTime() {
		return mDate.getTime();
	}*/
}