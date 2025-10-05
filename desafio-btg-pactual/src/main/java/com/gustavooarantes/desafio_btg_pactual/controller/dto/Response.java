package com.gustavooarantes.desafio_btg_pactual.controller.dto;

import java.util.List;
import java.util.Map;

public record Response<T>(Map<String, Object> summary, List<T> data, PaginationResponse pagination) {

}
