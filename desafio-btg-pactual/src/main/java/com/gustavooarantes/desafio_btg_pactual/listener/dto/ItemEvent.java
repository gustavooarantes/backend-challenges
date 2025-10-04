package com.gustavooarantes.desafio_btg_pactual.listener.dto;

import java.math.BigDecimal;

public record ItemEvent(
    String product, Integer quantity, BigDecimal price) {
}
