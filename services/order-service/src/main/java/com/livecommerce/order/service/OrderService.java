package com.livecommerce.order.service;

import com.livecommerce.order.client.ProductClient;
import com.livecommerce.order.domain.*;
import com.livecommerce.order.request.*;
import com.livecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {
            Map<String, Object> product = productClient.getProductById(itemReq.getProductId());
            String productName = (String) product.get("name");
            BigDecimal price = new BigDecimal(product.get("price").toString());

            productClient.reduceStock(itemReq.getProductId(), itemReq.getQuantity());

            OrderItem orderItem = OrderItem.builder()
                    .productId(itemReq.getProductId())
                    .productName(productName)
                    .price(price)
                    .quantity(itemReq.getQuantity())
                    .build();

            total = total.add(price.multiply(BigDecimal.valueOf(itemReq.getQuantity())));
            items.add(orderItem);
        }

        Order order = Order.builder()
                .userId(request.getUserId())
                .status(OrderStatus.CREATED)
                .totalAmount(total)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        items.forEach(i -> i.setOrder(order));
        order.setItems(items);
        orderRepository.save(order);

        return mapToResponse(order);
    }

    public List<OrderResponse> getOrdersByUser(String userId) {
        return orderRepository.findByUserId(userId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public OrderResponse getOrderById(UUID id) {
        return orderRepository.findById(id).map(this::mapToResponse).orElseThrow();
    }

    @Transactional
    public void updateOrderStatus(UUID orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        order.setUpdatedAt(OffsetDateTime.now());
        orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(OffsetDateTime.now());
        orderRepository.save(order);
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .items(order.getItems().stream().map(i ->
                        OrderItemResponse.builder()
                                .productId(i.getProductId())
                                .productName(i.getProductName())
                                .price(i.getPrice())
                                .quantity(i.getQuantity())
                                .build()).collect(Collectors.toList()))
                .build();
    }
}
