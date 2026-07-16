package com.example.admindashboardproject.order.controller;
import com.example.admindashboardproject.admin.dto.SessionAdmin;
import com.example.admindashboardproject.admin.entity.Role;
import com.example.admindashboardproject.admin.exception.InvalidCredentialException;
import com.example.admindashboardproject.order.dto.*;
import com.example.admindashboardproject.order.entity.OrderStatus;
import com.example.admindashboardproject.order.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// 주문 Controller CRUD구현
@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping("/commerce/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<CreateResponse> createOrder(@RequestBody CreateRequest request, HttpSession session){
        SessionAdmin loginAdmin =getCsAdmin(session);
        CreateResponse saved =  orderService.create(request, loginAdmin.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/list")
    public ResponseEntity<ListGetOrderResponse> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(required = false) OrderStatus status
    ) {
        ListGetOrderResponse response = orderService.getOrders(keyword, page, size, sortBy, sortOrder, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetOrderDetailResponse> getOrderDetail(@PathVariable Long id) {
        GetOrderDetailResponse response = orderService.getOrderDetail(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<UpdateStatusResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequest request,
            HttpSession session
    ) {
        UpdateStatusResponse response = orderService.updateStatus(id, request.getStatus());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<CancelResponse> cancelOrder(
            @PathVariable Long id,
            @RequestBody CancelRequest request,
            HttpSession session
    ) {
        getCsAdmin(session); // 권한 검증만, 반환값은 안 씀
        CancelResponse response = orderService.cancelOrder(id, request.getCancelReason());
        return ResponseEntity.ok(response);
    }

    private SessionAdmin getCsAdmin(HttpSession session) {
        SessionAdmin loginAdmin =
                (SessionAdmin) session.getAttribute("loginAdmin");
        if (loginAdmin == null) {
            throw new InvalidCredentialException("로그인이 필요합니다.");
        }
        if (loginAdmin.getRole() != Role.CS_ADMIN &&
                loginAdmin.getRole() != Role.SUPER_ADMIN) {
            throw new InvalidCredentialException("권한이 없습니다.");
        }
        return loginAdmin;
    }
}
