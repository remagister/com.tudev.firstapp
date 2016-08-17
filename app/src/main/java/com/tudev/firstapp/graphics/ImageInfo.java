package com.tudev.firstapp.graphics;

import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;

import java.io.InputStream;

/**
 * Created by arseniy on 12.08.16.
 */

public class ImageInfo {
    private InputStream stream;
    private int width;
    private int height;
    private String mime;
    private BitmapFactory.Options options;

    public ImageInfo(InputStream stream, int width, int height) {
        this.stream = stream;
        this.width = width;
        this.height = height;
    }

    public ImageInfo(InputStream stream, BitmapFactory.Options options) {
        this.stream = stream;
        this.options = options;
        this.width = options.outWidth;
        this.height = options.outHeight;
        this.mime = options.outMimeType;
    }

    public InputStream getStream() {
        return stream;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getMimeType() {
        return mime;
    }

    public BitmapFactory.Options getRelatedOptions() {
        return options;
    }

    public static ImageInfo fromStream(InputStream stream){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream, new Rect(-1, -1, -1, -1),options);
        return new ImageInfo(stream, options);
    }
}
