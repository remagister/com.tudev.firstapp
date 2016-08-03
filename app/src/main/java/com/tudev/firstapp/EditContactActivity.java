package com.tudev.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tudev.firstapp.data.Contact;
import com.tudev.firstapp.data.ContactAccessor;
import com.tudev.firstapp.data.SQLContactHelper;
import com.tudev.firstapp.data.SimpleContactDatabase;

public class EditContactActivity extends AppCompatActivity {

    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        final ContactActionIntent editingIntent = (ContactActionIntent)
                getIntent().getSerializableExtra(MainActivity.CONTACT_EDIT_INTENT_KEY);
        switch (editingIntent){
            case CREATE: contact = new Contact(0);
            case UPDATE: {
                Bundle extras = getIntent().getExtras();
                contact = (Contact) extras.get(ContactActivity.CONTACT_BUNDLE_KEY);
            }
        }

        // fill in data (possibly must be in presenter, but later)
        // TODO: image setting routine
        final EditText nameEditText = (EditText)findViewById(R.id.nameEditText);
        nameEditText.setText(contact.getName());
        final EditText emailEditText = (EditText)findViewById(R.id.editEmailText);
        emailEditText.setText(contact.getEmail());
        final EditText phoneEditText = (EditText)findViewById(R.id.editPhoneText);
        phoneEditText.setText(contact.getPhone());

        Button okButton = (Button) findViewById(R.id.buttonAcceptEdit);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: check validity and accept changes
                if(nameEditText.getText().length() != 0
                        && emailEditText.getText().length() != 0
                        && phoneEditText.getText().length() != 0){
                    SimpleContactDatabase db = new SimpleContactDatabase(
                            new SQLContactHelper(getApplicationContext())
                    );
                    switch (editingIntent){
                        case UPDATE: db.updateContact(contact.getId(), contact);
                        case CREATE: db.addContact(contact);
                    }
                } else {
                    Toast.makeText(EditContactActivity.this, getText(R.string.field_empty_message),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
