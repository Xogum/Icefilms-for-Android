package com.icefilms.info.prerelease;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class SideActivity extends ListActivity {
	private final String AD_DEV_ID = "623AEAFB9D5601E0FEEC58AFE8E64A59";
	
	//AdRequest mRequest;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_list);
        refresh();
        //AdView adView = (AdView) findViewById(R.id.side_adView);//new AdView(this, AdSize.BANNER, AD_PUB_ID);
        //adView.setAdListener(this);
        //adView.loadAd(mRequest);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
	    super.onListItemClick(l, v, position, id);
    	String uri = (String)(v.getTag());
    	if(uri != null) {
		    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		    startActivity(browserIntent);
    	}
    }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    
   @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_favorites).setVisible(true);
        menu.findItem(R.id.menu_img).setVisible(true);
        menu.findItem(R.id.menu_recent).setVisible(false);
        menu.findItem(R.id.menu_refresh).setVisible(true);
        menu.findItem(R.id.menu_search).setVisible(true);
        menu.findItem(R.id.menu_txt).setVisible(false);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
        case R.id.menu_recent:
            return true;
        case R.id.menu_search:
            return true;
        case R.id.menu_favorites:
            return true;
        case R.id.menu_img:
            Intent i = new Intent(this, RecentActivity.class);
            //i.putExtra(NotesDbAdapter.KEY_ROWID, id);
            startActivityForResult(i, 0);
            return true;
        case R.id.menu_txt:
            return true;
        case R.id.menu_refresh:
        	refresh();
            return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
    
/*    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, EDIT_ID, 1, R.string.menu_edit);
        menu.add(0, DUPL_ID, 2, R.string.menu_dupl);
        menu.add(0, DELETE_ID, 3, R.string.menu_delete);
    }

   @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
        case EDIT_ID:
            editNote(info.id);
            return true;
        case DUPL_ID:
            duplNote(info.id);
            return true;
        case DELETE_ID:
            //AdapterContextMenuInfo info2 = (AdapterContextMenuInfo) item.getMenuInfo();
            mDbHelper.deleteNote(info.id);
            fillData();
            return true;
        }
        return super.onContextItemSelected(item);
    }*/
    
    private void refresh() {
        new SideScraper(this).execute("http://www.icefilms.info");
        AdRequest mRequest = new AdRequest();
        mRequest.addTestDevice(AD_DEV_ID);
        mRequest.addKeyword("television");
        AdView adView = (AdView) findViewById(R.id.side_adView);
        adView.loadAd(mRequest);
    }
}
