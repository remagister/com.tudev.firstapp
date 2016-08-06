package com.tudev.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.data.SQLContactHelper;
import com.tudev.firstapp.data.SimpleContactDatabase;

public class ContactActivity extends AppCompatActivity {

    public static final String CONTACT_BUNDLE_KEY = "CONTACT_BUNDLE_KEY";

    private Contact contact;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;

    private void setInfo(Contact contact){
        String undefined = getString(R.string.undefined_contact);
        nameTextView = (TextView)findViewById(R.id.contactTextName);
        nameTextView.setText(contact == null ? undefined : contact.getName());
        emailTextView = (TextView)findViewById(R.id.contactTextEmail);
        emailTextView.setText(contact == null ? undefined : contact.getEmail());
        phoneTextView = (TextView)findViewById(R.id.contactPhoneLabel); // FIXME: 03.08.2016 rename label
        phoneTextView.setText(contact == null ? undefined : contact.getPhone());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            contact = (Contact) extras.get(CONTACT_BUNDLE_KEY);
        }
        else{
            // something went wrong
            String undefined = getString(R.string.undefined_contact);
            contact = new Contact(undefined, undefined);
        }

        setInfo(contact);

        Button button = (Button) findViewById(R.id.buttonEditContact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: pass contact to the edit_contact activity
                Intent intent = new Intent(ContactActivity.this, EditContactActivity.class);
                intent.putExtra(ContactActivity.CONTACT_BUNDLE_KEY, contact);
                intent.putExtra(MainActivity.CONTACT_EDIT_INTENT_KEY, ContactActionIntent.UPDATE);
                ContactActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        if(DBState.INSTANCE.getState().hasId()) {
            SimpleContactDatabase db = new SimpleContactDatabase(
                    new SQLContactHelper(getApplicationContext())
            );
            contact = db.getContact(DBState.INSTANCE.getState().getId());
            setInfo(contact);
        }
        super.onResume();
    }
}
