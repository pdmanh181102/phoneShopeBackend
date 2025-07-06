package tttn_2025.phoneShop.handlers.endPoints.products.self.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tttn_2025.phoneShop.handlers.endPoints.products.self.dto.ProductDto;
import tttn_2025.phoneShop.handlers.endPoints.products.self.models.ProductBrandUpdateRequest;
import tttn_2025.phoneShop.handlers.endPoints.products.self.models.ProductCreateRequest;
import tttn_2025.phoneShop.handlers.endPoints.products.self.models.ProductStatusUpdateRequest;
import tttn_2025.phoneShop.handlers.endPoints.products.self.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    ///
    /// CRUD
    ///

    @PostMapping("")
    public ResponseEntity<ProductDto> create(@RequestBody ProductCreateRequest request) {
        return ResponseEntity.status(201).body(productService.create(request.getBrandUid(), request.getName()));
    }

    @GetMapping("")
    public ResponseEntity<Page<ProductDto>> readAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(required = false) List<UUID> brandUids,
            @RequestParam(required = false) List<UUID> statusUids,
            @RequestParam(required = false) List<UUID> productLineUids,
            @RequestParam(required = false) String nameLike) {
        return ResponseEntity.ok(productService.readAll(
                page, size, sortBy, direction,
                brandUids, statusUids, productLineUids, nameLike));
    }

    @GetMapping("/{uid}")
    public ResponseEntity<ProductDto> readByUid(
            @PathVariable(name = "uid") UUID uid) {
        return ResponseEntity.ok(productService.readByUid(uid));
    }

    @PatchMapping("/{uid}/name")
    public ResponseEntity<ProductDto> updateName(
            @PathVariable(name = "uid") UUID uid,
            @RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.updateNameByUid(uid, dto.getName()));
    }

    @PatchMapping("/{uid}/status")
    public ResponseEntity<ProductDto> updateStatus(
            @PathVariable(name = "uid") UUID uid,
            @RequestBody ProductStatusUpdateRequest request) {
        return ResponseEntity.ok(productService.updateStatus(uid, request.getStatusUid()));
    }

    @PatchMapping("/{uid}/brand")
    public ResponseEntity<ProductDto> updateBrand(
            @PathVariable(name = "uid") UUID uid,
            @RequestBody ProductBrandUpdateRequest request) {
        return ResponseEntity.ok(productService.updateBrand(uid, request.getBrandUid()));
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<ProductDto> deleteByUid(
            @PathVariable(name = "uid") UUID uid) {
        return ResponseEntity.ok(productService.deleteByUid(uid));
    }

    ///
    /// OTHER
    ///

    @GetMapping("/exists")
    public ResponseEntity<Map<String, Boolean>> nameExists(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(Map.<String, Boolean>of("exists", productService.nameExists(name)));
    }

}