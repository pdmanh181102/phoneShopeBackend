package tttn_2025.phoneShop.handlers.endPoints.productStatus.selft.service;

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

import jakarta.persistence.EntityNotFoundException;
import tttn_2025.phoneShop.common.services.classes.ClassUtilService;
import tttn_2025.phoneShop.handlers.endPoints.productStatus.selft.dto.ProductStatusDto;
import tttn_2025.phoneShop.handlers.endPoints.productStatus.selft.entity.ProductStatusEntity;
import tttn_2025.phoneShop.handlers.endPoints.productStatus.selft.mapper.ProductStatusMapper;
import tttn_2025.phoneShop.handlers.endPoints.productStatus.selft.repo.ProductStatusRepo;

@Service
@Transactional
public class ProductStatusService {
    private final ProductStatusRepo productStatusRepo;
    private final ProductStatusMapper productStatusMapper;
    private final ClassUtilService classUtilService;

    public ProductStatusService(
            ProductStatusRepo productStatusRepo,
            ProductStatusMapper productStatusMapper,
            ClassUtilService classUtilService) {
        this.productStatusRepo = productStatusRepo;
        this.productStatusMapper = productStatusMapper;
        this.classUtilService = classUtilService;
    }

    @CacheEvict(value = { "productStatus.readAll", "productStatus.nameExists" }, allEntries = true)
    public ProductStatusDto create(String name) {
        ProductStatusEntity productStatus = new ProductStatusEntity();
        productStatus.setName(name);
        return productStatusMapper.toDto(productStatusRepo.save(productStatus));
    }

    @Cacheable(value = "productStatus.readByUid", key = "#uid")
    public ProductStatusDto readByUid(UUID uid) {
        ProductStatusEntity productStatus = productStatusRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product status not found with uid: " + uid));
        return productStatusMapper.toDto(productStatus);
    }

    @Cacheable(value = "productStatus.readAll", key = "T(java.util.Objects).hash(#page, #size, #sortBy, #direction)")
    public Page<ProductStatusDto> readAll(int page, int size, String sortBy, Sort.Direction direction) {
        int safePage = Math.max(page, 0);
        int safeSize = (size <= 0 || size > 100) ? 10 : size;
        if (!classUtilService.isValidField(sortBy, ProductStatusEntity.class)) {
            sortBy = classUtilService.getFirstFieldName(ProductStatusEntity.class);
        }
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(safePage, safeSize, sort);
        Page<ProductStatusEntity> productStatusPage = productStatusRepo.findAll(pageable);
        return productStatusPage.map(productStatusMapper::toDto);
    }

    @Caching(evict = {
            @CacheEvict(value = "productStatus.readByUid", key = "#uid"),
            @CacheEvict(value = "productStatus.readAll", allEntries = true),
            @CacheEvict(value = "productStatus.nameExists", allEntries = true)
    })
    public ProductStatusDto updateNameByUid(UUID uid, String name) {
        ProductStatusEntity productStatus = productStatusRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product status not found with uid: " + uid));
        if (!Objects.equals(productStatus.getName(), name)) {
            productStatus.setName(name.trim());
        }
        return productStatusMapper.toDto(productStatusRepo.save(productStatus));
    }

    @Caching(evict = {
            @CacheEvict(value = "productStatus.readByUid", key = "#uid"),
            @CacheEvict(value = "productStatus.readAll", allEntries = true),
            @CacheEvict(value = "productStatus.nameExists", allEntries = true)
    })
    public ProductStatusDto updateDescriptionByUid(UUID uid, String description) {
        ProductStatusEntity productStatus = productStatusRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product status not found with uid: " + uid));
        if (!Objects.equals(productStatus.getDescription(), description)) {
            productStatus.setDescription(description.trim());
        }
        return productStatusMapper.toDto(productStatusRepo.save(productStatus));
    }

    @Caching(evict = {
            @CacheEvict(value = "productStatus.readByUid", key = "#uid"),
            @CacheEvict(value = "productStatus.readAll", allEntries = true),
            @CacheEvict(value = "productStatus.nameExists", allEntries = true)
    })
    public ProductStatusDto updateDefaultStatusByUid(UUID uid) {
        ProductStatusEntity productStatus = productStatusRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product status not found with uid: " + uid));
        productStatusRepo.updateAllIsDefaultFalseExcept(uid);
        productStatus.setIsDefault(true);
        return productStatusMapper.toDto(productStatusRepo.save(productStatus));
    }

    @Caching(evict = {
            @CacheEvict(value = "productStatus.readByUid", key = "#uid"),
            @CacheEvict(value = "productStatus.readAll", allEntries = true),
            @CacheEvict(value = "productStatus.nameExists", allEntries = true)
    })
    public ProductStatusDto deleteByUid(UUID uid) {
        ProductStatusEntity productStatus = productStatusRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product status not found with uid: " + uid));
        productStatusRepo.delete(productStatus);
        return productStatusMapper.toDto(productStatus);
    }

    @Cacheable(value = "productStatus.nameExists", key = "#name")
    public boolean nameExists(String name) {
        return productStatusRepo.existsByName(name);
    }

}
