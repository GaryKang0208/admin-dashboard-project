package com.example.admindashboardproject.admin.exception;

public class InvalidStatus extends RuntimeException {
    private final AdminStatusErrorCode errorCode;
    public InvalidStatus(AdminStatusErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public AdminStatusErrorCode getErrorCode() {
        return errorCode;
    }
}
