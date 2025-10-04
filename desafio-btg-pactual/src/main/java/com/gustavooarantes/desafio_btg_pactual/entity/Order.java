package com.gustavooarantes.desafio_btg_pactual.entity;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "tb_orders")
public class Order {

  @MongoId
  private Long id;

  @Indexed(name = "customer_id_index")
  private Long customerId;

  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal total;

  private List<Item> items;

  public Long getId() {
    return id;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public List<Item> getItems() {
    return items;
  }

  public Order() {
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }
}
