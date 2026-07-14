package com.example.admindashboardproject.admin.global.exception;

// 로그인 시 이메일 또는 비밀번호가 일치하지 않을 때 발생시키는 전용 예외
public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String message) {
        super(message);
    }
}