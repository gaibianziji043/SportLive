package com.ft.base.http;

/**
 * 该类仅供参考，实际业务返回的固定字段, 根据需求来定义，
 */
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    private T items;
    private boolean eof;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setItems(T items) {
        this.items = items;
    }

    public void setEof(boolean eof) {
        this.eof = eof;
    }

    public T getItems() {
        return items;
    }

    public boolean isEof() {
        return eof;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {
        return code == 0;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
