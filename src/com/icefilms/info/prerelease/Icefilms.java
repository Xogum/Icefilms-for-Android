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

public class Icefilms extends ListActivity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_list);
        new SideScraper(this).execute("http://icefilms.info");
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
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_recent:
                return true;
            case R.id.menu_search:
                return true;
            case R.id.menu_favorites:
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
}
