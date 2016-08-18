package com.tudev.firstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.view.ViewBase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditContactActivity extends ViewBase<IEditPresenter> implements IEditView {

    public static final int PICK_IMAGE_CODE = 1;

    @BindView(R.id.nameEditText) EditText nameEditText;
    @BindView(R.id.editEmailText) EditText emailEditText;
    @BindView(R.id.editPhoneText) EditText phoneEditText;
    @BindView(R.id.buttonAcceptEdit) Button okButton;
    @BindView(R.id.editImageView) ImageView imageView;

    @Override
    public IEditPresenter onPresenterCreate()
    {
        return new EditPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        ButterKnife.bind(this);

        initializePresenter();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().acceptButtonClick(EditContactActivity.this);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent,
                        getString(R.string.selectImageTitle));
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK) {
            if (data == null) {
                message(R.string.imageNotPickedError);
            }
            else {
                getPresenter().onImageReceived(this, data.getData());
            }
        }
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
        if(!contact.getImage().equals(Contact.EMPTY)){
            Picasso.with(this).load(
                    Uri.withAppendedPath(EditPresenter.getIconsUri(this),
                            contact.getImage())
            ).into(imageView);
        }
    }

    @Override
    public boolean validate() {
        return nameEditText.getText().length() != 0
                && emailEditText.getText().length() != 0
                && phoneEditText.getText().length() != 0;
    }

    @Override
    public void extractData(Contact outContact) {
        outContact.setName(nameEditText.getText().toString());
        outContact.setEmail(emailEditText.getText().toString());
        outContact.setPhone(phoneEditText.getText().toString());
    }

    @Override
    public void setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
