package com.gustavooarantes.desafio_btg_pactual.controller.dto;

import java.util.List;

public record Response<T>(List<T> data, PaginationResponse pagination) {

}
