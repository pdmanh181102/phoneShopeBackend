package tttn_2025.phoneShop.handlers.endPoints.productStatus.selft.controller;

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

import jakarta.validation.Valid;
import tttn_2025.phoneShop.common.helpers.delay.DelayHelper;
import tttn_2025.phoneShop.handlers.endPoints.productStatus.selft.dto.ProductStatusDto;
import tttn_2025.phoneShop.handlers.endPoints.productStatus.selft.service.ProductStatusService;

@RestController
@RequestMapping("/product-status")
public class ProductStatusController {
    private final ProductStatusService productStatusService;

    public ProductStatusController(ProductStatusService productStatusService) {
        this.productStatusService = productStatusService;
    }

    ///
    /// CRUD
    ///

    @PostMapping("")
    public ResponseEntity<ProductStatusDto> create(@RequestBody ProductStatusDto dto) {
        return ResponseEntity.status(201).body(productStatusService.create(dto.getName()));
    }

    @GetMapping("")
    public ResponseEntity<Page<ProductStatusDto>> readAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        DelayHelper.Delay(500L);
        return ResponseEntity.ok(productStatusService.readAll(page, size, sortBy, direction));
    }

    @GetMapping("/{uid}")
    public ResponseEntity<ProductStatusDto> readByUid(
            @PathVariable(name = "uid") UUID uid) {
        return ResponseEntity.ok(productStatusService.readByUid(uid));
    }

    @PatchMapping("/{uid}/name")
    public ResponseEntity<ProductStatusDto> updateName(
            @PathVariable(name = "uid") UUID uid,
            @Valid @RequestBody ProductStatusDto dto) {
        return ResponseEntity.ok(productStatusService.updateNameByUid(uid, dto.getName()));
    }

    @PatchMapping("/{uid}/description")
    public ResponseEntity<ProductStatusDto> updateDescription(
            @PathVariable(name = "uid") UUID uid,
            @RequestBody ProductStatusDto dto) {
        return ResponseEntity.ok(productStatusService.updateDescriptionByUid(uid, dto.getDescription()));
    }

    @PatchMapping("/{uid}/default")
    public ResponseEntity<ProductStatusDto> updateDefaultStatus(
            @PathVariable(name = "uid") UUID uid) {
        return ResponseEntity.ok(productStatusService.updateDefaultStatusByUid(uid));
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<ProductStatusDto> deteleByUid(
            @PathVariable(name = "uid") UUID uid) {
        return ResponseEntity.ok(productStatusService.deleteByUid(uid));
    }

    ///
    /// OTHER
    ///

    @GetMapping("/exists")
    public ResponseEntity<Map<String, Boolean>> nameExists(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(Map.<String, Boolean>of("exists", productStatusService.nameExists(name)));
    }

}
