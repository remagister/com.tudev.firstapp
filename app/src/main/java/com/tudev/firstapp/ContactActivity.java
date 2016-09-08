package com.tudev.firstapp;

import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.view.ViewBase;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactActivity extends ViewBase<IContactPresenter> implements IContactView {

    public static final String CONTACT_BUNDLE_KEY = "CONTACT_BUNDLE_KEY";

    @BindView(R.id.contactTextName) TextView nameTextView;
    @BindView(R.id.contactTextEmail) TextView emailTextView;
    @BindView(R.id.contactTextPhone) TextView phoneTextView;
    @BindView(R.id.contactTextDate) TextView dateTextView;
    @BindView(R.id.buttonEditContact) Button buttonEdit;
    @BindView(R.id.contactIconView) ImageView imageView;

    private java.text.DateFormat dateFormat;

    @Override
    public IContactPresenter onPresenterCreate() {
        return new ContactPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);

        dateFormat = DateFormat.getDateFormat(this);

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
        if(contact == null){
            String undefined = getString(R.string.undefined_contact);
            nameTextView.setText(undefined);
            emailTextView.setText(undefined);
            phoneTextView.setText(undefined);
        } else {
            nameTextView.setText(contact.getName());
            emailTextView.setText(contact.getEmail());
            phoneTextView.setText(contact.getPhone());
            Date date = contact.getNativeDate();
            if (date != null){
                dateTextView.setText(dateFormat.format(date));
            }
            if(!contact.getImage().equals(Contact.EMPTY)) {
                Uri filename = Uri.withAppendedPath(
                        EditPresenter.getIconsUri(this),
                        contact.getImage());
                Picasso.with(this)
                        .load(filename)
                        .placeholder(R.mipmap.ic_account_box_black_48dp)
                        .fit()
                        .into(imageView);
            }
        }
    }
}
