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
    public final CustomersService service;


    @GetMapping("/{customerId}")//리스트 조회
    public ResponseEntity<List<CustomerResponse>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        List<CustomerResponse> one = service.findAll(name, email);
        return ResponseEntity.ok(one);
    }

    @GetMapping //상세 조회
    public ResponseEntity<CustomerResponse> findOne(@PathVariable Long id){
        List<CustomerResponse> responseList = service.findOne(id);
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}") //고객 정보 수정
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @RequestBody CustomerUpdateResponse customerUpdateResponse){
        service.update(id);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{id}") //삭제
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}