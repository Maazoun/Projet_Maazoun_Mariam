package com.ensta.librarymanager.exception;

public class ServiceException extends Exception{private static final long serialVersionUID = 11L;
    public ServiceException() {}
    public ServiceException(String msg) {
        super(msg);
    }
    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
