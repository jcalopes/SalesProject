package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.model.Inventory;
import com.microservice.inventoryservice.repository.InventoryRepository;
import com.microservice.inventoryservice.dto.InventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode){
        log.info("GET: Find by {}", skuCode);
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    @Transactional
    public void createInventory(InventoryDto inventoryDto){
        Inventory inventory = mapToInventory(inventoryDto);
        inventoryRepository.save(inventory);
        log.info("POST: Inventory {} created successfully.",inventory.getId());
    }

    private Inventory mapToInventory(InventoryDto inventoryDto) {
        Inventory inventory = new Inventory();
        inventory.setQuantity(inventoryDto.getQuantity());
        inventory.setSkuCode(inventoryDto.getSkuCode());
        return inventory;
    }
}
