package com.example.admindashboardproject.customer.controller;


import com.example.admindashboardproject.customer.dto.*;
import com.example.admindashboardproject.customer.service.CustomersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    public final CustomersService customersService;


    @GetMapping//리스트 조회
    public ResponseEntity<List<CustomerResponse>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        List<CustomerResponse> one = customersService.findAll(name, email);
        return ResponseEntity.ok(one);
    }

    @GetMapping("/{customerId}") //상세 조회
    public ResponseEntity<CustomerResponse> findOne(@PathVariable Long customerId){
        CustomerResponse responseList = customersService.findOne(customerId);
        return ResponseEntity.ok(responseList);
    }

    @PatchMapping("/{customerId}") //고객 정보 수정
    public ResponseEntity<CustomerUpdateResponse> update(@PathVariable Long customerId, @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest){
        CustomerUpdateResponse customerUpdateResponse = customersService.update(customerId, customerUpdateRequest);
        return ResponseEntity.ok(customerUpdateResponse);
    }
    @PatchMapping("/{customerId}/status") //고객 상태 변경
    public ResponseEntity<ChangeStatusResponse> changeStatus(@PathVariable Long customerId, @Valid @RequestBody ChangeStatusRequest changeStatusRequest){
        ChangeStatusResponse changeStatusResponse = customersService.changeStatus(customerId, changeStatusRequest);
        return ResponseEntity.ok(changeStatusResponse);
    }

    @DeleteMapping("/{customerId}") //삭제
    public ResponseEntity<DeleteCustomerResponse> delete(@PathVariable Long customerId){
        customersService.delete(customerId);
        DeleteCustomerResponse deleteCustomerResponse = new DeleteCustomerResponse("고객이 삭제되었습니다.");
        return ResponseEntity.ok(deleteCustomerResponse);
    }


}