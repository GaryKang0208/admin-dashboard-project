package com.example.admindashboardproject.customer.controller;

import com.example.admindashboardproject.customer.dto.CustomerResponse;
import com.example.admindashboardproject.customer.dto.CustomerUpdateResponse;
import com.example.admindashboardproject.customer.service.CustomersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    public final CustomersService customersService;


    @GetMapping("/{customerId}")//리스트 조회
    public ResponseEntity<List<CustomerResponse>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        List<CustomerResponse> one = customersService.findAll(name, email);
        return ResponseEntity.ok(one);
    }

    @GetMapping("/{customerId}") //상세 조회
    public ResponseEntity<CustomerResponse> findOne(@PathVariable Long id){
        CustomerResponse responseList = customersService.findOne(id);
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{customerId}") //고객 정보 수정
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @RequestBody CustomerUpdateResponse customerUpdateResponse){
        CustomerResponse customerResponse = customersService.update(id, customerUpdateResponse);
        return ResponseEntity.ok(customerResponse);
    }
//    @PutMapping("/{customerId}") //고객 상태 변경
//    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @RequestBody CustomerUpdateResponse customerUpdateResponse){
//        CustomerResponse customerResponse = customersService.update(id, customerUpdateResponse);
//        return ResponseEntity.ok(customerResponse);
//    }

    @DeleteMapping("/{customerId}") //삭제
    public ResponseEntity<Void> delete(@PathVariable Long id,@RequestBody CustomerUpdateResponse customerUpdateResponse){
        customersService.delete(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}