package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.dto.InventoryResponse;
import com.microservice.inventoryservice.model.Inventory;
import com.microservice.inventoryservice.repository.InventoryRepository;
import com.microservice.inventoryservice.dto.InventoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("GET: Find by {}", skuCode);
        List<Inventory> listInventory = inventoryRepository.findBySkuCodeIn(skuCode);
        return listInventory.stream()
                .map(inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() > 0)
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void createInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = mapToInventory(inventoryRequest);
        inventoryRepository.save(inventory);
        log.info("POST: Inventory {} created successfully.", inventory.getId());
    }

    private Inventory mapToInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = new Inventory();
        inventory.setQuantity(inventoryRequest.getQuantity());
        inventory.setSkuCode(inventoryRequest.getSkuCode());
        return inventory;
    }
}
