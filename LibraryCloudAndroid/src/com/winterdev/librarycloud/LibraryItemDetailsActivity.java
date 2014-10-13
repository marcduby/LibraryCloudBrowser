package com.winterdev.librarycloud;

import com.winterdev.librarycloud.domain.LibraryItem;
import com.winterdev.librarycloud.service.LibraryJsonService;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class LibraryItemDetailsActivity extends Activity {
	// instance variables
	LibraryJsonService libraryJsonService = LibraryJsonService.getLibraryJsonService();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_item_details);
        
        // get the data sent from the calling activity
        int itemIndex = this.getIntent().getExtras().getInt(LibraryItem.SESSION_ITEM_LIST_INDEX);
        this.displayItemAtPosition(itemIndex);
    }
    
    /**
     * display the item data in the view
     * 
     * @param position
     */
    protected void displayItemAtPosition(int position) {
    	// get the library item
    	LibraryItem item = this.libraryJsonService.getLastSearchListItem(position);
    	
    	if (item != null) {
        	// set the data on the text views
    		TextView titleView = (TextView)this.findViewById(R.id.item_title_text);
    		titleView.setText(item.getTitle() != null ? item.getTitle() : "");
    		
        	// set the data on the text views
    		TextView authorView = (TextView)this.findViewById(R.id.item_author_text);
    		authorView.setText(item.getAuthorNameString() != null ? item.getAuthorNameString() : "");
    		
        	// set the data on the text views
    		TextView descriptionView = (TextView)this.findViewById(R.id.item_description_text);
    		descriptionView.setText(item.getSubTitle() != null ? item.getSubTitle() : "");
    		
        	// set the data on the locations text view
    		TextView locationsView = (TextView)this.findViewById(R.id.item_locations_text);
    		locationsView.setText(item.getLocationsFormattedString() != null ? item.getLocationsFormattedString() : "");
    		
    	} else {
    		Log.i(this.getClass().getName(), "Got incorrect item list position: " + position);
    	}
    	
    }
}
