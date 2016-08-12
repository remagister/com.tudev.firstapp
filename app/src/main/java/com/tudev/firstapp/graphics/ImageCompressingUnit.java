package com.tudev.firstapp.graphics;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by arseniy on 12.08.16.
 */

public class ImageCompressingUnit {

    private ImageInfo info;
    private int compressionRate;

    public ImageCompressingUnit(int desiredWidth, int desiredHeight, ImageInfo imageInfo) {
        info = imageInfo;
        compressionRate = calculateCompressionRate(desiredWidth, desiredHeight, info);
    }

    public static Bitmap getCompressedBitmap(int desiredWidth, int desiredHeight, InputStream stream) throws IOException {
        ImageInfo info = ImageInfo.fromStream(stream);
        BitmapFactory.Options options = info.getRelatedOptions();
        options.inSampleSize = calculateCompressionRate(desiredWidth,desiredHeight,info);
        options.inJustDecodeBounds = false;
        stream.reset();
        return BitmapFactory.decodeStream(stream, new Rect(-1, -1, -1, -1), options);
    }

    public static Bitmap getCompressedBitmap(ImageInfo desiredImageInfo) throws IOException {
        return getCompressedBitmap(desiredImageInfo.getWidth(),
                desiredImageInfo.getHeight(), desiredImageInfo.getStream());
    }

    private static int calculateCompressionRate(
            int desiredWidth, int desiredHeight, ImageInfo imageInfo) {

        int height = imageInfo.getHeight();
        int width = imageInfo.getWidth();
        int inSampleSize = 1;

        if (height > desiredHeight || width > desiredWidth) {

            int halfHeight = height / 2;
            int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= desiredHeight
                    && (halfWidth / inSampleSize) >= desiredWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
