package com.tudev.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tudev.firstapp.data.Contacts;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.data.SQLContactHelper;
import com.tudev.firstapp.data.SimpleContactDatabase;
import com.tudev.firstapp.view.ViewBase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditContactActivity extends ViewBase implements IEditView {

    private IEditPresenter presenter;
    @BindView(R.id.nameEditText) private EditText nameEditText;
    @BindView(R.id.editEmailText) private EditText emailEditText;
    @BindView(R.id.editPhoneText) private EditText phoneEditText;
    @BindView(R.id.buttonAcceptEdit) private Button okButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        ButterKnife.bind(this);

        presenter = new EditPresenter(this);
        presenter.onCreate(getApplicationContext());

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: check validity and accept changes
                presenter.acceptButtonClick();
            }
        });

    }

    @Override
    public void goToActivity(Intent intent) {
        if(intent == null){
            finish();
        }
    }

    @Override
    public void setContact(Contact contact) {
        nameEditText.setText(contact.getName());
        emailEditText.setText(contact.getEmail());
        phoneEditText.setText(contact.getPhone());
        // TODO: IMAGE SETTING
    }

    @Override
    public boolean validate() {
        return nameEditText.getText().length() != 0
                && emailEditText.getText().length() != 0
                && phoneEditText.getText().length() != 0;
    }

    @Override
    public Contact extractData(long id) {
        Contact ret = new Contact(id);
        ret.setName(nameEditText.getText().toString());
        ret.setEmail(emailEditText.getText().toString());
        ret.setPhone(phoneEditText.getText().toString());
        // TODO: get image raw data
        return ret;
    }
}
