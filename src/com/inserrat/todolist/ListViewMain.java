package com.inserrat.todolist;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ListViewMain extends Activity {
	
	ArrayList<String> todoItems;
	ArrayAdapter<String> aa;
	EditText myEditText;
	ListView myListView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	//Intent MainIntent = 
    	//	new Intent(ToDoList.this, ListViewMain.class);
    	//ToDoList.this.startActivity(MainIntent);
    	    	
    	// Inflate your view
    	setContentView(R.layout.list_view_main);
    	
    	// Get reference to UI widgets
    	myListView = (ListView)findViewById(R.id.myListView);
    	myEditText = (EditText)findViewById(R.id.myEditText);

    	// Create the array list of to do items
    	todoItems = new ArrayList<String>();
    	// Create the array adapter to bind the array to the listview
    	
    	aa = new ArrayAdapter<String>(this,
    			android.R.layout.simple_list_item_1,
    			todoItems);
    	// Bind the array adapters to the listview
    	myListView.setAdapter(aa);
    	
    	myEditText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
						keyCode == KeyEvent.KEYCODE_ENTER)
				{
					String myEditTextString = myEditText.getText().toString();
					addTodoItem(myEditTextString);
					return true;
				}
				else if(keyCode == KeyEvent.KEYCODE_BACK
						&& myEditText.getText().length() > 0)
				{
					myEditText.setText("");
					return true;
				}
				return false;
			}
    		
    	});
    	
    	// register contextual menu with list view items
    	registerForContextMenu(myListView);
    	
    	// example of creating intent and starting intent activity
    	Intent TextInputOverlayIntent = 
    		new Intent(ListViewMain.this, TextInputOverlay.class);
    	ListViewMain.this.startActivity(TextInputOverlayIntent);

    }
    
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.context_menu, menu);
    }
    
    public boolean onContextItemSelected(MenuItem item)
    {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	int idInt = new Long(info.id).intValue();
    	
    	switch(item.getItemId())
    	{
    		case R.id.delete_item:
    			deleteTodoItem(idInt);
    			return true;
    		case R.id.move_item_up:
    			moveUpTodoItem(idInt);
    			return true;
    		case R.id.move_item_dn:
    			moveDnTodoItem(idInt);
    			return true;
    		default:
    			return super.onContextItemSelected(item);
    	}
    }
    
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.options_menu, menu);
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId()) 
    	{
    	case R.id.clear_items:
    		clearTodoList();
    		return true;
    	case R.id.add_item:
    		addTodoItem();
    		return true;
    	case R.id.quit:
    		quit();
    		return true;
    	}
    	return false;
    }
    
    public boolean moveUpTodoItem(int id)
    {
    	int newId = id - 1;
    	if(newId < 0) return true;
    	
    	String item = todoItems.get(id);
    	todoItems.remove(id);
    	todoItems.add(newId, item);
    	aa.notifyDataSetChanged();
    	
    	return true;
    }
    
    public boolean moveDnTodoItem(int id)
    {
    	int newId = id + 1;
    	if(newId >= todoItems.size()) return true;
    	
    	String item = todoItems.get(id);
    	todoItems.remove(id);
    	todoItems.add(newId, item);
    	aa.notifyDataSetChanged();
    	
    	return true;
    }
    
    public boolean deleteTodoItem(int id)
    {	
    	todoItems.remove(id);
    	aa.notifyDataSetChanged();
    	return true;
    }
    
    public boolean addTodoItem()
    {
    	String myEditTextString = myEditText.getText().toString();
    	addTodoItem(myEditTextString);
    	return true;
    }
    
    public boolean addTodoItem(String textString)
    {
    	if(myEditText.getText().length() == 0)
    	{
    		return true;
    	}
    	todoItems.add(0, textString);
		aa.notifyDataSetChanged();
		myEditText.setText("");
    	return true;
    }
    
    public boolean quit()
    {
    	finish();
    	return true;
    }
    
    public boolean clearTodoList()
    {
    	todoItems.clear();
    	aa.notifyDataSetChanged();
    	return true;
    }
    
    protected void sendnotification (String title, String message) 
    {  
    	   String ns = Context.NOTIFICATION_SERVICE;  
    	   NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);  
    	   
    	   int icon = R.drawable.icon;  
    	   CharSequence tickerText = message;  
    	   long when = System.currentTimeMillis();  
    	  
    	   Notification notification = new Notification(icon, tickerText, when);  
    	  
    	   Context context = getApplicationContext();  
    	   CharSequence contentTitle = title;  
    	   CharSequence contentText = message;  
    	   Intent notificationIntent = new Intent(this, ListViewMain.class);  
    	   PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);  
    	  
    	   notification.flags = Notification.FLAG_AUTO_CANCEL;  
    	   notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);  
    	   mNotificationManager.notify(0, notification);  
    	}  

}