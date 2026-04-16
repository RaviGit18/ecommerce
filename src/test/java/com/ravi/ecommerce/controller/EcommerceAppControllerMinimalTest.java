package com.ravi.ecommerce.controller;

import com.ravi.ecommerce.dto.InventoryCreateDto;
import com.ravi.ecommerce.dto.InventoryDto;
import com.ravi.ecommerce.dto.InventoryUpdateDto;
import com.ravi.ecommerce.entity.Inventory;
import com.ravi.ecommerce.mapper.InventoryMapper;
import com.ravi.ecommerce.service.EcommerceAppService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EcommerceAppControllerMinimalTest {

    @Mock
    private EcommerceAppService ecommerceAppService;

    @Mock
    private InventoryMapper inventoryMapper;

    @InjectMocks
    private EcommerceAppController ecommerceAppController;

    private Inventory testInventory;
    private InventoryDto testInventoryDto;
    private InventoryCreateDto testCreateDto;
    private InventoryUpdateDto testUpdateDto;

    @BeforeEach
    void setUp() {
        testInventory = new Inventory();
        testInventory.setInventoryId(1L);
        testInventory.setBrandName("Nike");
        testInventory.setPrice(100.0);
        testInventory.setColor("Red");
        testInventory.setSize("M");
        testInventory.setSkuId("NIKE-RED-M-001");
        testInventory.setQuantity(10);
        testInventory.setProductId(1L);
        testInventory.setSupplierId(1L);

        testInventoryDto = new InventoryDto();
        testInventoryDto.setInventoryId(1L);
        testInventoryDto.setBrandName("Nike");
        testInventoryDto.setPrice(new BigDecimal("100.0"));
        testInventoryDto.setColor("Red");
        testInventoryDto.setSize("M");
        testInventoryDto.setSkuId("NIKE-RED-M-001");
        testInventoryDto.setQuantity(10);

        testCreateDto = new InventoryCreateDto();
        testCreateDto.setBrandName("Nike");
        testCreateDto.setPrice(100.0);
        testCreateDto.setColor("Red");
        testCreateDto.setSize("M");
        testCreateDto.setSkuId("NIKE-RED-M-001");
        testCreateDto.setQuantity(10);
        testCreateDto.setProductId(1L);
        testCreateDto.setSupplierId(1L);

        testUpdateDto = new InventoryUpdateDto();
        testUpdateDto.setPrice(120.0);
        testUpdateDto.setQuantity(15);
    }

    @Test
    void testGetInventoryDetailsByBrand_Success() {
        // Given
        String brandName = "Nike";
        List<Inventory> inventoryList = Arrays.asList(testInventory);
        List<InventoryDto> dtoList = Arrays.asList(testInventoryDto);
        
        when(ecommerceAppService.getInventoryDetailsByBrand(brandName)).thenReturn(inventoryList);
        when(inventoryMapper.toDtoList(inventoryList)).thenReturn(dtoList);

        // When
        ResponseEntity<List<InventoryDto>> response = ecommerceAppController.getInventoryDetailsByBrand(brandName);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(testInventoryDto, response.getBody().get(0));
        verify(ecommerceAppService, times(1)).getInventoryDetailsByBrand(brandName);
        verify(inventoryMapper, times(1)).toDtoList(inventoryList);
    }

    @Test
    void testGetInventoryDetailsByBrand_EmptyResult() {
        // Given
        String brandName = "NonExistent";
        List<Inventory> inventoryList = Collections.emptyList();
        
        when(ecommerceAppService.getInventoryDetailsByBrand(brandName)).thenReturn(inventoryList);

        // When
        ResponseEntity<List<InventoryDto>> response = ecommerceAppController.getInventoryDetailsByBrand(brandName);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(ecommerceAppService, times(1)).getInventoryDetailsByBrand(brandName);
        verify(inventoryMapper, never()).toDtoList(any());
    }

    @Test
    void testGetAvailableNumberOfProductBySeller_Success() {
        // Given
        String sellerId = "1";
        int expectedCount = 5;
        
        when(ecommerceAppService.getAvailableNumberOfProductBySeller(sellerId)).thenReturn(expectedCount);

        // When
        ResponseEntity<Integer> response = ecommerceAppController.getAvailableNumberOfProductBySeller(sellerId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCount, response.getBody());
        verify(ecommerceAppService, times(1)).getAvailableNumberOfProductBySeller(sellerId);
    }

    @Test
    void testCreateInventory_Success() {
        // Given
        when(ecommerceAppService.createInventory(any(InventoryCreateDto.class))).thenReturn(testInventory);
        when(inventoryMapper.toDto(testInventory)).thenReturn(testInventoryDto);

        // When
        ResponseEntity<InventoryDto> response = ecommerceAppController.createInventory(testCreateDto);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testInventoryDto, response.getBody());
        verify(ecommerceAppService, times(1)).createInventory(any(InventoryCreateDto.class));
        verify(inventoryMapper, times(1)).toDto(testInventory);
    }

    @Test
    void testUpdateInventory_Success() {
        // Given
        Long inventoryId = 1L;
        Inventory updatedInventory = new Inventory();
        updatedInventory.setInventoryId(inventoryId);
        updatedInventory.setPrice(120.0);
        updatedInventory.setQuantity(15);
        
        InventoryDto updatedDto = new InventoryDto();
        updatedDto.setInventoryId(inventoryId);
        updatedDto.setPrice(new BigDecimal("120.0"));
        updatedDto.setQuantity(15);
        
        when(ecommerceAppService.updateInventory(eq(inventoryId), any(InventoryUpdateDto.class))).thenReturn(updatedInventory);
        when(inventoryMapper.toDto(updatedInventory)).thenReturn(updatedDto);

        // When
        ResponseEntity<InventoryDto> response = ecommerceAppController.updateInventory(inventoryId, testUpdateDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("120.0"), response.getBody().getPrice());
        assertEquals(15, response.getBody().getQuantity());
        verify(ecommerceAppService, times(1)).updateInventory(eq(inventoryId), any(InventoryUpdateDto.class));
        verify(inventoryMapper, times(1)).toDto(updatedInventory);
    }

    @Test
    void testDeleteInventory_Success() {
        // Given
        Long inventoryId = 1L;
        doNothing().when(ecommerceAppService).deleteInventory(inventoryId);

        // When
        ResponseEntity<Void> response = ecommerceAppController.deleteInventory(inventoryId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(ecommerceAppService, times(1)).deleteInventory(inventoryId);
    }

    @Test
    void testGetInventoryById_Success() {
        // Given
        Long inventoryId = 1L;
        when(ecommerceAppService.getInventoryById(inventoryId)).thenReturn(testInventory);
        when(inventoryMapper.toDto(testInventory)).thenReturn(testInventoryDto);

        // When
        ResponseEntity<InventoryDto> response = ecommerceAppController.getInventoryById(inventoryId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testInventoryDto, response.getBody());
        verify(ecommerceAppService, times(1)).getInventoryById(inventoryId);
        verify(inventoryMapper, times(1)).toDto(testInventory);
    }

    @Test
    void testGetInventoryDetailsByColor_Success() {
        // Given
        String color = "Red";
        List<Inventory> inventoryList = Arrays.asList(testInventory);
        List<InventoryDto> dtoList = Arrays.asList(testInventoryDto);
        
        when(ecommerceAppService.getInventoryDetailsByColor(color)).thenReturn(inventoryList);
        when(inventoryMapper.toDtoList(inventoryList)).thenReturn(dtoList);

        // When
        ResponseEntity<List<InventoryDto>> response = ecommerceAppController.getInventoryDetailsByColor(color);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Red", response.getBody().get(0).getColor());
        verify(ecommerceAppService, times(1)).getInventoryDetailsByColor(color);
        verify(inventoryMapper, times(1)).toDtoList(inventoryList);
    }

    @Test
    void testGetInventoryDetailsBySize_Success() {
        // Given
        String size = "M";
        List<Inventory> inventoryList = Arrays.asList(testInventory);
        List<InventoryDto> dtoList = Arrays.asList(testInventoryDto);
        
        when(ecommerceAppService.getInventoryDetailsBySize(size)).thenReturn(inventoryList);
        when(inventoryMapper.toDtoList(inventoryList)).thenReturn(dtoList);

        // When
        ResponseEntity<List<InventoryDto>> response = ecommerceAppController.getInventoryDetailsBySize(size);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("M", response.getBody().get(0).getSize());
        verify(ecommerceAppService, times(1)).getInventoryDetailsBySize(size);
        verify(inventoryMapper, times(1)).toDtoList(inventoryList);
    }
}
