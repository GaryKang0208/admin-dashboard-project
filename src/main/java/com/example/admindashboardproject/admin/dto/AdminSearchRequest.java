package com.example.admindashboardproject.admin.dto;

import com.example.admindashboardproject.admin.entity.Role;
import com.example.admindashboardproject.admin.entity.Status;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

// 리스트 검색/필터 조건
public class AdminSearchRequest {

    private String keyword; // 검색 키워드

    private int page = 1;
    private int size = 10;

    private String sortBy = "createdAt"; // 정렬 기준 : 이름 (name), 이메일 (email) , 가입일 (createdAt)
    private String sortOrder = "desc"; // 정렬 순서 :  asc, desc

    private Role role; // 역할 필터
    private Status status; // 상태 필터
}
