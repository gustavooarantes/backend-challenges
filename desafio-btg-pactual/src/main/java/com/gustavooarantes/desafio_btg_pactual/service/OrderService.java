package com.gustavooarantes.desafio_btg_pactual.service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.gustavooarantes.desafio_btg_pactual.controller.dto.OrderResponse;
import com.gustavooarantes.desafio_btg_pactual.entity.Item;
import com.gustavooarantes.desafio_btg_pactual.entity.Order;
import com.gustavooarantes.desafio_btg_pactual.listener.dto.OrderCreatedEvent;
import com.gustavooarantes.desafio_btg_pactual.repository.OrderRepository;

@Service
public class OrderService {

  private final OrderRepository repository;

  public OrderService(OrderRepository repository) {
    this.repository = repository;
  }

  public void save(OrderCreatedEvent event) {

    var entity = new Order();

    entity.setId(event.orderId());
    entity.setCustomerId(event.customerId());
    entity.setItems(
        event.items()
            .stream()
            .map(i -> new Item(i.product(), i.quantity(), i.price()))
            .collect(Collectors.toList()));
    entity.setTotal(getTotal(event));

    repository.save(entity);
  }

  public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
    var orders = repository.findAllByCustomerId(customerId, pageRequest);

    return orders
        .map(OrderResponse::fromEntity);
  }

  private BigDecimal getTotal(OrderCreatedEvent event) {
    return event.items().stream()
        .map(i -> i.price().multiply(BigDecimal.valueOf(i.quantity())))
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);
  }
}
