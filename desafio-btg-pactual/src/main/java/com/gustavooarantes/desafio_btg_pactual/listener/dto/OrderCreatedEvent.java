package com.gustavooarantes.desafio_btg_pactual.listener.dto;

import java.util.List;

public record OrderCreatedEvent(
    Long orderId, Long customerId, List<ItemEvent> items) {
}
