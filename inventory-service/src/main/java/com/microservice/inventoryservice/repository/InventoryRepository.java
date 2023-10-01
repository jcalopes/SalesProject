package com.microservice.inventoryservice.repository;

import com.microservice.inventoryservice.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InventoryRepository extends MongoRepository<Inventory, String> {

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
