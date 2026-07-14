package com.example.admindashboardproject.admin.service;

import com.example.admindashboardproject.admin.config.PasswordEncoder;
import com.example.admindashboardproject.admin.dto.AdminDetailResponse;
import com.example.admindashboardproject.admin.dto.AdminSearchRequest;
import com.example.admindashboardproject.admin.dto.AdminUpdateRequest;
import com.example.admindashboardproject.admin.dto.PageResponse;
import com.example.admindashboardproject.admin.entity.Admins;
import com.example.admindashboardproject.admin.global.exception.AdminNotFoundException;
import com.example.admindashboardproject.admin.global.exception.DuplicateEmailException;
import com.example.admindashboardproject.admin.repository.AdminRepository;
import com.example.admindashboardproject.admin.repository.AdminSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

// 관리자 정보 관리 로직
public class AdminManageService {

    private final AdminRepository repository;
    private final PasswordEncoder passwordEncoder;


    // 관리자 리스트 조회
    public PageResponse<AdminDetailResponse> getAdminList(AdminSearchRequest condition) {
        Sort.Direction direction = "asc".equalsIgnoreCase(condition.getSortOrder())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(
                condition.getPage() - 1,
                condition.getSize(),
                Sort.by(direction, condition.getSortBy())
        );

        Specification<Admins> spec = AdminSpecification.search(
                condition.getKeyword(), condition.getRole(), condition.getStatus()
        );

        Page<Admins> adminsPage = repository.findAll(spec, pageable);
        Page<AdminDetailResponse> responsePage = adminsPage.map(this::toDetailResponse);

        return new PageResponse<>(responsePage);
    }

    private AdminDetailResponse toDetailResponse(Admins admins){
        return new AdminDetailResponse(
                admins.getId(),
                admins.getName(),
                admins.getEmail(),
                admins.getPhone(),
                admins.getRole(),
                admins.getStatus(),
                admins.getCreatedAt(),
                admins.getApprovedAt()
        );
    }

    // 관리자 상세 조회
    public AdminDetailResponse getAdminDetail(Long id){
        Admins admins = repository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("존재하지 않는 관리자입니다."));
        return toDetailResponse(admins);
    }

    // 관리자 정보 수정
    public AdminDetailResponse updateAdmin(Long id, AdminUpdateRequest request){
        Admins admins = repository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("존재하지 않는 관리자입니다."));

        if (repository.existsByEmail(request.getEmail(), id)){
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }

        admins.updateInfo(request.getName(), request.getEmail(),  request.getPhone());
        return toDetailResponse(admins);
    }
}