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
@Table(name="supplier")
public class Supplier {
	
    @Id
    @NotNull
    @Column(name="supplier_Id")
	private Long supplierId;
    
    @NotEmpty
    @Column(name="supplier_Name")
	private String supplierName;
}
