package com.gustavooarantes.desafio_btg_pactual.controller.dto;

import java.math.BigDecimal;

import com.gustavooarantes.desafio_btg_pactual.entity.Order;

public record OrderResponse(Long orderId, Long customerId, BigDecimal total) {
  public static OrderResponse fromEntity(Order entity) {
    return new OrderResponse(entity.getId(), entity.getCustomerId(), entity.getTotal());
  }
}
