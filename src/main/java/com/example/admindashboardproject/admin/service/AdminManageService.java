package com.example.admindashboardproject.admin.service;

import com.example.admindashboardproject.admin.config.PasswordEncoder;
import com.example.admindashboardproject.admin.dto.*;
import com.example.admindashboardproject.admin.entity.Admins;
import com.example.admindashboardproject.admin.global.exception.AdminNotFoundException;
import com.example.admindashboardproject.admin.global.exception.DuplicateEmailException;
import com.example.admindashboardproject.admin.global.exception.InvalidCredentialException;
import com.example.admindashboardproject.admin.repository.AdminRepository;
import com.example.admindashboardproject.admin.repository.AdminSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

// 관리자 정보 관리 로직
public class AdminManageService {

    private final AdminRepository repository;
    private final PasswordEncoder passwordEncoder;


    // 관리자 리스트 조회
    public PageResponse<AdminBaseResponse> getAdminList(AdminSearchRequest condition) {
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
        Page<AdminBaseResponse> responsePage = adminsPage.map(this::toBaseResponse);

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


    private AdminBaseResponse toBaseResponse(Admins admins){
        return new AdminBaseResponse(
                admins.getId(),
                admins.getName(),
                admins.getEmail(),
                admins.getPhone(),
                admins.getRole(),
                admins.getStatus(),
                admins.getCreatedAt()
        );
    }

    // 관리자 상세 조회
    public AdminDetailResponse getAdminDetail(Long id){
        Admins admins = repository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("존재하지 않는 관리자입니다."));
        return toDetailResponse(admins);
    }

    // 관리자 정보 수정
    @Transactional
    public AdminBaseResponse updateAdmin(Long id, AdminUpdateRequest request){
        Admins admins = repository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("존재하지 않는 관리자입니다."));

        if (repository.existsByEmailAndIdNot(request.getEmail(), id)){
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }

        admins.updateInfo(request.getName(), request.getEmail(),  request.getPhone());
        return toBaseResponse(admins);
    }

    // 관리자 역할 변경
    @Transactional
    public AdminBaseResponse changeAdminRole(Long id, AdminRoleUpdateRequest request){
        Admins admins = repository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("존재하지 않는 관리자입니다."));

        admins.changeRole(request.getRole());
        return toBaseResponse(admins);
    }

    // 관리자 상태 변경
    @Transactional
    public AdminBaseResponse changeAdminStatus(Long id, AdminStatusUpdateRequest request){
        Admins admins = repository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("존재하지 않는 관리자입니다."));

        admins.changeStatus(request.getStatus());
        return toBaseResponse(admins);
    }

    // 관리자 삭제
    @Transactional
    public void deleteAdmin(Long id){
        Admins admins = repository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("존재하지 않는 관리자입니다."));

        repository.delete(admins);
    }

    // 관리자 승인
    @Transactional
    public AdminBaseResponse approveAdmin(Long id){
        Admins admins = repository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("존재하지 않는 관리자 입니다."));

        admins.approve();
        return toBaseResponse(admins);
    }

    // 관리자 거부
    @Transactional
    public AdminBaseResponse rejectAdmin(Long id, AdminRejectRequest request){
        Admins admins = repository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("존재하지 않는 관리자입니다."));

        admins.reject(request.getRejectReason());
        return toBaseResponse(admins);
    }

    // 비밀번호 변경
    @Transactional
    public void changePassword(Long id, PasswordChangeRequest request){
        Admins admins = repository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("존재하지 않는 관리자입니다."));

        if (!passwordEncoder.matches(request.getCurrentPassword(), admins.getPassword())){
            throw new InvalidCredentialException("현재 비밀번호가 일치하지 않습니다.");
        }
        admins.changePassword(passwordEncoder.encode(request.getNewPassword()));
    }


    public AdminBaseResponse getAdminBase(Long id){
        Admins admins = repository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("존재하지 않는 관리자입니다."));
        return toBaseResponse(admins);
    }
}