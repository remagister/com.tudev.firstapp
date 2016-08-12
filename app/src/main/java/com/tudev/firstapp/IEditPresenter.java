package com.tudev.firstapp;

import android.net.Uri;

import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.graphics.ImageInfo;
import com.tudev.firstapp.presenter.IPresenter;

import java.io.IOException;

/**
 * Created by arseniy on 07.08.16.
 */

public interface IEditPresenter extends IPresenter {

    void acceptButtonClick();

    void onImageReceived(ImageInfo desiredImageInfo);
}
