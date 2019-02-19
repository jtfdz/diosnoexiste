package com.tfmm.models;

public class Response<T> {

    private String message;
    private Integer status;
    private T data;

    public static void main(String[] args) {
        Response<?> resp = new Response<RegisterModel>();
        resp.setMessage("hola");
        resp.setStatus(200);

    }

    public Object getData(){ return data; }
    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
