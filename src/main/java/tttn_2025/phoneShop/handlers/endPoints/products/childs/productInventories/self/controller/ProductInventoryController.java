package tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.dto.ProductInventoryDto;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.service.ProductInventoryService;

@RestController
public class ProductInventoryController {
    private final ProductInventoryService productInventoryService;

    public ProductInventoryController(ProductInventoryService productInventoryService) {
        this.productInventoryService = productInventoryService;
    }

    ///
    /// CRUD
    ///
    @GetMapping("/products/{productUid}/product-inventories")
    public ResponseEntity<ProductInventoryDto> readByProductUid(
            @PathVariable(name = "productUid") UUID productUid) {
        return ResponseEntity.ok(productInventoryService.readByProductUid(productUid));
    }

    @PatchMapping("/products/{productUid}/product-inventories/quantity/add/{number}")
    public ResponseEntity<ProductInventoryDto> updateAddQuantity(
            @PathVariable(name = "productUid") UUID productUid,
            @PathVariable(name = "number") Long number) {
        return ResponseEntity.ok(productInventoryService.updateAddQuantity(productUid, number));
    }

    @PatchMapping("/products/{productUid}/product-inventories/quantity/minus/{number}")
    public ResponseEntity<ProductInventoryDto> updateMinusQuantity(
            @PathVariable(name = "productUid") UUID productUid,
            @PathVariable(name = "number") Long number) {
        return ResponseEntity.ok(productInventoryService.updateMinusQuantity(productUid, number));
    }

    @PatchMapping("/products/{productUid}/product-inventories/price/import/{price}")
    public ResponseEntity<ProductInventoryDto> updateImportPrice(
            @PathVariable(name = "productUid") UUID productUid,
            @PathVariable(name = "price") BigDecimal price) {
        return ResponseEntity.ok(productInventoryService.updateImportPrice(productUid, price));
    }

    @PatchMapping("/products/{productUid}/product-inventories/price/sale/{price}")
    public ResponseEntity<ProductInventoryDto> updateSalePrice(
            @PathVariable(name = "productUid") UUID productUid,
            @PathVariable(name = "price") BigDecimal price) {
        return ResponseEntity.ok(productInventoryService.updateSalePrice(productUid, price));
    }

}
