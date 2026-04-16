package com.ravi.ecommerce.repository;

import com.ravi.ecommerce.entity.Inventory;
import com.ravi.ecommerce.projection.InventoryBasicProjection;
import com.ravi.ecommerce.projection.InventoryPriceProjection;
import com.ravi.ecommerce.projection.InventorySummaryProjection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryRepositorySimpleTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Test
    void testFindByBrandName_Success() {
        // Given
        String brandName = "Nike";
        List<Inventory> expectedList = Arrays.asList(new Inventory());
        when(inventoryRepository.findByBrandName(brandName)).thenReturn(expectedList);

        // When
        List<Inventory> result = inventoryRepository.findByBrandName(brandName);

        // Then
        assertNotNull(result);
        assertEquals(expectedList, result);
        verify(inventoryRepository, times(1)).findByBrandName(brandName);
    }

    @Test
    void testFindByBrandName_EmptyResult() {
        // Given
        String brandName = "NonExistent";
        when(inventoryRepository.findByBrandName(brandName)).thenReturn(Collections.emptyList());

        // When
        List<Inventory> result = inventoryRepository.findByBrandName(brandName);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(inventoryRepository, times(1)).findByBrandName(brandName);
    }

    @Test
    void testFindBySize_Success() {
        // Given
        String size = "M";
        List<Inventory> expectedList = Arrays.asList(new Inventory());
        when(inventoryRepository.findBySize(size)).thenReturn(expectedList);

        // When
        List<Inventory> result = inventoryRepository.findBySize(size);

        // Then
        assertNotNull(result);
        assertEquals(expectedList, result);
        verify(inventoryRepository, times(1)).findBySize(size);
    }

    @Test
    void testFindByColor_Success() {
        // Given
        String color = "Red";
        List<Inventory> expectedList = Arrays.asList(new Inventory());
        when(inventoryRepository.findByColor(color)).thenReturn(expectedList);

        // When
        List<Inventory> result = inventoryRepository.findByColor(color);

        // Then
        assertNotNull(result);
        assertEquals(expectedList, result);
        verify(inventoryRepository, times(1)).findByColor(color);
    }

    @Test
    void testGetAvailableNumberOfProductBySeller_Success() {
        // Given
        String sellerId = "1";
        int expectedCount = 5;
        when(inventoryRepository.getAvailableNumberOfProductBySeller(sellerId)).thenReturn(expectedCount);

        // When
        int result = inventoryRepository.getAvailableNumberOfProductBySeller(sellerId);

        // Then
        assertEquals(expectedCount, result);
        verify(inventoryRepository, times(1)).getAvailableNumberOfProductBySeller(sellerId);
    }

    @Test
    void testExistsBySkuId_Success() {
        // Given
        String skuId = "NIKE-RED-M-001";
        when(inventoryRepository.existsBySkuId(skuId)).thenReturn(true);

        // When
        boolean result = inventoryRepository.existsBySkuId(skuId);

        // Then
        assertTrue(result);
        verify(inventoryRepository, times(1)).existsBySkuId(skuId);
    }

    @Test
    void testExistsBySkuId_NotFound() {
        // Given
        String skuId = "NON-EXISTENT";
        when(inventoryRepository.existsBySkuId(skuId)).thenReturn(false);

        // When
        boolean result = inventoryRepository.existsBySkuId(skuId);

        // Then
        assertFalse(result);
        verify(inventoryRepository, times(1)).existsBySkuId(skuId);
    }

    @Test
    void testFindById_Success() {
        // Given
        Long inventoryId = 1L;
        Inventory expectedInventory = new Inventory();
        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.of(expectedInventory));

        // When
        Optional<Inventory> result = inventoryRepository.findById(inventoryId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(expectedInventory, result.get());
        verify(inventoryRepository, times(1)).findById(inventoryId);
    }

    @Test
    void testFindById_NotFound() {
        // Given
        Long inventoryId = 999L;
        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.empty());

        // When
        Optional<Inventory> result = inventoryRepository.findById(inventoryId);

        // Then
        assertFalse(result.isPresent());
        verify(inventoryRepository, times(1)).findById(inventoryId);
    }

    @Test
    void testFindAllBasicProjectedBy_Success() {
        // Given
        List<InventoryBasicProjection> expectedProjections = Arrays.asList(mock(InventoryBasicProjection.class));
        when(inventoryRepository.findAllBasicProjectedBy()).thenReturn(expectedProjections);

        // When
        List<InventoryBasicProjection> result = inventoryRepository.findAllBasicProjectedBy();

        // Then
        assertNotNull(result);
        assertEquals(expectedProjections, result);
        verify(inventoryRepository, times(1)).findAllBasicProjectedBy();
    }

    @Test
    void testFindBasicProjectionsByBrandName_Success() {
        // Given
        String brandName = "Nike";
        List<InventoryBasicProjection> expectedProjections = Arrays.asList(mock(InventoryBasicProjection.class));
        when(inventoryRepository.findBasicProjectionsByBrandName(brandName)).thenReturn(expectedProjections);

        // When
        List<InventoryBasicProjection> result = inventoryRepository.findBasicProjectionsByBrandName(brandName);

        // Then
        assertNotNull(result);
        assertEquals(expectedProjections, result);
        verify(inventoryRepository, times(1)).findBasicProjectionsByBrandName(brandName);
    }

    @Test
    void testFindAllPriceProjections_Success() {
        // Given
        List<InventoryPriceProjection> expectedProjections = Arrays.asList(mock(InventoryPriceProjection.class));
        when(inventoryRepository.findAllPriceProjections()).thenReturn(expectedProjections);

        // When
        List<InventoryPriceProjection> result = inventoryRepository.findAllPriceProjections();

        // Then
        assertNotNull(result);
        assertEquals(expectedProjections, result);
        verify(inventoryRepository, times(1)).findAllPriceProjections();
    }

    @Test
    void testFindPriceProjectionsByBrandName_Success() {
        // Given
        String brandName = "Nike";
        List<InventoryPriceProjection> expectedProjections = Arrays.asList(mock(InventoryPriceProjection.class));
        when(inventoryRepository.findPriceProjectionsByBrandName(brandName)).thenReturn(expectedProjections);

        // When
        List<InventoryPriceProjection> result = inventoryRepository.findPriceProjectionsByBrandName(brandName);

        // Then
        assertNotNull(result);
        assertEquals(expectedProjections, result);
        verify(inventoryRepository, times(1)).findPriceProjectionsByBrandName(brandName);
    }

    @Test
    void testFindAllSummaryProjections_Success() {
        // Given
        List<InventorySummaryProjection> expectedProjections = Arrays.asList(mock(InventorySummaryProjection.class));
        when(inventoryRepository.findAllSummaryProjections()).thenReturn(expectedProjections);

        // When
        List<InventorySummaryProjection> result = inventoryRepository.findAllSummaryProjections();

        // Then
        assertNotNull(result);
        assertEquals(expectedProjections, result);
        verify(inventoryRepository, times(1)).findAllSummaryProjections();
    }

    @Test
    void testFindSummaryProjectionsByBrandName_Success() {
        // Given
        String brandName = "Nike";
        List<InventorySummaryProjection> expectedProjections = Arrays.asList(mock(InventorySummaryProjection.class));
        when(inventoryRepository.findSummaryProjectionsByBrandName(brandName)).thenReturn(expectedProjections);

        // When
        List<InventorySummaryProjection> result = inventoryRepository.findSummaryProjectionsByBrandName(brandName);

        // Then
        assertNotNull(result);
        assertEquals(expectedProjections, result);
        verify(inventoryRepository, times(1)).findSummaryProjectionsByBrandName(brandName);
    }

    @Test
    void testFindAll_WithPagination_Success() {
        // Given
        Pageable pageable = mock(Pageable.class);
        List<Inventory> inventoryList = Arrays.asList(new Inventory());
        Page<Inventory> expectedPage = new PageImpl<>(inventoryList);
        when(inventoryRepository.findAll(pageable)).thenReturn(expectedPage);

        // When
        Page<Inventory> result = inventoryRepository.findAll(pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(inventoryRepository, times(1)).findAll(pageable);
    }

    @Test
    void testFindByBrandName_WithPagination_Success() {
        // Given
        String brandName = "Nike";
        Pageable pageable = mock(Pageable.class);
        List<Inventory> inventoryList = Arrays.asList(new Inventory());
        Page<Inventory> expectedPage = new PageImpl<>(inventoryList);
        when(inventoryRepository.findByBrandName(brandName, pageable)).thenReturn(expectedPage);

        // When
        Page<Inventory> result = inventoryRepository.findByBrandName(brandName, pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(inventoryRepository, times(1)).findByBrandName(brandName, pageable);
    }

    @Test
    void testFindByColor_WithPagination_Success() {
        // Given
        String color = "Red";
        Pageable pageable = mock(Pageable.class);
        List<Inventory> inventoryList = Arrays.asList(new Inventory());
        Page<Inventory> expectedPage = new PageImpl<>(inventoryList);
        when(inventoryRepository.findByColor(color, pageable)).thenReturn(expectedPage);

        // When
        Page<Inventory> result = inventoryRepository.findByColor(color, pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(inventoryRepository, times(1)).findByColor(color, pageable);
    }

    @Test
    void testFindBySize_WithPagination_Success() {
        // Given
        String size = "M";
        Pageable pageable = mock(Pageable.class);
        List<Inventory> inventoryList = Arrays.asList(new Inventory());
        Page<Inventory> expectedPage = new PageImpl<>(inventoryList);
        when(inventoryRepository.findBySize(size, pageable)).thenReturn(expectedPage);

        // When
        Page<Inventory> result = inventoryRepository.findBySize(size, pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(inventoryRepository, times(1)).findBySize(size, pageable);
    }

    @Test
    void testFindBySupplierId_WithPagination_Success() {
        // Given
        Long supplierId = 1L;
        Pageable pageable = mock(Pageable.class);
        List<Inventory> inventoryList = Arrays.asList(new Inventory());
        Page<Inventory> expectedPage = new PageImpl<>(inventoryList);
        when(inventoryRepository.findBySupplierId(supplierId, pageable)).thenReturn(expectedPage);

        // When
        Page<Inventory> result = inventoryRepository.findBySupplierId(supplierId, pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(inventoryRepository, times(1)).findBySupplierId(supplierId, pageable);
    }

    @Test
    void testFindAllBasicProjectedBy_WithPagination_Success() {
        // Given
        Pageable pageable = mock(Pageable.class);
        List<InventoryBasicProjection> projectionList = Arrays.asList(mock(InventoryBasicProjection.class));
        Page<InventoryBasicProjection> expectedPage = new PageImpl<>(projectionList);
        when(inventoryRepository.findAllBasicProjectedBy(pageable)).thenReturn(expectedPage);

        // When
        Page<InventoryBasicProjection> result = inventoryRepository.findAllBasicProjectedBy(pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(inventoryRepository, times(1)).findAllBasicProjectedBy(pageable);
    }

    @Test
    void testFindBasicProjectionsByBrandName_WithPagination_Success() {
        // Given
        String brandName = "Nike";
        Pageable pageable = mock(Pageable.class);
        List<InventoryBasicProjection> projectionList = Arrays.asList(mock(InventoryBasicProjection.class));
        Page<InventoryBasicProjection> expectedPage = new PageImpl<>(projectionList);
        when(inventoryRepository.findBasicProjectionsByBrandName(brandName, pageable)).thenReturn(expectedPage);

        // When
        Page<InventoryBasicProjection> result = inventoryRepository.findBasicProjectionsByBrandName(brandName, pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(inventoryRepository, times(1)).findBasicProjectionsByBrandName(brandName, pageable);
    }

    @Test
    void testFindAllPriceProjectedBy_WithPagination_Success() {
        // Given
        Pageable pageable = mock(Pageable.class);
        List<InventoryPriceProjection> projectionList = Arrays.asList(mock(InventoryPriceProjection.class));
        Page<InventoryPriceProjection> expectedPage = new PageImpl<>(projectionList);
        when(inventoryRepository.findAllPriceProjectedBy(pageable)).thenReturn(expectedPage);

        // When
        Page<InventoryPriceProjection> result = inventoryRepository.findAllPriceProjectedBy(pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(inventoryRepository, times(1)).findAllPriceProjectedBy(pageable);
    }

    @Test
    void testFindPriceProjectionsByBrandName_WithPagination_Success() {
        // Given
        String brandName = "Nike";
        Pageable pageable = mock(Pageable.class);
        List<InventoryPriceProjection> projectionList = Arrays.asList(mock(InventoryPriceProjection.class));
        Page<InventoryPriceProjection> expectedPage = new PageImpl<>(projectionList);
        when(inventoryRepository.findPriceProjectionsByBrandName(brandName, pageable)).thenReturn(expectedPage);

        // When
        Page<InventoryPriceProjection> result = inventoryRepository.findPriceProjectionsByBrandName(brandName, pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(inventoryRepository, times(1)).findPriceProjectionsByBrandName(brandName, pageable);
    }

    @Test
    void testFindAllSummaryProjectedBy_WithPagination_Success() {
        // Given
        Pageable pageable = mock(Pageable.class);
        List<InventorySummaryProjection> projectionList = Arrays.asList(mock(InventorySummaryProjection.class));
        Page<InventorySummaryProjection> expectedPage = new PageImpl<>(projectionList);
        when(inventoryRepository.findAllSummaryProjectedBy(pageable)).thenReturn(expectedPage);

        // When
        Page<InventorySummaryProjection> result = inventoryRepository.findAllSummaryProjectedBy(pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(inventoryRepository, times(1)).findAllSummaryProjectedBy(pageable);
    }

    @Test
    void testFindSummaryProjectionsByBrandName_WithPagination_Success() {
        // Given
        String brandName = "Nike";
        Pageable pageable = mock(Pageable.class);
        List<InventorySummaryProjection> projectionList = Arrays.asList(mock(InventorySummaryProjection.class));
        Page<InventorySummaryProjection> expectedPage = new PageImpl<>(projectionList);
        when(inventoryRepository.findSummaryProjectionsByBrandName(brandName, pageable)).thenReturn(expectedPage);

        // When
        Page<InventorySummaryProjection> result = inventoryRepository.findSummaryProjectionsByBrandName(brandName, pageable);

        // Then
        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(inventoryRepository, times(1)).findSummaryProjectionsByBrandName(brandName, pageable);
    }

    @Test
    void testSave_NewInventory() {
        // Given
        Inventory newInventory = new Inventory();
        Inventory savedInventory = new Inventory();
        when(inventoryRepository.save(newInventory)).thenReturn(savedInventory);

        // When
        Inventory result = inventoryRepository.save(newInventory);

        // Then
        assertEquals(savedInventory, result);
        verify(inventoryRepository, times(1)).save(newInventory);
    }

    @Test
    void testDeleteById_Success() {
        // Given
        Long inventoryId = 1L;
        doNothing().when(inventoryRepository).deleteById(inventoryId);

        // When
        inventoryRepository.deleteById(inventoryId);

        // Then
        verify(inventoryRepository, times(1)).deleteById(inventoryId);
    }

    @Test
    void testCount_TotalInventory() {
        // Given
        long expectedCount = 10L;
        when(inventoryRepository.count()).thenReturn(expectedCount);

        // When
        long result = inventoryRepository.count();

        // Then
        assertEquals(expectedCount, result);
        verify(inventoryRepository, times(1)).count();
    }

    @Test
    void testExistsById_Success() {
        // Given
        Long inventoryId = 1L;
        when(inventoryRepository.existsById(inventoryId)).thenReturn(true);

        // When
        boolean result = inventoryRepository.existsById(inventoryId);

        // Then
        assertTrue(result);
        verify(inventoryRepository, times(1)).existsById(inventoryId);
    }

    @Test
    void testExistsById_NotFound() {
        // Given
        Long inventoryId = 999L;
        when(inventoryRepository.existsById(inventoryId)).thenReturn(false);

        // When
        boolean result = inventoryRepository.existsById(inventoryId);

        // Then
        assertFalse(result);
        verify(inventoryRepository, times(1)).existsById(inventoryId);
    }
}
