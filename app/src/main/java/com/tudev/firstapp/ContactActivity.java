package com.tudev.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        final Contact contact;
        if (extras != null) {
            contact = (Contact)extras.get(CONTACT_BUNDLE_KEY);
        }
        else{
            // something went wrong
            contact = new Contact(undefined, undefined);
        }

        TextView nameTextView = (TextView)findViewById(R.id.contactTextName);
        nameTextView.setText(contact == null ? undefined : contact.getName());
        TextView emailTextView = (TextView)findViewById(R.id.contactTextEmail);
        emailTextView.setText(contact == null ? undefined : contact.getEmail());
        TextView phoneTextView = (TextView)findViewById(R.id.contactPhoneLabel); // FIXME: 03.08.2016 rename label
        phoneTextView.setText(contact == null ? undefined : contact.getPhone());


        Button button = (Button) findViewById(R.id.buttonEditContact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: pass contact to the edit_contact activity
                Intent intent = new Intent(ContactActivity.this, ContactActivity.class);
                intent.putExtra(ContactActivity.CONTACT_BUNDLE_KEY, contact);
                intent.putExtra(MainActivity.CONTACT_EDIT_INTENT_KEY, ContactActionIntent.UPDATE);
                ContactActivity.this.startActivity(intent);
            }
        });
    }
}
