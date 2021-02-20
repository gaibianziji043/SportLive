package com.ft.base.http;

import java.io.IOException;

public class ServerResponseException extends IOException {

    private int code;
    private String message;

    public ServerResponseException(int code, String message) {
        super();
        try {
            this.code = code;
            this.message = message;
        } catch (Exception e){

        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
