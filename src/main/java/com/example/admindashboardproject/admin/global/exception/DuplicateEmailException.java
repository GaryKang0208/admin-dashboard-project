package com.example.admindashboardproject.admin.global.exception;

// 회원가입 시 이미 등록된 이메일로 가입 시도할 때 발생시키는 전용 예외
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
