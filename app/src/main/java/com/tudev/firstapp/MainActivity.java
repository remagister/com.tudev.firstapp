package com.tudev.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView c = (ListView)findViewById(R.id.listViewOne);
        ContactAdapter adapter = new ContactAdapter(this, Contacts.getInstance().getContacts());

        c.setAdapter(adapter);
        c.setOnItemClickListener(new ContactListener(this));
    }


}
