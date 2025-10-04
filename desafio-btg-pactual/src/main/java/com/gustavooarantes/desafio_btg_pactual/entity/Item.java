package com.gustavooarantes.desafio_btg_pactual.entity;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

public class Item {

  private String product;

  private Integer quantity;

  private BigDecimal price;

  public String getProduct() {
    return product;
  }

  public Integer getQuantity() {
    return quantity;
  }

  @Field(targetType = FieldType.DECIMAL128)
  public BigDecimal getPrice() {
    return price;
  }

  public Item() {
  }

  public Item(String product, Integer quantity, BigDecimal price) {
    this.product = product;
    this.quantity = quantity;
    this.price = price;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
