package com.spring.csvupload.api.utils;

import com.spring.csvupload.api.exception.ApplicationException;

public class StringValidator {
    public static final int LENGTH_CONSTRAINT = 2000;
    public static String getValidStringValue(String stringField, String fieldName) {
        if (stringField.equals("")) {
            return null;
        }
        if (stringField.length() > LENGTH_CONSTRAINT) {
            throw ApplicationException.to("The file has invalid field %s, Length can not Exceed 2000 ", fieldName);
        }
        return stringField;
    }
    private StringValidator(){
        // Do Nothing
    }
}
