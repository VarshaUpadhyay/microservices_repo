package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.InventoryRequestBody;
import org.example.dto.SkuDto;
import org.example.model.Inventory;
import org.example.repository.InventoryRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepo inventoryRepo;

    public List<SkuDto> isInStock(List<String> skuCodes) {
        List<Inventory> inventoryList = inventoryRepo.findAllBySkuCodeIn(skuCodes);
        List<SkuDto> list= new ArrayList<>(inventoryList.stream()
                .map(this::mapToDto).toList());
        skuCodes.stream().filter(a->list.stream().noneMatch(b-> b.getSku().equals(a)))
                .forEach(sku->list.add(SkuDto.builder().sku(sku).available(false).build()));
        return list;
    }



    private SkuDto mapToDto(Inventory inventory) {
        return SkuDto.builder().sku(inventory.getSkuCode()).available(true).build();

    }

    public void saveInventory(InventoryRequestBody inventoryRequestBody) {
        if(inventoryRequestBody!=null && inventoryRequestBody.getInventories() != null) {
            List<Inventory> inventoryList = inventoryRequestBody.getInventories();
            inventoryRepo.saveAll(inventoryList);
        }
        else
            throw new NullPointerException("Please provide at least one inventory");
    }


}
