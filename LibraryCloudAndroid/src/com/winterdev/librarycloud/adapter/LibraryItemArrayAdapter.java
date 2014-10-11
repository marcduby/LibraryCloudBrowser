package com.winterdev.librarycloud.adapter;

import java.util.List;

import com.winterdev.librarycloud.R;
import com.winterdev.librarycloud.domain.LibraryItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LibraryItemArrayAdapter extends ArrayAdapter<LibraryItem> {

	/**
	 * custom constructor
	 * 
	 * @param context
	 * @param resource
	 * @param itemList
	 */
	public LibraryItemArrayAdapter(Context context, int resource, List<LibraryItem> itemList) {
		super(context, resource, itemList);
	}
	
	/**
	 * inflate the view
	 * 
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// get the item at the position
		LibraryItem item = this.getItem(position);
		
		// inflate the layout
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.library_item_list, null);
		}
		
		// set the item title
		TextView titleView = (TextView)convertView.findViewById(R.id.item_title);
		titleView.setText(item.getTitle() != null ? item.getTitle() : "");
		
		// set the item author
		TextView authorView = (TextView)convertView.findViewById(R.id.item_author);
		authorView.setText(item.getAuthorNameString() != null ? item.getAuthorNameString() : "");
		
		// return the view
		return convertView;
	}
}
