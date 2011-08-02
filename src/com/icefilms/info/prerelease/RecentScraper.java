package com.icefilms.info.prerelease;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.view.View;

    public class RecentScraper extends AsyncTask<Void,RecentItem,Integer> {
    	
    	private final RecentActivity mContext;
    	private RecentAdapter mAdapter;
    	
    	public RecentScraper(RecentActivity c) {
    		mContext = c;
    		mAdapter = (RecentAdapter) c.getListAdapter();
    		if(mAdapter == null) {
    			mAdapter = new RecentAdapter(mContext);
    			c.setListAdapter(mAdapter);
    		}
    	}
    	
    	protected Integer doInBackground(Void... item) {
    		/*if(!mAdapter.isEmpty()) {
    			mContext.findViewById(R.id.recent_empty1).setVisibility(View.VISIBLE);			
    			mContext.findViewById(R.id.recent_empty2).setVisibility(View.GONE);
    			mAdapter.clear();
    			//mAdapter.notifyDataSetChanged();
    		}*/
    		try {
    			//String html2 = connect("http://www.icefilms.info");
    			String html = query("http://www.icefilms.info");
	            if(html != null) {
	            	//TODO: Re-write entire web scraping section
		            //String latestReleases = html2.substring(html2.indexOf("<span",html2.indexOf("<h1>Latest Releases</h1>")+24),html2.indexOf("<h1>Being Watched Now</h1>"));
		            //ArrayList<String> list = findTitles(latestReleases);
	            	int start = html.indexOf("<div id=\"fp_articles\"");
	            	int index = start+1;
	            	int divs = 1;
	            	int divUp,divDown;
	            	
	            	while(divs > 0)
	            	{
	            		divUp = html.indexOf("<div", index);
	            		divDown = html.indexOf("</div", index);
	            		if( divUp < divDown ) {
	            			divs++;
		            		index = html.indexOf("<div", index) + 5;
	            		}
	            		else {
	            			divs--;
		            		index = html.indexOf("</div", index) + 6;
	            		}
	            	}
	            	
	    			String articles = html.substring(start, index);
	    			index = 0;
	    			
	    			//String date = "Unknown Date";
	    			String article;
	    			int link = -1, h1 = -1;
	    			//publishProgress(new RecentVideo("http://icefilms.info",articles,"http://img109.imageshack.us/img109/4581/baccno.png"));
		            while((link = articles.indexOf("<a href=\"/ip", index)) != -1) {
		            	
		            	h1 = articles.lastIndexOf("<h1", link);
		            	if(h1 > index)
		            		publishProgress( new RecentDate( articles.substring(h1+4, articles.indexOf("</h1>", h1)) ) );
		            		//date = articles.substring(h1+4, articles.indexOf("</h1>", h1));
		            	
		            	//String url = "http://icefilms.info" + articles.substring( link+9, articles.indexOf("\"", link+9));
		            	article = articles.substring( articles.lastIndexOf("<p",link) , articles.indexOf("</p",link));
		            	publishProgress(new RecentVideo(article));
		            	
		            	index = articles.indexOf("</p",link);
		            	
		    			//if(str.startsWith("*")) {
		    			//	publishProgress(new RecentDate(str.substring(1)));
		    			//} else {
		    			//	publishProgress(new RecentVideo(str));
		    			//}
		            	
						//publishProgress(item);
		            }
	            	return Integer.valueOf(R.id.recent_empty1);
	            }
    		} catch(Exception e) {
    		}
			return Integer.valueOf(R.id.recent_empty2);	//TODO: Implement exception catching and return proper error message values
    	}
    	
		@Override
		protected void onProgressUpdate(RecentItem... items) {
			for(RecentItem item : items)
			{
				mAdapter.add(item);
			}
			//mAdapter.notifyDataSetChanged();
		}

		@Override
		protected void onPostExecute(Integer error) {
			//TODO: Add remaining empty views
			mContext.findViewById(R.id.recent_empty1).setVisibility(View.GONE);
			mContext.findViewById(R.id.recent_empty2).setVisibility(View.GONE);
			mContext.findViewById(error.intValue()).setVisibility(View.VISIBLE);			
		}
		
		private String query(String url) {
	        HttpClient httpClient = new DefaultHttpClient();
	        HttpGet httpReq = new HttpGet(url);
	        HttpResponse httpResponse;
	        try {
	            httpResponse = httpClient.execute(httpReq);
	            HttpEntity entity = httpResponse.getEntity();

	            if (entity != null) {
	                InputStream instream = entity.getContent();
	    	        BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
	    	        
	    	        StringBuilder sb = new StringBuilder();
	    	        String line = reader.readLine();
    	            while (line != null) {
    	                sb.append(line + "\n");
    	                line = reader.readLine();
    	            }
    	            
	    	        instream.close();
	                return sb.toString();
	            }
	        } catch (Exception e) {
	        }
	        return null;
		}
		
	    /*private String connect(String url) {
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
	         *//*
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
	    		String link = "http://icefilms.info" + releases.substring(j, releases.indexOf(">",j));
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
	    }*/
    }
