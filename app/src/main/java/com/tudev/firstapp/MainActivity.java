package com.tudev.firstapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView c = (ListView)findViewById(R.id.listViewOne);
        ContactAdapter adapter = new ContactAdapter(getLayoutInflater(), Contacts.Instance.getContacts());

        c.setAdapter(adapter);
        final Context context = this;
        c.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, ContactActivity.class);
                intent.putExtra(ContactActivity.CONTACT_BUNDLE_KEY,
                        (Serializable)adapterView.getItemAtPosition(i));
                context.startActivity(intent);
            }
        });
    }

}
