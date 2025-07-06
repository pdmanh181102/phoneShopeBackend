package tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tttn_2025.phoneShop.common.helpers.delay.DelayHelper;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.dto.ProductPhotoDto;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.service.ProductPhotoService;

@RestController
public class ProductPhotoController {
    private final ProductPhotoService productPhotoService;

    public ProductPhotoController(ProductPhotoService productPhotoService) {
        this.productPhotoService = productPhotoService;
    }

    ///
    /// CRUD
    ///

    @PostMapping("/products/{productUid}/product-photos")
    public ResponseEntity<ProductPhotoDto> create(
            @PathVariable(name = "productUid") UUID productUid,
            @RequestParam(name = "photo") MultipartFile photo) {
        return ResponseEntity.status(201).body(productPhotoService.create(productUid, photo));
    }

    @GetMapping("/product-photos")
    public ResponseEntity<Page<ProductPhotoDto>> readAll(
            @RequestParam(required = false) UUID productUid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        DelayHelper.Delay(500L);
        return ResponseEntity.ok(productPhotoService.readAll(productUid, page, size, sortBy, direction));
    }

    @GetMapping("/product-photos/{uid}")
    public ResponseEntity<ProductPhotoDto> readByUid(
            @PathVariable(name = "uid") UUID uid) {
        return ResponseEntity.ok(productPhotoService.readByUid(uid));
    }

    @PatchMapping("/product-photos/{uid}/is-main")
    public ResponseEntity<ProductPhotoDto> updateIsMain(
            @PathVariable(name = "uid") UUID uid) {
        return ResponseEntity.ok(productPhotoService.updateIsMain(uid));
    }

    @DeleteMapping("/product-photos/{uid}")
    public ResponseEntity<ProductPhotoDto> deteleByUid(
            @PathVariable(name = "uid") UUID uid) {
        return ResponseEntity.ok(productPhotoService.deleteByUid(uid));
    }

    ///
    /// OTHER
    ///

}
