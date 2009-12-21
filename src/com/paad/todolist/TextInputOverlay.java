package com.paad.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TextInputOverlay extends Activity {
	
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.text_input_overlay);
    	Log.v("LOGGED AT TextInputOverlay::onCreate", "The creation of an intent worked!");
    	//myListView = (ListView)findViewById(R.id.myListView);
    	//myEditText = (EditText)findViewById(R.id.myEditText);
    }
    
}