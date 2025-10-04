package com.gustavooarantes.desafio_btg_pactual.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.gustavooarantes.desafio_btg_pactual.entity.Order;

public interface OrderRepository extends MongoRepository<Order, Long> {
  Page<Order> findAllByCustomerId(Long customerId, PageRequest pageRequest);
}
