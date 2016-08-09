package com.tudev.firstapp;

import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.presenter.IPresenter;

/**
 * Created by arseniy on 07.08.16.
 */

public interface IEditPresenter extends IPresenter {

    void acceptButtonClick();

    void onImageReceived();
}
