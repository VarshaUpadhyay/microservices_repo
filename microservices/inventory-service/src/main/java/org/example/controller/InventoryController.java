package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.InventoryRequestBody;
import org.example.dto.SkuDto;
import org.example.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/{sku-codes}")
    public List<SkuDto> isInStock(@PathVariable("sku-codes") List<String> skuCodes) {
        return inventoryService.isInStock(skuCodes);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveInventory(@RequestBody InventoryRequestBody inventoryRequestBody) {
        inventoryService.saveInventory(inventoryRequestBody);
    }
}
