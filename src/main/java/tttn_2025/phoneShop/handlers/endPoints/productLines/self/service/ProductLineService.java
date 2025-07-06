package tttn_2025.phoneShop.handlers.endPoints.productLines.self.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import tttn_2025.phoneShop.common.services.classes.ClassUtilService;
import tttn_2025.phoneShop.handlers.endPoints.brands.self.entity.BrandEntity;
import tttn_2025.phoneShop.handlers.endPoints.brands.self.repo.BrandRepo;
import tttn_2025.phoneShop.handlers.endPoints.productLines.self.dto.ProductLineDto;
import tttn_2025.phoneShop.handlers.endPoints.productLines.self.entity.ProductLineEntity;
import tttn_2025.phoneShop.handlers.endPoints.productLines.self.mapper.ProductLineMapper;
import tttn_2025.phoneShop.handlers.endPoints.productLines.self.repo.ProductLineRepo;
import tttn_2025.phoneShop.handlers.endPoints.productLines.self.specification.ProductLineSpecification;

@Service
@Transactional
public class ProductLineService {
    private final BrandRepo brandRepo;
    private final ProductLineRepo productLineRepo;
    private final ProductLineMapper productLineMapper;
    private final ClassUtilService classUtilService;

    public ProductLineService(
            BrandRepo brandRepo,
            ProductLineRepo productLineRepo,
            ProductLineMapper productLineMapper,
            ClassUtilService classUtilService) {
        this.brandRepo = brandRepo;
        this.productLineRepo = productLineRepo;
        this.productLineMapper = productLineMapper;
        this.classUtilService = classUtilService;
    }

    @CacheEvict(value = { "productLine.readAll", "productLine.nameExists" }, allEntries = true)
    public ProductLineDto create(UUID brandUid, String name) {
        BrandEntity brand = brandRepo.findById(brandUid)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with uid: " + brandUid));
        ProductLineEntity productLine = new ProductLineEntity();
        productLine.setBrand(brand);
        productLine.setName(name);
        return productLineMapper.toDto(productLineRepo.save(productLine));
    }

    @Cacheable(value = "productLine.readByUid", key = "#uid")
    public ProductLineDto readByUid(UUID uid) {
        ProductLineEntity productLine = productLineRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product line not found with uid: " + uid));
        return productLineMapper.toDto(productLine);
    }

    @Cacheable(value = "productLine.readAll")
    public Page<ProductLineDto> readAll(List<UUID> brandUids, int page, int size, String sortBy,
            Sort.Direction direction) {
        int safePage = Math.max(page, 0);
        int safeSize = (size <= 0 || size > 100) ? 10 : size;
        if (!classUtilService.isValidField(sortBy, ProductLineEntity.class)) {
            sortBy = classUtilService.getFirstFieldName(ProductLineEntity.class);
        }
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(safePage, safeSize, sort);
        Specification<ProductLineEntity> spec = Specification
                .where(ProductLineSpecification.hasBrandUids(brandUids));

        Page<ProductLineEntity> entities = productLineRepo.findAll(spec, pageable);
        return entities.map(productLineMapper::toDto);
    }

    @Caching(evict = {
            @CacheEvict(value = "productLine.readByUid", key = "#uid"),
            @CacheEvict(value = "productLine.readAll", allEntries = true),
            @CacheEvict(value = "productLine.nameExists", allEntries = true)
    })
    public ProductLineDto updateNameByUid(UUID uid, String name) {
        ProductLineEntity productLine = productLineRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product line not found with uid: " + uid));
        if (!Objects.equals(productLine.getName(), name)) {
            productLine.setName(name.trim());
        }
        return productLineMapper.toDto(productLineRepo.save(productLine));
    }

    @Caching(evict = {
            @CacheEvict(value = "productLine.readByUid", key = "#uid"),
            @CacheEvict(value = "productLine.readAll", allEntries = true),
            @CacheEvict(value = "productLine.nameExists", allEntries = true)
    })
    public ProductLineDto deleteByUid(UUID uid) {
        ProductLineEntity productLine = productLineRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product line not found with uid: " + uid));
        productLineRepo.delete(productLine);
        return productLineMapper.toDto(productLine);
    }

    @Cacheable(value = "productLine.nameExists", key = "T(java.util.Objects).hash(#brandUid, #name)")
    public boolean nameExists(UUID brandUid, String name) {
        BrandEntity brand = brandRepo.findById(brandUid)
                .orElseThrow(() -> new EntityNotFoundException("brand not found with uid: " + brandUid));
        return productLineRepo.existsByNameAndBrand(name, brand);
    }

}
