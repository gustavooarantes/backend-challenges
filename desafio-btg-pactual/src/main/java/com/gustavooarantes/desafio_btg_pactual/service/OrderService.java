package com.gustavooarantes.desafio_btg_pactual.service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.gustavooarantes.desafio_btg_pactual.controller.dto.OrderResponse;
import com.gustavooarantes.desafio_btg_pactual.entity.Item;
import com.gustavooarantes.desafio_btg_pactual.entity.Order;
import com.gustavooarantes.desafio_btg_pactual.listener.dto.OrderCreatedEvent;
import com.gustavooarantes.desafio_btg_pactual.repository.OrderRepository;

@Service
public class OrderService {

  private final OrderRepository repository;

  private final MongoTemplate template;

  public OrderService(OrderRepository repository, MongoTemplate template) {
    this.repository = repository;
    this.template = template;
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

  public BigDecimal findTotalOnOrdersByCustomerId(Long customerId) {
    var aggregations = Aggregation.newAggregation(
        Aggregation.match(Criteria.where("customerId").is(customerId)),
        Aggregation.group().sum("total").as("total"));

    var resultDoc = template.aggregate(aggregations, "tb_orders", Document.class)
        .getUniqueMappedResult();

    if (resultDoc == null) {
      return BigDecimal.ZERO;
    }

    Object totalObj = resultDoc.get("total");
    if (totalObj instanceof Number) {
      return BigDecimal.valueOf(((Number) totalObj).doubleValue());
    }

    return BigDecimal.ZERO;
  }

  private BigDecimal getTotal(OrderCreatedEvent event) {
    return event.items().stream()
        .map(i -> i.price().multiply(BigDecimal.valueOf(i.quantity())))
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);
  }
}
