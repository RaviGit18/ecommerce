package com.ravi.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name="product")
public class Product {

    @Id
    @NotNull
    @Column(name="product_Id")
	private Long productId;
    
    @NotEmpty
    @Column(name="product_Name")
	private String productName;
}
