package com.tudev.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tudev.firstapp.data.Contact;

public class EditContactActivity extends AppCompatActivity {

    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        ContactActionIntent editingIntent = (ContactActionIntent)
                getIntent().getSerializableExtra(MainActivity.CONTACT_EDIT_INTENT_KEY);
        if(editingIntent == ContactActionIntent.CREATE){
            contact = new Contact(0);
        }

        Button okButton = (Button) findViewById(R.id.buttonAcceptEdit);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
