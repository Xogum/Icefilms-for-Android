package com.icefilms.info.prerelease;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.icefilms.info.prerelease.R;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

    public class SideScraper extends AsyncTask<String,Void,ArrayList<String>> {
    	
    	private final ListActivity mContext;
    	
    	public SideScraper(ListActivity c) {
    		mContext = c;
    	}
    	
    	protected ArrayList<String> doInBackground(String... s) {
    		try {
	    		if(s.length > 0) {
		            String html = connect(s[0]);
		            if(html != null) {
			            String latestReleases = html.substring(html.indexOf("<span",html.indexOf("<h1>Latest Releases</h1>")+24),html.indexOf("<h1>Being Watched Now</h1>"));
			            ArrayList<String> titles = findTitles(latestReleases);
						return titles;
		            }
	    		}
    		} catch(Exception e) {
    		}
			return null;
    	}
    	
		/*@Override
		protected void onProgressUpdate(Void... item) {
		}*/

		@Override
		protected void onPostExecute(ArrayList<String> titles) {
			if(titles == null) {
				//TextView empty1 = (TextView) 
				mContext.findViewById(R.id.side_empty1).setVisibility(View.GONE);
				mContext.findViewById(R.id.side_empty2).setVisibility(View.VISIBLE);
			} else if(titles.size() == 0) {
				
			}
			else {
		        SideAdapter adapter = new SideAdapter(mContext, titles);
		        mContext.setListAdapter(adapter);
		        mContext.onContentChanged();
			}
		}
		
	    private String connect(String url) {
	        HttpClient httpclient = new DefaultHttpClient();

	        // Prepare a request object
	        HttpGet httpget = new HttpGet(url);

	        // Execute the request
	        HttpResponse response;
	        try {
	            response = httpclient.execute(httpget);
	            // Examine the response status
	            Log.i("Praeda",response.getStatusLine().toString());

	            // Get hold of the response entity
	            HttpEntity entity = response.getEntity();
	            // If the response does not enclose an entity, there is no need
	            // to worry about connection release

	            if (entity != null) {

	                // A Simple JSON Response Read
	                InputStream instream = entity.getContent();
	                String result= convertStreamToString(instream);
	                // now you have the string representation of the HTML request
	                instream.close();
	                return result;
	            }
	            


	        } catch (Exception e) {
	        	//view.append("Exception: " + e.toString());
	        }
	        return null;
	    }
	    
	    private String convertStreamToString(InputStream is) {
	        /*
	         * To convert the InputStream to String we use the BufferedReader.readLine()
	         * method. We iterate until the BufferedReader return null which means
	         * there's no more data to read. Each line will appended to a StringBuilder
	         * and returned as String.
	         */
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();

	        String line = null;
	        try {
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return sb.toString();
	    }
	    
	    private ArrayList<String> findTitles(String releases) {
	    	
	    	ArrayList<String> list = new ArrayList<String>();
	    	int index = releases.indexOf("<li>")+1;
	    	int i = releases.indexOf("<br>");
	    	i = releases.lastIndexOf('>',i)+1;
	    	int insertIndex = 0;
	    	String date = releases.substring(i,releases.indexOf('<',i));
	    	
	    	while(index > 0 && releases.indexOf("<a href=",index) >= 0) {
	    		int j = releases.indexOf("<a href=",index);
	    		j = releases.indexOf(">",j)+1;
	    		String title = releases.substring(j,releases.indexOf("</a>",j));
	    		j = releases.indexOf("<a href=",index) + 8;
	    		String temp = releases.substring(j, releases.indexOf(">",j));
	    		if(!temp.startsWith("/"))
	    			temp = "/" + temp;
	    		String link = "http://www.icefilms.info" + temp;
	    		list.add(insertIndex,title + "!?!" + link);
	    		
	    		int newIndex = releases.indexOf("<li>",index)+1;
	    		if(newIndex > 0 && releases.indexOf("<br>",index) != releases.lastIndexOf("<br>",newIndex)) {
	    			list.add(insertIndex, "*" + date);
	    			date = releases.substring(releases.indexOf("<br>",index)+4,releases.lastIndexOf("<br>",newIndex));
	    			insertIndex = list.size();
	    		}
	    		index = newIndex;
	    	}
	    	
			list.add(insertIndex, "*" + date);
	    	return list;
	    }
    }
