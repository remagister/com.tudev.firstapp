package com.tudev.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tudev.firstapp.data.Contact;
import com.tudev.firstapp.data.SQLContactHelper;
import com.tudev.firstapp.data.SimpleContactDatabase;

public class EditContactActivity extends AppCompatActivity {

    private Contact contact;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;

    private void fillInfo(Contact contact){
        // fill in data (possibly must be in presenter, but later)
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        nameEditText.setText(contact.getName());
        emailEditText = (EditText) findViewById(R.id.editEmailText);
        emailEditText.setText(contact.getEmail());
        phoneEditText = (EditText) findViewById(R.id.editPhoneText);
        phoneEditText.setText(contact.getPhone());
        // TODO: image setting routine
    }

    private Contact constructContact(long id){
        Contact ret = new Contact(id);
        ret.setName(nameEditText.getText().toString());
        ret.setEmail(emailEditText.getText().toString());
        ret.setPhone(phoneEditText.getText().toString());
        // TODO: get image raw data
        return ret;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        final ContactActionIntent editingIntent = (ContactActionIntent)
                getIntent().getSerializableExtra(MainActivity.CONTACT_EDIT_INTENT_KEY);
        switch (editingIntent){
            case CREATE: {
                contact = new Contact(0);
                break;
            }
            case UPDATE: {
                Bundle extras = getIntent().getExtras();
                contact = (Contact) extras.get(ContactActivity.CONTACT_BUNDLE_KEY);
                break;
            }
        }

        fillInfo(contact);

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
                    contact = constructContact(contact.getId());
                    switch (editingIntent){
                        case UPDATE: {
                            db.updateContact(contact.getId(), contact);
                            DBState.INSTANCE.setState(ContactDBState.MODIFIED.with(contact.getId()));
                            break;
                        }
                        case CREATE: {
                            db.addContact(contact);
                            DBState.INSTANCE.setState(ContactDBState.MODIFIED.with(ContactDBState.NO_ID));
                            break;
                        }
                    }
                    finish();
                } else {
                    Toast.makeText(EditContactActivity.this, getText(R.string.field_empty_message),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
