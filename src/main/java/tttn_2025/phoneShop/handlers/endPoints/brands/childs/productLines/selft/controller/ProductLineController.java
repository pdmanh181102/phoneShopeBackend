package tttn_2025.phoneShop.handlers.endPoints.brands.childs.productLines.selft.controller;

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
import tttn_2025.phoneShop.handlers.endPoints.brands.childs.productLines.selft.dto.ProductLineDto;
import tttn_2025.phoneShop.handlers.endPoints.brands.childs.productLines.selft.service.ProductLineService;

@RestController
public class ProductLineController {
    private final ProductLineService productLineService;

    public ProductLineController(ProductLineService productLineService) {
        this.productLineService = productLineService;
    }

    ///
    /// CRUD
    ///

    @PostMapping("/brands/{brandUid}/product-lines")
    public ResponseEntity<ProductLineDto> create(
            @PathVariable(name = "brandUid") UUID brandUid,
            @RequestBody ProductLineDto dto) {
        return ResponseEntity.status(201).body(productLineService.create(brandUid, dto.getName()));
    }

    @GetMapping("/brands/{brandUid}/product-lines")
    public ResponseEntity<Page<ProductLineDto>> readAll(
            @PathVariable(name = "brandUid") UUID brandUid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        DelayHelper.Delay(500L);
        return ResponseEntity.ok(productLineService.readAll(brandUid, page, size, sortBy, direction));
    }

    @GetMapping("/product-lines/{uid}")
    public ResponseEntity<ProductLineDto> readByUid(
            @PathVariable(name = "uid") UUID uid) {
        return ResponseEntity.ok(productLineService.readByUid(uid));
    }

    @PatchMapping("/product-lines/{uid}/name")
    public ResponseEntity<ProductLineDto> updateName(
            @PathVariable(name = "uid") UUID uid,
            @RequestBody ProductLineDto dto) {
        return ResponseEntity.ok(productLineService.updateNameByUid(uid, dto.getName()));
    }

    @DeleteMapping("/product-lines/{uid}")
    public ResponseEntity<ProductLineDto> deteleByUid(
            @PathVariable(name = "uid") UUID uid) {
        return ResponseEntity.ok(productLineService.deleteByUid(uid));
    }

    ///
    /// OTHER
    ///

    @GetMapping("/brands/{brandUid}/product-lines/exists")
    public ResponseEntity<Map<String, Boolean>> nameExists(
            @PathVariable(name = "brandUid") UUID brandUid,
            @RequestParam(name = "name") String name) {
        return ResponseEntity.ok(Map.<String, Boolean>of("exists", productLineService.nameExists(brandUid, name)));
    }

}
