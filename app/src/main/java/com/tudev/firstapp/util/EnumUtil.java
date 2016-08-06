package com.tudev.firstapp.util;

import com.tudev.firstapp.data.sql.TableInfoCommand;

import java.util.EnumSet;
import java.util.Enumeration;
import java.util.concurrent.Executor;

/**
 * Created by arseniy on 06.08.16.
 */

public final class EnumUtil {

    // T is enum
    public static <T extends Enum<T>> EnumSet<T> getFlags(long code, Class<T> typeOfEnum){
        EnumSet<T> statusFlags = EnumSet.noneOf(typeOfEnum);
        return statusFlags;
    }
}
