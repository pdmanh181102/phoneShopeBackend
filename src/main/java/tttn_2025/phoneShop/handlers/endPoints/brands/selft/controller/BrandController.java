package tttn_2025.phoneShop.handlers.endPoints.brands.selft.controller;

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
import org.springframework.web.multipart.MultipartFile;

import tttn_2025.phoneShop.handlers.endPoints.brands.selft.dto.BrandDto;
import tttn_2025.phoneShop.handlers.endPoints.brands.selft.service.BrandService;

@RestController
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    ///
    /// CRUD
    ///

    @PostMapping("")
    public ResponseEntity<BrandDto> create(@RequestBody BrandDto dto) {
        return ResponseEntity.status(201).body(brandService.create(dto.getName()));
    }

    @GetMapping("")
    public ResponseEntity<Page<BrandDto>> readAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        return ResponseEntity.ok(brandService.readAll(page, size, sortBy, direction));
    }

    @GetMapping("/{uid}")
    public ResponseEntity<BrandDto> readByUid(
            @PathVariable(name = "uid") UUID uid) {
        return ResponseEntity.ok(brandService.readByUid(uid));
    }

    @PatchMapping("/{uid}/name")
    public ResponseEntity<BrandDto> updateName(
            @PathVariable(name = "uid") UUID uid,
            @RequestBody BrandDto dto) {
        return ResponseEntity.ok(brandService.updateNameByUid(uid, dto.getName()));
    }

    @PatchMapping("/{uid}/photo")
    public ResponseEntity<BrandDto> updatePhoto(
            @PathVariable(name = "uid") UUID uid,
            @RequestParam(name = "photo") MultipartFile photo) {
        return ResponseEntity.ok(brandService.updatePhotoByUid(uid, photo));
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<BrandDto> deteleByUid(
            @PathVariable(name = "uid") UUID uid) {
        return ResponseEntity.ok(brandService.deleteByUid(uid));
    }

    ///
    /// OTHER
    ///

    @GetMapping("/exists")
    public ResponseEntity<Boolean> nameExists(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(brandService.nameExists(name));
    }

}
