package com.tudev.firstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.graphics.ImageInfo;
import com.tudev.firstapp.view.ViewBase;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditContactActivity extends ViewBase<IEditPresenter> implements IEditView {

    public static final int PICK_IMAGE_CODE = 1;

    private IEditImagePresenter imagePresenter;

    @BindView(R.id.nameEditText) EditText nameEditText;
    @BindView(R.id.editEmailText) EditText emailEditText;
    @BindView(R.id.editPhoneText) EditText phoneEditText;
    @BindView(R.id.buttonAcceptEdit) Button okButton;
    @BindView(R.id.editImageView) ImageView imageView;

    @Override
    public IEditPresenter onPresenterCreate()
    {
        imagePresenter = new EditImagePresenter(this);
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
                // TODO: check validity and accept changes
                getPresenter().acceptButtonClick();
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
                try {
                    getPresenter().onImageReceived(
                            new ImageInfo(getContentResolver().openInputStream(data.getData()),
                                    imageView.getWidth(), imageView.getHeight())
                    );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
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

    @Override
    public void setThumbnail(Bitmap bitmap) {
        if(bitmap == null){
            Log.e(getClass().getName(), "BITMAP == null");
        }
        imageView.setImageBitmap(bitmap);
    }
}
