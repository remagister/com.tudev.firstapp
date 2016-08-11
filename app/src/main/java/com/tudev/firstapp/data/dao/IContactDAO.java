package com.tudev.firstapp.data.dao;

import com.tudev.firstapp.data.dao.IContactReader;
import com.tudev.firstapp.data.dao.IContactWriter;

import java.io.Closeable;
import java.util.List;

/**
 * Created by Саша on 02.08.2016.
 */
public interface IContactDAO extends Closeable {
    IContactReader getReader();
    IContactWriter getWriter();
    void invalidate();

    boolean isClosed();
}
