package com.ensta.librarymanager.exception;

public class DaoException extends Exception{
    private static final long serialVersionUID = 11L;
    public DaoException(){}
    public DaoException(String msg,Throwable cause){super(msg,cause);}
    public DaoException(String msg){super(msg);}

}
