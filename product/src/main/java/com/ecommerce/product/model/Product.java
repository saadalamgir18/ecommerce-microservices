package com.ecommerce.product.model;

import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "products")
public class Product extends BaseModel {

    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer stockQuantity;

   private String imageUrl;

   @Builder.Default
   private Boolean active = true;


}
