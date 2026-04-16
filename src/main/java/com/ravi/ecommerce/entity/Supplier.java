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
 * Entity representing suppliers in the e-commerce system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "inventoryItems")
@ToString(exclude = "inventoryItems")
@Entity
@Table(name="supplier")
public class Supplier {
	
    @Id
    @NotNull
    @Column(name="supplier_Id")
	private Long supplierId;
    
    @NotBlank(message = "Supplier name cannot be blank")
    @Column(name="supplier_Name")
	private String supplierName;
    
    @OneToMany(mappedBy = "supplier")
    private List<Inventory> inventoryItems;
}
