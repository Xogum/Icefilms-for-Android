package com.icefilms.info.prerelease;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

    public class ImageDownloader extends AsyncTask<ImageView,Void,Bitmap> {
    	
    	//private final RecentActivity mContext;
    	//private RecentAdapter mAdapter;
    	private ImageView mImgView;
    	
    	public ImageDownloader() {//RecentActivity c, ImageView v) {
    		//mContext = c;
    		//mImgView = v;
    	}
    	
    	protected Bitmap doInBackground(ImageView... views) {
    		for(ImageView view : views) {
    			mImgView = view;
    			String uri = (String)view.getTag();//getImageUri();
    			if(uri != null) {
    				Bitmap bmp = downloadBitmap(uri);
    				if(bmp != null && uri == ((String)mImgView.getTag()))
    					return bmp;
    					//vid.saveImage(bmp);
    			}
    		}
    		return null;
    	}
    	
    	//TODO: implement a cache to avoid additional data usage
		private Bitmap downloadBitmap(String url) {
		    final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
		    final HttpGet getRequest = new HttpGet(url);
	
		    try {
		        HttpResponse response = client.execute(getRequest);
		        final int statusCode = response.getStatusLine().getStatusCode();
		        if (statusCode != HttpStatus.SC_OK) {
		            Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);
		            return null;
		        }
		        
		        final HttpEntity entity = response.getEntity();
		        if (entity != null) {
		            InputStream inputStream = null;
		            try {
		                inputStream = entity.getContent();
		                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		                return bitmap;
		            } finally {
		                if (inputStream != null) {
		                    inputStream.close();
		                }
		                entity.consumeContent();
		            }
		        }
		    } catch (Exception e) {
		        // Could provide a more explicit error message for IOException or IllegalStateException
		        getRequest.abort();
		        Log.w("ImageDownloader", "Error while retrieving bitmap from " + url);
		    } finally {
		        if (client != null) {
		            client.close();
		        }
		    }
		    return null;
	    }
    	
		@Override
		protected void onProgressUpdate(Void... v) {
		}

		@Override
		protected void onPostExecute(Bitmap b) {
			if(mImgView != null && b != null)
				mImgView.setImageBitmap(b);
			//((RecentAdapter)mContext.getListAdapter()).notifyDataSetChanged();
		}
    }
