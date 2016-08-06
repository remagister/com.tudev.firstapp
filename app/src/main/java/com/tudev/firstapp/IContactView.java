package com.tudev.firstapp;

import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.view.IView;

/**
 * Created by arseniy on 07.08.16.
 */

public interface IContactView extends IView {

    void setContact(Contact contact);
}
