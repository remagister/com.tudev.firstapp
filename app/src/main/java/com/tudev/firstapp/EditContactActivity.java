package com.tudev.firstapp;

    import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
    import android.util.Log;
    import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.dialog.DatePickerDialog;
import com.tudev.firstapp.dialog.OnDateSelectedListener;
import com.tudev.firstapp.view.ViewBase;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditContactActivity extends ViewBase<IEditPresenter> implements IEditView {

    private class DismissListener implements OnDateSelectedListener{

        @Override
        public void onDateSelected(Date date) {
            lastDateValue = date;
        }
    }

    private class AcceptListener implements OnDateSelectedListener{

        @Override
        public void onDateSelected(Date date) {
            lastDateValue = date;
            actualDate = date;
            dateTextView.setText(dateFormat.format(lastDateValue));
        }
    }

    private static final int PICK_IMAGE_CODE = 1;
    private static final String DATE_DIALOG_TAG = "DATE_DIALOG_TAG";
    private static final String ACTUAL_DATE_KEY = "ACTUAL_DATE";


    @BindView(R.id.nameEditText) EditText nameEditText;
    @BindView(R.id.editEmailText) EditText emailEditText;
    @BindView(R.id.editPhoneText) EditText phoneEditText;
    @BindView(R.id.buttonAcceptEdit) Button okButton;
    @BindView(R.id.editImageView) ImageView imageView;
    @BindView(R.id.editDateTextLabel) TextView dateTextView;
    @BindView(R.id.editDateButton) ImageButton editDateButton;

    private Date lastDateValue;
    private Date actualDate;
    private java.text.DateFormat dateFormat;

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

        dateFormat = DateFormat.getDateFormat(this);


        initializePresenter();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().acceptButtonClick(EditContactActivity.this);
            }
        });

        editDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog();
                datePickerDialog.setDate(lastDateValue);
                datePickerDialog.setOnDateSelectedListener(new AcceptListener());
                datePickerDialog.setOnDateDismissedListener(new DismissListener());
                datePickerDialog.show(getSupportFragmentManager(), DATE_DIALOG_TAG);
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
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        lastDateValue = (Date) savedInstanceState.getSerializable(DatePickerDialog.DATE_KEY);
        actualDate = (Date) savedInstanceState.getSerializable(ACTUAL_DATE_KEY);
        if(actualDate != null) {
            dateTextView.setText(dateFormat.format(actualDate));
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(lastDateValue != null) {
            outState.putSerializable(DatePickerDialog.DATE_KEY, lastDateValue);
        }
        if(actualDate != null){
            outState.putSerializable(ACTUAL_DATE_KEY, actualDate);
        }
        super.onSaveInstanceState(outState);
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
        actualDate = contact.getNativeDate();
        lastDateValue = actualDate;

        if(actualDate != null) {
            dateTextView.setText(dateFormat.format(actualDate));
        }

        if(!contact.getImage().equals(Contact.EMPTY)){
            Picasso.with(this).load(
                    Uri.withAppendedPath(EditPresenter.getIconsUri(this),
                            contact.getImage())
            ).fit().into(imageView);
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
        if(actualDate != null) {
            outContact.setNativeDate(lastDateValue);
        }
    }

    @Override
    public void setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

}
