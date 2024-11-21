package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Inventory;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequestBody {
    private List<Inventory> inventories;
}
