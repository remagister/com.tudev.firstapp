package com.tudev.firstapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.tudev.firstapp.graphics.ImageCompressingUnit;
import com.tudev.firstapp.graphics.ImageInfo;
import com.tudev.firstapp.presenter.ContactPresenterBase;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by arseniy on 17.08.16.
 */

public class EditImagePresenter extends ContactPresenterBase<IEditView> implements IEditImagePresenter {

    private static final String THUMBNAILS_DIR = ".thumbnails";
    private static final String ICONS_DIR = ".icons";
    private static final String READ_MODE = "r";

    private boolean isImageChosen = false;
    private Uri cacheUri;

    public EditImagePresenter(IEditView view) {
        super(view, true);
    }

    @Override
    public void onCreate(Context context) {
        cacheUri = Uri.withAppendedPath(Uri.fromFile(context.getCacheDir()), THUMBNAILS_DIR);
    }

    @Override
    public void onImageReceived(Context context, Uri imageUri) {
        try {
            InputStream infoStream = context.getContentResolver().openInputStream(imageUri);
            InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
            // get image size and mime type
            BitmapFactory.Options bitmapOptions = ImageCompressingUnit.getImageOptions(infoStream);
            ImageInfo desiredImageIcon = new ImageInfo(imageStream, bitmapOptions);
            Bitmap bitmapIcon = ImageCompressingUnit.getCompressedBitmap(
                    context.getResources().getDimensionPixelSize(R.dimen.avatar_width),
                    context.getResources().getDimensionPixelSize(R.dimen.avatar_height),
                    desiredImageIcon);

        } catch (FileNotFoundException e) {
            getParentView().message(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void initialize() {

    }
}
