package com.gustavooarantes.desafio_btg_pactual.controller;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gustavooarantes.desafio_btg_pactual.controller.dto.OrderResponse;
import com.gustavooarantes.desafio_btg_pactual.controller.dto.PaginationResponse;
import com.gustavooarantes.desafio_btg_pactual.controller.dto.Response;
import com.gustavooarantes.desafio_btg_pactual.service.OrderService;

@RestController
public class OrderController {

  private final OrderService service;

  public OrderController(OrderService service) {
    this.service = service;
  }

  @GetMapping("/customers/{customerId}/orders")
  public ResponseEntity<Response<OrderResponse>> listOrders(
      @PathVariable("customerId") Long customerId,
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

    var pageResponse = service.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
    var totalOnOrders = service.findTotalOnOrdersByCustomerId(customerId);

    return ResponseEntity.ok(new Response<>(
        Map.of("totalOnOrders", totalOnOrders),
        pageResponse.getContent(),
        PaginationResponse.fromPage(pageResponse)));
  }
}
