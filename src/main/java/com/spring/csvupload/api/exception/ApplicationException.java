package com.spring.csvupload.api.exception;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String format, Object... parameters) {
        super(String.format(format, parameters));
    }

    protected ApplicationException(Throwable cause, String format, Object... parameters) {
        super(String.format(format, parameters), cause);
    }

    public static ApplicationException to(String format, Object... parameters) {
        return new ApplicationException(format, parameters);
    }

    public static ApplicationException to(Throwable cause, String format, Object... parameters) {
        return new ApplicationException(cause, format, parameters);
    }
}
