package com.example.admindashboardproject.customer.service;



import com.example.admindashboardproject.customer.dto.*;
import com.example.admindashboardproject.customer.entity.Customer;
import com.example.admindashboardproject.customer.exception.CustomerNotFoundException;
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
        if (name != null&& !name.isBlank()) {
            List<Customer> customerList = customerRepository.findByName(name);
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
        } else if (email != null&&!email.isBlank()) {
            List<Customer> customerList = customerRepository.findByEmail(email);
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
                .orElseThrow(()->new CustomerNotFoundException(
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
    public CustomerUpdateResponse update(Long id, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer=customerRepository.findById(id)
                .orElseThrow(()->new CustomerNotFoundException(
                        "고객을 찾을수 없습니다:"
                ));
        customer.update(
                customerUpdateRequest.getName(),
                customerUpdateRequest.getEmail(),
                customerUpdateRequest.getPhone()
        );
        Customer customerRenewal = customerRepository.save(customer);
        CustomerUpdateResponse customerUpdateResponse = new CustomerUpdateResponse(
                customerRenewal.getId(),
                customerRenewal.getName(),
                customerRenewal.getEmail(),
                customerRenewal.getPhone(),
                customerRenewal.getStatus(),
                customerRenewal.getUpdatedAt()
        );
        return customerUpdateResponse;
    }
    @Transactional //상태 변경
    public ChangeStatusResponse changeStatus(Long customerId, ChangeStatusRequest changeStatusRequest) {
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException(
                        "고객을 찾을수 없습니다:"
                ));
        customer.changeStatus(
                changeStatusRequest.getStatus()
        );
        Customer customerRenewal = customerRepository.save(customer);
        ChangeStatusResponse changeStatusResponse = new ChangeStatusResponse(
                customerRenewal.getId(),
                customerRenewal.getName(),
                customerRenewal.getStatus(),
                customerRenewal.getUpdatedAt()
        );
        return changeStatusResponse;
    }

    //고객 삭제
    @Transactional
    public void delete(Long id) {
        Customer customer=customerRepository.findById(id)
                .orElseThrow(()->new CustomerNotFoundException(
                        "고객을 찾을수 없습니다:"
                ));
        customerRepository.delete(customer);
    }


}
//인셉션 안에 커스텀 예외처리를 만들어 놓으면 된다.

//