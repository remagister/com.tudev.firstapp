package com.tudev.firstapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.view.ViewBase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactActivity extends ViewBase<IContactPresenter> implements IContactView {

    public static final String CONTACT_BUNDLE_KEY = "CONTACT_BUNDLE_KEY";

    @BindView(R.id.contactTextName) TextView nameTextView;
    @BindView(R.id.contactTextEmail) TextView emailTextView;
    @BindView(R.id.contactPhoneLabel) TextView phoneTextView; // FIXME: 03.08.2016 rename label
    @BindView(R.id.buttonEditContact) Button buttonEdit;
    @BindView(R.id.contactIconView) ImageView imageView;

    @Override
    public IContactPresenter onPresenterCreate() {
        return new ContactPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);

        initializePresenter();
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().onEditButtonClick();
            }
        });
    }

    @Override
    public void setContact(Contact contact) {
        String undefined = getString(R.string.undefined_contact);
        if(contact == null){
            nameTextView.setText(undefined);
            emailTextView.setText(undefined);
            phoneTextView.setText(undefined);
        } else {
            nameTextView.setText(contact.getName());
            emailTextView.setText(contact.getEmail());
            phoneTextView.setText(contact.getPhone());
            Uri filename = Uri.withAppendedPath(
                    EditPresenter.getIconsUri(this),
                    contact.getImage());
            Picasso.with(this).load(filename).into(imageView);
        }
    }
}
