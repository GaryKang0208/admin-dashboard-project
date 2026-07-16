package com.example.admindashboardproject.customer.controller;
import com.example.admindashboardproject.admin.dto.SessionAdmin;
import com.example.admindashboardproject.admin.entity.Role;
import com.example.admindashboardproject.admin.exception.UnauthorizedException;
import com.example.admindashboardproject.customer.dto.*;
import com.example.admindashboardproject.customer.exception.ForbiddenException;
import com.example.admindashboardproject.customer.service.CustomersService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
// 고객 Controller CRUD 구현
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    public final CustomersService customersService;

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll(
            @ModelAttribute CustomerSearchRequest request){
        List<CustomerResponse> one = customersService.findAll(request);
        return ResponseEntity.ok(one);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> findOne(@PathVariable Long customerId){
        CustomerResponse responseList = customersService.findOne(customerId);
        return ResponseEntity.ok(responseList);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerUpdateResponse> update(@PathVariable Long customerId, @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest){
        CustomerUpdateResponse customerUpdateResponse = customersService.update(customerId, customerUpdateRequest);
        return ResponseEntity.ok(customerUpdateResponse);
    }

    @PatchMapping("/{customerId}/status")
    public ResponseEntity<ChangeStatusResponse> changeStatus(@PathVariable Long customerId, @Valid @RequestBody ChangeStatusRequest changeStatusRequest){
        ChangeStatusResponse changeStatusResponse = customersService.changeStatus(customerId, changeStatusRequest);
        return ResponseEntity.ok(changeStatusResponse);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<DeleteCustomerResponse> delete(@PathVariable Long customerId,HttpSession session){
        SessionAdmin loginAdmin = getLoginAdmin(session);
        if (loginAdmin.getRole() != Role.SUPER_ADMIN){
            throw new ForbiddenException("삭제 권한이 없습니다");
        }
        customersService.delete(customerId);
        DeleteCustomerResponse deleteCustomerResponse = new DeleteCustomerResponse("고객이 삭제되었습니다.");
        return ResponseEntity.ok(deleteCustomerResponse);
    }

    private SessionAdmin getLoginAdmin(HttpSession session){
        SessionAdmin loginAdmin = (SessionAdmin) session.getAttribute("loginAdmin");
        if (loginAdmin == null){
            throw new UnauthorizedException("로그인이 필요합니다");
        }
        return loginAdmin;
    }
}