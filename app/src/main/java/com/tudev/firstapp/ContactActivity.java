package com.tudev.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tudev.firstapp.data.Contact;

public class ContactActivity extends AppCompatActivity {

    public static final String CONTACT_BUNDLE_KEY = "CONTACT_BUNDLE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Bundle extras = getIntent().getExtras();
        String undefined = getString(R.string.undefined_contact);
        Contact contact;
        if (extras != null) {
            contact = (Contact)extras.get(CONTACT_BUNDLE_KEY);
        }
        else{

            contact = new Contact(undefined, undefined);
        }

        TextView nameTextView = (TextView)findViewById(R.id.contactNameLabel);
        nameTextView.setText(contact == null ? undefined : contact.getName());
        TextView emailTextView = (TextView)findViewById(R.id.contactTextEmail);
        emailTextView.setText(contact == null ? undefined : contact.getEmail());

    }
}
