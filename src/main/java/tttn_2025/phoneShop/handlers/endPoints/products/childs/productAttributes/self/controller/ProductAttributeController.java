package tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tttn_2025.phoneShop.common.helpers.delay.DelayHelper;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.dto.ProductAttributeDto;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.service.ProductAttributeService;

@RestController
public class ProductAttributeController {
    private final ProductAttributeService productAttributeService;

    public ProductAttributeController(ProductAttributeService productAttributeService) {
        this.productAttributeService = productAttributeService;
    }

    ///
    /// CRUD
    ///

    @PostMapping("/products/{productUid}/product-attributes")
    public ResponseEntity<ProductAttributeDto> create(
            @PathVariable(name = "productUid") UUID productUid,
            @RequestBody ProductAttributeDto dto) {
        return ResponseEntity.status(201).body(productAttributeService.create(productUid, dto.getName()));
    }

    @GetMapping("/product-attributes")
    public ResponseEntity<Page<ProductAttributeDto>> readAll(
            @RequestParam(required = false) UUID productUid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        DelayHelper.Delay(500L);
        return ResponseEntity.ok(productAttributeService.readAll(productUid, page, size, sortBy, direction));
    }

    @GetMapping("/product-attributes/{uid}")
    public ResponseEntity<ProductAttributeDto> readByUid(
            @PathVariable(name = "uid") UUID uid) {
        return ResponseEntity.ok(productAttributeService.readByUid(uid));
    }

    @PatchMapping("/product-attributes/{uid}/name")
    public ResponseEntity<ProductAttributeDto> updateName(
            @PathVariable(name = "uid") UUID uid,
            @RequestBody ProductAttributeDto dto) {
        return ResponseEntity.ok(productAttributeService.updateNameByUid(uid, dto.getName()));
    }

    @PatchMapping("/product-attributes/{uid}/value")
    public ResponseEntity<ProductAttributeDto> updateValue(
            @PathVariable(name = "uid") UUID uid,
            @RequestBody ProductAttributeDto dto) {
        return ResponseEntity.ok(productAttributeService.updateValueByUid(uid, dto.getValue()));
    }

    @PatchMapping("/product-attributes/{uid}/group-name")
    public ResponseEntity<ProductAttributeDto> updateGroupName(
            @PathVariable(name = "uid") UUID uid,
            @RequestBody ProductAttributeDto dto) {
        return ResponseEntity.ok(productAttributeService.updateGroupNameByUid(uid, dto.getGroupName()));
    }

    @DeleteMapping("/product-attributes/{uid}")
    public ResponseEntity<ProductAttributeDto> deteleByUid(
            @PathVariable(name = "uid") UUID uid) {
        return ResponseEntity.ok(productAttributeService.deleteByUid(uid));
    }

    ///
    /// OTHER
    ///

    @GetMapping("/products/{productUid}/product-attributes/exists")
    public ResponseEntity<Map<String, Boolean>> nameExists(
            @PathVariable(name = "productUid") UUID productUid,
            @RequestParam(name = "name") String name) {
        return ResponseEntity
                .ok(Map.<String, Boolean>of("exists", productAttributeService.nameExists(productUid, name)));
    }

}
