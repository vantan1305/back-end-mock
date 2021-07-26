package com.demo.demo.message.response;

public class ResponseMessage {
    private boolean success;
    private String message;

    public ResponseMessage(boolean b, String otp_sent_on_email_account) {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResponseMessage(boolean success) {
        this.success = success;
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
