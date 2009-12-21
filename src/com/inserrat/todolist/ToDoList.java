package com.inserrat.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ToDoList extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	Intent MainIntent = 
    		new Intent(ToDoList.this, ListViewMain.class);
    	ToDoList.this.startActivity(MainIntent);
    	    
    }

}