package com.gustavooarantes.desafio_btg_pactual.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.gustavooarantes.desafio_btg_pactual.listener.dto.OrderCreatedEvent;
import com.gustavooarantes.desafio_btg_pactual.service.OrderService;

@Component
public class OrderCreatedListener {

  private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

  private final OrderService service;

  public OrderCreatedListener(OrderService service) {
    this.service = service;
  }

  @RabbitListener(queues = com.gustavooarantes.desafio_btg_pactual.config.RabbitMQConfig.ORDER_CREATED_QUEUE)
  public void listen(Message<OrderCreatedEvent> message) {
    logger.info("Message consumed: {}", message);

    service.save(message.getPayload());
  }
}
