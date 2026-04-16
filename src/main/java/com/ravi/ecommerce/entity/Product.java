package com.ravi.ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Entity representing products in the e-commerce system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "inventoryItems")
@ToString(exclude = "inventoryItems")
@Entity
@Table(name="product")
public class Product {

    @Id
    @NotNull
    @Column(name="product_Id")
	private Long productId;
    
    @NotBlank(message = "Product name cannot be blank")
    @Column(name="product_Name")
	private String productName;
    
    @OneToMany(mappedBy = "product")
    private List<Inventory> inventoryItems;
}
