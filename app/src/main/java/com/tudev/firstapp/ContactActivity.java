package com.tudev.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        String key = getString(R.string.contact_bundle_key);
        Bundle extras = getIntent().getExtras();
        Contact contact;
        if (extras != null) {
            contact = (Contact)extras.get(key);
        }
        else{
            contact = new Contact("<undefined>", "<undefined>");
        }

        TextView nameTextView = (TextView)findViewById(R.id.contactNameLabel);
        nameTextView.setText(contact.getName());
        TextView emailTextView = (TextView)findViewById(R.id.contactEmailLabel);
        emailTextView.setText(contact.getEmail());

    }
}
