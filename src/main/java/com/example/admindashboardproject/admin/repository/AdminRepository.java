package com.example.admindashboardproject.admin.repository;
import com.example.admindashboardproject.admin.entity.Admins;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admins, Long>, JpaSpecificationExecutor<Admins> {
    boolean existsByEmailAndIdNot(String email, Long id);
    Optional<Admins> findByEmail(
            @NotBlank(message = "이메일은 필수 입력값입니다.")
            @Email(message = "이메일 형식이 아닙니다.") String email);
}