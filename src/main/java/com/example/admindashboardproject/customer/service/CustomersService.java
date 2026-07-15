package com.example.admindashboardproject.customer.service;



import com.example.admindashboardproject.customer.dto.*;
import com.example.admindashboardproject.customer.entity.Customer;
import com.example.admindashboardproject.customer.exception.CustomerNotFoundException;
import com.example.admindashboardproject.customer.repository.CustomerRepository;
import com.example.admindashboardproject.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll(CustomerSearchRequest request) {
        Sort sort;
        if (request.getDirection().equals("asc")){
            sort=Sort.by(request.getSortBy()).ascending();
        }else {
            sort=Sort.by(request.getSortBy()).descending();
        }
        Pageable pageable= PageRequest.of(
                request.getPage() -1,
                request.getSize(),
                sort
        );
        Page<Customer> customerPage;
        if (request.getKeyword() != null && !request.getKeyword().isBlank() && request.getStatus() !=null) {
            customerPage = customerRepository.findByKeywordAndStatus(
                    request.getKeyword(),
                    request.getStatus(),
                    pageable
            );
        } else if (request.getKeyword() !=null && !request.getKeyword().isBlank()) {
            customerPage = customerRepository.findByNameContainingOrEmailContaining(
                    request.getKeyword(),
                    request.getKeyword(),
                    pageable
            );
        }else if (request.getStatus() != null){
            customerPage = customerRepository.findByStatus(
                    request.getStatus(),
                    pageable
            );
        } else {
            customerPage=customerRepository.findAll(pageable);
          }
        List<CustomerResponse> customerResponseList =new ArrayList<>();
        for (Customer customer : customerPage.getContent()){
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
            customerResponseList.add(customerResponse);
        }
        return customerResponseList;
    }
    //상세 조회
    @Transactional(readOnly = true)
    public CustomerResponse findOne(Long id) {
        Customer customer=customerRepository.findById(id)
                .orElseThrow(()->new CustomerNotFoundException(
                        "고객을 찾을수 없습니다:"
                ));
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus(),
                customer.getTotalOrders(),
                customer.getTotalPurchaseAmount(),
                customer.getCreatedAt()
        );
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
        return new CustomerUpdateResponse(
                customerRenewal.getId(),
                customerRenewal.getName(),
                customerRenewal.getEmail(),
                customerRenewal.getPhone(),
                customerRenewal.getStatus(),
                customerRenewal.getUpdatedAt()
        );
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
        return new ChangeStatusResponse(
                customerRenewal.getId(),
                customerRenewal.getName(),
                customerRenewal.getStatus(),
                customerRenewal.getUpdatedAt()
        );
    }

    //고객 삭제
    @Transactional
    public void delete(Long id) {
        Customer customer=customerRepository.findById(id)
                .orElseThrow(()->new CustomerNotFoundException(
                        "고객을 찾을수 없습니다:"
                ));
        orderRepository.deleteByCustomer_Id(id); // 관련 주문 먼저 삭제
        customerRepository.delete(customer);
    }


}
