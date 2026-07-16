package com.example.admindashboardproject.admin.service;
import com.example.admindashboardproject.admin.config.PasswordEncoder;
import com.example.admindashboardproject.admin.dto.AdminLoginRequest;
import com.example.admindashboardproject.admin.dto.AdminRequest;
import com.example.admindashboardproject.admin.dto.AdminResponse;
import com.example.admindashboardproject.admin.dto.SessionAdmin;
import com.example.admindashboardproject.admin.entity.Admins;
import com.example.admindashboardproject.admin.entity.Status;
import com.example.admindashboardproject.admin.exception.AdminStatusErrorCode;
import com.example.admindashboardproject.admin.exception.DuplicateEmailException;
import com.example.admindashboardproject.admin.exception.InvalidCredentialException;
import com.example.admindashboardproject.admin.exception.InvalidStatus;
import com.example.admindashboardproject.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository repository;
    private final PasswordEncoder passwordEncoder;
    public static final String SESSION_KEY = "LOGIN_ADMIN_ID";

    @Transactional
    public AdminResponse signupadmin(AdminRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }
        Admins admin = new Admins(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getPhone(),
                request.getRole()
        );
        Admins savedadmin = repository.save(admin);
        return new AdminResponse(
                savedadmin.getName(),
                savedadmin.getEmail());
    }

    public SessionAdmin loginadmin(AdminLoginRequest request) {
        Admins admin = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialException("이메일 또는 비밀번호가 일치하지 않습니다."));
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new InvalidCredentialException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
        validStatus(admin);
        return new SessionAdmin(admin.getId(), admin.getName(), admin.getRole());
    }

    private void validStatus(Admins admin){
        if (admin.getStatus() == Status.ACTIVE){
            return;
        }
        AdminStatusErrorCode errorCode =  AdminStatusErrorCode.valueOf(admin.getStatus().name());
        throw new InvalidStatus(errorCode);
    }
}
