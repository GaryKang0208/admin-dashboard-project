package com.example.admindashboardproject.customer.service;

import com.example.admindashboardproject.customer.dto.CustomerResponse;
import com.example.admindashboardproject.customer.dto.CustomerUpdateResponse;
import com.example.admindashboardproject.customer.entity.Customer;
import com.example.admindashboardproject.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomersService {
    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll(String name, String email) {
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        if (name != null&& !name.isEmpty()) {
            List<Customer> customerList = customerRepository.findByName(name);
            if (customerList.isEmpty()) {
                throw new IllegalArgumentException("저장되지 않은 이름 입니다.");
            }
            List<CustomerResponse> customerResponseFindOneList = new ArrayList<>();

            for (Customer customer : customerList) {
                CustomerResponse customerResponse = new CustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getPhone(),
                        customer.getStatus(),
                        customer.getTotalOrders(),
                        customer.getTotalPurchaseAmount(),
                        customer.getCreatedAt()
                );
                customerResponseFindOneList.add(customerResponse);
            }
            return customerResponseFindOneList;
        } else if (email != null&&!email.isEmpty()) {
            List<Customer> customerList = customerRepository.findByEmail(email);
            if (customerList.isEmpty()) {
                throw new IllegalArgumentException("저장되지 않은 이메일 입니다.");
            }
            List<CustomerResponse> customerResponseFindOneList = new ArrayList<>();

            for (Customer customer : customerList) {
                CustomerResponse customerResponse = new CustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getPhone(),
                        customer.getStatus(),
                        customer.getTotalOrders(),
                        customer.getTotalPurchaseAmount(),
                        customer.getCreatedAt()
                );
                customerResponseFindOneList.add(customerResponse);
            }
            return customerResponseFindOneList;
          }else {
            List<CustomerResponse> customerResponseFindOneList = new ArrayList<>();
            List<Customer> customerList = customerRepository.findAll();
            for (Customer customer : customerList) {
                CustomerResponse customerResponse = new CustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getPhone(),
                        customer.getStatus(),
                        customer.getTotalOrders(),
                        customer.getTotalPurchaseAmount(),
                        customer.getCreatedAt()
                );
                customerResponseFindOneList.add(customerResponse);
            }
            return customerResponseFindOneList;
          }

    }
    //상세 조회
    @Transactional(readOnly = true)
    public CustomerResponse findOne(Long id) {
        Customer customer=customerRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "고객을 찾을수 없습니다:"
                ));
        CustomerResponse customerResponse= new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus(),
                customer.getTotalOrders(),
                customer.getTotalPurchaseAmount(),
                customer.getCreatedAt()
        );
        return customerResponse;
    }

    //정보 수정
    @Transactional
    public CustomerResponse update(Long id, CustomerUpdateResponse customerUpdateResponse) {
        Customer customer=customerRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "고객을 찾을수 없습니다:"
                ));
        customer.update(
                customerUpdateResponse.getName(),
                customerUpdateResponse.getEmail(),
                customerUpdateResponse.getPhone()
        );
        Customer customerRenewal = customerRepository.save(customer);
        CustomerResponse customerResponse = new CustomerResponse(
                customerRenewal.getId(),
                customerRenewal.getName(),
                customerRenewal.getEmail(),
                customerRenewal.getPhone(),
                customerRenewal.getStatus(),
                customerRenewal.getTotalOrders(),
                customerRenewal.getTotalPurchaseAmount(),
                customerRenewal.getCreatedAt()
        );
        return customerResponse;
    }

    //고객 삭제
    @Transactional
    public void delete(Long id) {
        Customer customer=customerRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "고객을 찾을수 없습니다:"
                ));
        customerRepository.delete(customer);
    }
}
//인셉션 안에 커스텀 예외처리를 만들어 놓으면 된다.

//