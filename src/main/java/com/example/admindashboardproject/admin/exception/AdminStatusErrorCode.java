package com.example.admindashboardproject.admin.exception;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
    public enum AdminStatusErrorCode {
        PENDING("계정 승인대기중입니다."),
        INACTIVE("계정 비활성화 상태입니다."),
        REJECTED("계정 거부 상태입니다."),
        SUSPENDED("계정 정지 상태입니다.");

        private final String message;




    }

