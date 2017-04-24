package ru.spbstu.icc.kspt.inspace.service.documents.responses;

public class Error {
    private final String message;
    private final int code;

    public Error(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
