package com.ecommerce.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem  extends BaseModel{

   private Long productId;
    private Integer quantity;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

}