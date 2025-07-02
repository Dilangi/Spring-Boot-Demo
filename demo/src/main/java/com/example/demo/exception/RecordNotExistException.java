package com.example.demo.exception;

public class RecordNotExistException extends RuntimeException{
    public RecordNotExistException(String msg){
        super(msg);
    }
}
