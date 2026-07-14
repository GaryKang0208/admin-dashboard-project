package com.example.admindashboardproject.customer.service;

import com.example.admindashboardproject.customer.dto.CustomerResponse;
import com.example.admindashboardproject.customer.entity.Customer;
import com.example.admindashboardproject.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                        customer.getTotalOrders(),
                        customer.getTotalPurchaseAmount()
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
                        customer.getTotalOrders(),
                        customer.getTotalPurchaseAmount()
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
                        customer.getTotalOrders(),
                        customer.getTotalPurchaseAmount()
                );
                customerResponseFindOneList.add(customerResponse);
            }
            return customerResponseFindOneList;
          }

    }

    @Transactional
    public List<CustomerResponse> findOne(Long id) {
        Customer customer=get
    }
    @Transactional
    public void delete(Long id) {
        if (id)
            customerRepository.delete(customer);
    }

}
//인셉션 안에 커스텀 예외처리를 만들어 놓으면 된다.

//