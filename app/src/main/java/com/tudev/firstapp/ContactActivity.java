package com.tudev.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tudev.firstapp.data.Contacts;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.data.SQLContactHelper;
import com.tudev.firstapp.data.SimpleContactDatabase;
import com.tudev.firstapp.view.ViewBase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactActivity extends ViewBase implements IContactView {

    public static final String CONTACT_BUNDLE_KEY = "CONTACT_BUNDLE_KEY";

    private IContactPresenter presenter;
    @BindView(R.id.contactTextName) private TextView nameTextView;
    @BindView(R.id.contactTextEmail) private TextView emailTextView;
    @BindView(R.id.contactPhoneLabel) private TextView phoneTextView; // FIXME: 03.08.2016 rename label

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ContactPresenter(this);
        presenter.onCreate(this);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);

        Button button = (Button) findViewById(R.id.buttonEditContact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onEditButtonClick();
            }
        });
    }

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }


    @Override
    public void setContact(Contact contact) {
        String undefined = getString(R.string.undefined_contact);
        nameTextView.setText(contact == null ? undefined : contact.getName());
        emailTextView.setText(contact == null ? undefined : contact.getEmail());
        phoneTextView.setText(contact == null ? undefined : contact.getPhone());
    }
}
