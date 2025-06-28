package tttn_2025.phoneShop.handlers.endPoints.brands.selft.service;

import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import tttn_2025.phoneShop.common.services.classes.ClassUtilService;
import tttn_2025.phoneShop.common.services.file.FileUploadService;
import tttn_2025.phoneShop.handlers.endPoints.brands.selft.dto.BrandDto;
import tttn_2025.phoneShop.handlers.endPoints.brands.selft.entity.BrandEntity;
import tttn_2025.phoneShop.handlers.endPoints.brands.selft.mapper.BrandMapper;
import tttn_2025.phoneShop.handlers.endPoints.brands.selft.repo.BrandRepo;

@Service
@Transactional
public class BrandService {
    private final BrandRepo brandRepo;
    private final BrandMapper brandMapper;
    private final FileUploadService fileUploadService;
    private final ClassUtilService classUtilService;

    public BrandService(BrandRepo brandRepo, BrandMapper brandMapper, FileUploadService fileUploadService,
            ClassUtilService classUtilService) {
        this.brandRepo = brandRepo;
        this.brandMapper = brandMapper;
        this.fileUploadService = fileUploadService;
        this.classUtilService = classUtilService;
    }

    @CacheEvict(value = { "brand.readAll", "brand.nameExists" }, allEntries = true)
    public BrandDto create(String name) {
        BrandEntity brand = new BrandEntity();
        brand.setName(name);
        return brandMapper.toDto(brandRepo.save(brand));
    }

    @Cacheable(value = "brand.readByUid", key = "#uid")
    public BrandDto readByUid(UUID uid) {
        BrandEntity brand = brandRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with uid: " + uid));
        return brandMapper.toDto(brand);
    }

    @Cacheable(value = "brand.readAll", key = "T(java.util.Objects).hash(#page, #size, #sortBy, #direction)")
    public Page<BrandDto> readAll(int page, int size, String sortBy, Sort.Direction direction) {
        int safePage = Math.max(page, 0);
        int safeSize = (size <= 0 || size > 100) ? 10 : size;
        if (!classUtilService.isValidField(sortBy, BrandEntity.class)) {
            sortBy = classUtilService.getFirstFieldName(BrandEntity.class);
        }
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(safePage, safeSize, sort);
        Page<BrandEntity> brandPage = brandRepo.findAll(pageable);
        return brandPage.map(brandMapper::toDto);
    }

    @Caching(evict = {
            @CacheEvict(value = "brand.readByUid", key = "#uid"),
            @CacheEvict(value = "brand.readAll", allEntries = true),
            @CacheEvict(value = "brand.nameExists", allEntries = true)
    })
    public BrandDto updateNameByUid(UUID uid, String name) {
        BrandEntity brand = brandRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with uid: " + uid));
        if (!Objects.equals(brand.getName(), name)) {
            brand.setName(name.trim());
        }
        return brandMapper.toDto(brandRepo.save(brand));
    }

    @Caching(evict = {
            @CacheEvict(value = "brand.readByUid", key = "#uid"),
            @CacheEvict(value = "brand.readAll", allEntries = true)
    })
    public BrandDto updatePhotoByUid(UUID uid, MultipartFile photo) {
        BrandEntity brand = brandRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with uid: " + uid));
        if (brand.getPhotoUrl() != null) {
            try {
                fileUploadService.deleteFile(brand.getPhotoUrl());
            } catch (Exception e) {
            }
        }
        try {
            String fileName = fileUploadService.uploadImage(photo);
            brand.setPhotoUrl(fileName);
            return brandMapper.toDto(brandRepo.save(brand));
        } catch (Exception e) {
            throw new RuntimeException("file upload failed!");
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "brand.readByUid", key = "#uid"),
            @CacheEvict(value = "brand.readAll", allEntries = true),
            @CacheEvict(value = "brand.nameExists", allEntries = true)
    })
    public BrandDto deleteByUid(UUID uid) {
        BrandEntity brand = brandRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with uid: " + uid));
        brandRepo.delete(brand);
        return brandMapper.toDto(brand);
    }

    @Cacheable(value = "brand.nameExists", key = "#name")
    public boolean nameExists(String name) {
        return brandRepo.existsByName(name);
    }

}
