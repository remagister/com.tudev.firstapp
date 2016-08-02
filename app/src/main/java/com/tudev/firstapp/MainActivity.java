package com.tudev.firstapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.tudev.firstapp.data.Contact;
import com.tudev.firstapp.data.ContactAccessor;
import com.tudev.firstapp.data.Contacts;
import com.tudev.firstapp.data.SQLContactHelper;
import com.tudev.firstapp.data.SimpleContactDatabase;

import java.io.IOException;
import java.io.Serializable;

import static android.R.color.holo_red_dark;

public class MainActivity extends AppCompatActivity {

    private static final int TAG_ID = 1;

    ContactAccessor accessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listViewOne);
        final Button actionButton = (Button) findViewById(R.id.listActionButton);
        accessor = new SimpleContactDatabase(new SQLContactHelper(getApplicationContext()));
        final ContactAdapter adapter = new ContactAdapter(getLayoutInflater(), accessor.getSimpleContacts());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                Contact.ContactSimple origin = (Contact.ContactSimple) adapterView.getItemAtPosition(i);
                Contact toSend = accessor.getContact(origin.getId());
                intent.putExtra(ContactActivity.CONTACT_BUNDLE_KEY, toSend);
                MainActivity.this.startActivity(intent);
            }
        });
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                actionButton.setText(MainActivity.this.getString(R.string.remove_button));
                actionButton.setTag(TAG_ID, AddButtonIntent.REMOVE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                actionButton.setText(MainActivity.this.getString(R.string.add_button));
                actionButton.setTag(TAG_ID, AddButtonIntent.ADD);
            }
        });
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddButtonIntent intent = (AddButtonIntent) actionButton.getTag(TAG_ID);
                switch (intent){
                    case ADD:{
                        // TODO: start activity_edit view with CREATE param
                    }
                    case REMOVE:{

                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            accessor.close();
        }catch (IOException ex){

        }
    }
}
