package com.tudev.firstapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.squareup.picasso.Picasso;
import com.tudev.firstapp.data.Contacts;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.graphics.ImageCompressingUnit;
import com.tudev.firstapp.graphics.ImageInfo;
import com.tudev.firstapp.presenter.ContactPresenterBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Struct;
import java.util.StringTokenizer;

/**
 * Created by arseniy on 07.08.16.
 */

public class EditPresenter extends ContactPresenterBase<IEditView> implements IEditPresenter {

    private static final String THUMBNAILS_DIR = ".thumbnails";
    private static final String ICONS_DIR = ".icons";
    private static final String BITMAP_ICON_BUNDLE_KEY = "BITMAP_ICON_BUNDLE_KEY";
    private static final String BITMAP_THUMB_BUNDLE_KEY = "BITMAP_THUMB_BUNDLE_KEY";
    private static final int COMPRESS_RATE = 85;
    private static final Bitmap.CompressFormat COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;

    private ContactActionIntent editingIntent;
    private Contact contact;
    private Uri iconsUri;
    private Uri thumbsUri;
    private Uri lastUri;


    private Bitmap bitmapThumbnail;
    private Bitmap bitmapIcon;

    public EditPresenter(IEditView parentView) {
        super(parentView, true);
    }

    public static Uri getIconsUri(Context context){
        return Uri.withAppendedPath(Uri.fromFile(context.getCacheDir()), ICONS_DIR);
    }
    public static Uri getThumbsUri(Context context){
        return Uri.withAppendedPath(Uri.fromFile(context.getCacheDir()), THUMBNAILS_DIR);
    }

    @Override
    public void onLoadState(Bundle bundle) {
        if(bundle != null) {
            contact = (Contact) bundle.getSerializable(ContactActivity.CONTACT_BUNDLE_KEY);
            bitmapIcon = bundle.getParcelable(BITMAP_ICON_BUNDLE_KEY);
            bitmapThumbnail = bundle.getParcelable(BITMAP_THUMB_BUNDLE_KEY);
            getParentView().setImage(bitmapIcon);
            super.onLoadState(bundle);
        }
    }

    private boolean checkOrCreate(String path){
        File fcheck = new File(path);
        if(!fcheck.exists() || !fcheck.isDirectory()){
            return fcheck.mkdir();
        }
        return true;
    }
    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        iconsUri = getIconsUri(context);
        thumbsUri = getThumbsUri(context);
        if(!checkOrCreate(iconsUri.getPath())){
            getParentView().message("Cannot find icons directory path");
        }
        if(!checkOrCreate(thumbsUri.getPath())){
            getParentView().message("Cannot find thumbs directory path");
        }
        Intent intent = ((Activity) context ).getIntent();
        editingIntent = (ContactActionIntent) intent
                .getSerializableExtra(MainPresenter.CONTACT_EDIT_INTENT_KEY);
        switch (editingIntent){
            case CREATE: {
                contact = new Contact(0);
                break;
            }
            case UPDATE: {
                Bundle extras = intent.getExtras();
                contact = (Contact) extras.get(ContactActivity.CONTACT_BUNDLE_KEY);
                break;
            }
        }
    }

    @Override
    public void initialize() {
        getParentView().setContact(contact);
    }

    @Override
    public void acceptButtonClick(Context context) {
        IEditView view = getParentView();
        if(view.validate()) {
            view.extractData(contact);
            if(!contact.getImage().equals(Contact.EMPTY)){
                try {
                    saveCache(context);
                } catch (IOException e) {
                    getParentView().message(e.getMessage());
                    e.printStackTrace();
                }
            }
            switch (editingIntent) {
                case UPDATE: {
                    getWriter().updateContact(contact.getId(), contact);
                    Contacts.INSTANCE.setState(ContactDBState.MODIFIED.with(contact.getId()));
                    break;
                }
                case CREATE: {
                    getWriter().addContact(contact);
                    Contacts.INSTANCE.setState(ContactDBState.MODIFIED.with(ContactDBState.NO_ID));
                    break;
                }
            }
            view.goToActivity(null);
        } else {
            view.message(R.string.field_empty_message);
        }
    }

    private void saveCache(Context context) throws IOException {
        // save icon
        if(bitmapIcon != null && bitmapThumbnail != null) {
            FileOutputStream writer = new FileOutputStream(
                    Uri.withAppendedPath(iconsUri, contact.getImage()).getPath());
            if(!bitmapIcon.compress(COMPRESS_FORMAT, COMPRESS_RATE, writer)){
                getParentView().message(context.getString(R.string.iconCompressingFalse));
            }
            writer.close();
            writer = new FileOutputStream(
                    Uri.withAppendedPath(thumbsUri, contact.getImage()).getPath());
            if(!bitmapThumbnail.compress(COMPRESS_FORMAT, COMPRESS_RATE, writer)){
                getParentView().message(context.getString(R.string.thumbCompressingFalse));
            }
            writer.close();
        }
    }

    private static InputStream getStream(Context context, Uri uri) throws FileNotFoundException {
        return context.getContentResolver().openInputStream(uri);
    }
    private static int getDimension(Context context, int id){
        return context.getResources().getDimensionPixelSize(id);
    }

    @Override
    public void onSaveState(Bundle bundle) {
        bundle.putSerializable(ContactActivity.CONTACT_BUNDLE_KEY, contact);
        if(bitmapIcon != null && bitmapThumbnail != null) {
            bundle.putParcelable(BITMAP_ICON_BUNDLE_KEY, bitmapIcon);
            bundle.putParcelable(BITMAP_THUMB_BUNDLE_KEY, bitmapThumbnail);
        }
        super.onSaveState(bundle);
    }

    @Override
    public void onImageReceived(Context context, Uri uri){
        ImageInfo info;
        if(lastUri == null || lastUri.compareTo(uri) != 0) {
            try {
                contact.setImage(uri.getLastPathSegment());
                // get image size and mime type
                BitmapFactory.Options bitmapOptions = ImageCompressingUnit.getImageOptions(
                        getStream(context, uri)
                );
                info = new ImageInfo(getStream(context, uri), bitmapOptions);
                int wid = getDimension(context, R.dimen.avatar_width);
                int hei = getDimension(context, R.dimen.avatar_height);
                bitmapIcon = Bitmap.createScaledBitmap(
                        ImageCompressingUnit.getCompressedBitmap(wid, hei, info), wid, hei, true);
                info.getStream().close();
                wid = getDimension(context, R.dimen.thumbnail_width);
                hei = getDimension(context, R.dimen.thumbnail_height);
                info = new ImageInfo(getStream(context, uri), bitmapOptions);
                bitmapThumbnail = Bitmap.createScaledBitmap(
                        ImageCompressingUnit.getCompressedBitmap(wid, hei, info), wid, hei, true);
                info.getStream().close();
                getParentView().setImage(bitmapIcon);
            } catch (IOException e) {
                getParentView().message(e.getMessage());
                e.printStackTrace();
                contact.setImage("");
            }
        }
    }
}
