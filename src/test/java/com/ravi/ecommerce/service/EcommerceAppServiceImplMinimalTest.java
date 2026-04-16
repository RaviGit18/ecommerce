package com.ravi.ecommerce.service;

import com.ravi.ecommerce.dto.InventoryCreateDto;
import com.ravi.ecommerce.dto.InventoryUpdateDto;
import com.ravi.ecommerce.entity.Inventory;
import com.ravi.ecommerce.exception.BaseException;
import com.ravi.ecommerce.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EcommerceAppServiceImplMinimalTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private EcommerceAppServiceImpl ecommerceAppService;

    private Inventory testInventory;
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
        when(inventoryRepository.findByBrandName(brandName)).thenReturn(inventoryList);

        // When
        List<Inventory> result = ecommerceAppService.getInventoryDetailsByBrand(brandName);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testInventory, result.get(0));
        verify(inventoryRepository, times(1)).findByBrandName(brandName);
    }

    @Test
    void testGetInventoryDetailsByBrand_EmptyResult() {
        // Given
        String brandName = "NonExistent";
        when(inventoryRepository.findByBrandName(brandName)).thenReturn(Collections.emptyList());

        // When & Then
        BaseException exception = assertThrows(BaseException.class, () -> {
            ecommerceAppService.getInventoryDetailsByBrand(brandName);
        });
        assertEquals("Inventory does not exist!", exception.getMessage());
        assertEquals(30, exception.getCode());
        verify(inventoryRepository, times(1)).findByBrandName(brandName);
    }

    @Test
    void testGetAvailableNumberOfProductBySeller_Success() {
        // Given
        String sellerId = "1";
        int expectedCount = 5;
        when(inventoryRepository.getAvailableNumberOfProductBySeller(sellerId)).thenReturn(expectedCount);

        // When
        int result = ecommerceAppService.getAvailableNumberOfProductBySeller(sellerId);

        // Then
        assertEquals(expectedCount, result);
        verify(inventoryRepository, times(1)).getAvailableNumberOfProductBySeller(sellerId);
    }

    @Test
    void testCreateInventory_Success() {
        // Given
        when(inventoryRepository.existsBySkuId(testCreateDto.getSkuId())).thenReturn(false);
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(testInventory);

        // When
        Inventory result = ecommerceAppService.createInventory(testCreateDto);

        // Then
        assertNotNull(result);
        assertEquals(testInventory, result);
        verify(inventoryRepository, times(1)).existsBySkuId(testCreateDto.getSkuId());
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void testCreateInventory_DuplicateSku() {
        // Given
        when(inventoryRepository.existsBySkuId(testCreateDto.getSkuId())).thenReturn(true);

        // When & Then
        BaseException exception = assertThrows(BaseException.class, () -> {
            ecommerceAppService.createInventory(testCreateDto);
        });
        assertEquals("Inventory with SKU ID NIKE-RED-M-001 already exists", exception.getMessage());
        assertEquals(409, exception.getCode());
        verify(inventoryRepository, times(1)).existsBySkuId(testCreateDto.getSkuId());
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void testUpdateInventory_Success() {
        // Given
        Long inventoryId = 1L;
        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.of(testInventory));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(testInventory);

        // When
        Inventory result = ecommerceAppService.updateInventory(inventoryId, testUpdateDto);

        // Then
        assertNotNull(result);
        assertEquals(testInventory, result);
        verify(inventoryRepository, times(1)).findById(inventoryId);
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void testUpdateInventory_NotFound() {
        // Given
        Long inventoryId = 999L;
        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.empty());

        // When & Then
        BaseException exception = assertThrows(BaseException.class, () -> {
            ecommerceAppService.updateInventory(inventoryId, testUpdateDto);
        });
        assertEquals("Inventory does not exist!", exception.getMessage());
        assertEquals(30, exception.getCode());
        verify(inventoryRepository, times(1)).findById(inventoryId);
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void testDeleteInventory_Success() {
        // Given
        Long inventoryId = 1L;
        when(inventoryRepository.existsById(inventoryId)).thenReturn(true);

        // When
        ecommerceAppService.deleteInventory(inventoryId);

        // Then
        verify(inventoryRepository, times(1)).existsById(inventoryId);
        verify(inventoryRepository, times(1)).deleteById(inventoryId);
    }

    @Test
    void testDeleteInventory_NotFound() {
        // Given
        Long inventoryId = 999L;
        when(inventoryRepository.existsById(inventoryId)).thenReturn(false);

        // When & Then
        BaseException exception = assertThrows(BaseException.class, () -> {
            ecommerceAppService.deleteInventory(inventoryId);
        });
        assertEquals("Inventory does not exist!", exception.getMessage());
        assertEquals(30, exception.getCode());
        verify(inventoryRepository, times(1)).existsById(inventoryId);
        verify(inventoryRepository, never()).deleteById(inventoryId);
    }

    @Test
    void testGetInventoryById_Success() {
        // Given
        Long inventoryId = 1L;
        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.of(testInventory));

        // When
        Inventory result = ecommerceAppService.getInventoryById(inventoryId);

        // Then
        assertNotNull(result);
        assertEquals(testInventory, result);
        verify(inventoryRepository, times(1)).findById(inventoryId);
    }

    @Test
    void testGetInventoryById_NotFound() {
        // Given
        Long inventoryId = 999L;
        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.empty());

        // When & Then
        BaseException exception = assertThrows(BaseException.class, () -> {
            ecommerceAppService.getInventoryById(inventoryId);
        });
        assertEquals("Inventory does not exist!", exception.getMessage());
        assertEquals(30, exception.getCode());
        verify(inventoryRepository, times(1)).findById(inventoryId);
    }

    @Test
    void testGetAllInventoryPaginated_Success() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<Inventory> inventoryList = Arrays.asList(testInventory);
        Page<Inventory> inventoryPage = new PageImpl<>(inventoryList, pageable, 1);
        when(inventoryRepository.findAll(pageable)).thenReturn(inventoryPage);

        // When
        Page<Inventory> result = ecommerceAppService.getAllInventoryPaginated(pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(inventoryRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetInventoryByBrandPaginated_Success() {
        // Given
        String brandName = "Nike";
        Pageable pageable = PageRequest.of(0, 10);
        List<Inventory> inventoryList = Arrays.asList(testInventory);
        Page<Inventory> inventoryPage = new PageImpl<>(inventoryList, pageable, 1);
        when(inventoryRepository.findByBrandName(brandName, pageable)).thenReturn(inventoryPage);

        // When
        Page<Inventory> result = ecommerceAppService.getInventoryByBrandPaginated(brandName, pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(inventoryRepository, times(1)).findByBrandName(brandName, pageable);
    }

    @Test
    void testGetInventoryByBrandPaginated_EmptyResult() {
        // Given
        String brandName = "NonExistent";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Inventory> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(inventoryRepository.findByBrandName(brandName, pageable)).thenReturn(emptyPage);

        // When & Then
        BaseException exception = assertThrows(BaseException.class, () -> {
            ecommerceAppService.getInventoryByBrandPaginated(brandName, pageable);
        });
        assertEquals("Inventory does not exist!", exception.getMessage());
        assertEquals(30, exception.getCode());
        verify(inventoryRepository, times(1)).findByBrandName(brandName, pageable);
    }

    @Test
    void testGetInventoryDetailsBySize_Success() {
        // Given
        String size = "M";
        List<Inventory> inventoryList = Arrays.asList(testInventory);
        when(inventoryRepository.findBySize(size)).thenReturn(inventoryList);

        // When
        List<Inventory> result = ecommerceAppService.getInventoryDetailsBySize(size);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testInventory, result.get(0));
        verify(inventoryRepository, times(1)).findBySize(size);
    }

    @Test
    void testGetInventoryDetailsByColor_Success() {
        // Given
        String color = "Red";
        List<Inventory> inventoryList = Arrays.asList(testInventory);
        when(inventoryRepository.findByColor(color)).thenReturn(inventoryList);

        // When
        List<Inventory> result = ecommerceAppService.getInventoryDetailsByColor(color);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testInventory, result.get(0));
        verify(inventoryRepository, times(1)).findByColor(color);
    }

    @Test
    void testGetInventoryDetailsBySize_EmptyResult() {
        // Given
        String size = "XXL";
        when(inventoryRepository.findBySize(size)).thenReturn(Collections.emptyList());

        // When & Then
        BaseException exception = assertThrows(BaseException.class, () -> {
            ecommerceAppService.getInventoryDetailsBySize(size);
        });
        assertEquals("Inventory does not exist!", exception.getMessage());
        assertEquals(30, exception.getCode());
        verify(inventoryRepository, times(1)).findBySize(size);
    }

    @Test
    void testGetInventoryDetailsByColor_EmptyResult() {
        // Given
        String color = "Yellow";
        when(inventoryRepository.findByColor(color)).thenReturn(Collections.emptyList());

        // When & Then
        BaseException exception = assertThrows(BaseException.class, () -> {
            ecommerceAppService.getInventoryDetailsByColor(color);
        });
        assertEquals("Inventory does not exist!", exception.getMessage());
        assertEquals(30, exception.getCode());
        verify(inventoryRepository, times(1)).findByColor(color);
    }
}
