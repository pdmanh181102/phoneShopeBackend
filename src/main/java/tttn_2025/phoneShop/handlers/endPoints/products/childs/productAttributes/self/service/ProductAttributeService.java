package tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.service;

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

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import tttn_2025.phoneShop.common.services.classes.ClassUtilService;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.dto.ProductAttributeDto;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.entity.ProductAttributeEntity;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.mapper.ProductAttributeMapper;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.repo.ProductAttributeRepo;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.specification.ProductAttributeSpecification;
import tttn_2025.phoneShop.handlers.endPoints.products.self.entity.ProductEntity;
import tttn_2025.phoneShop.handlers.endPoints.products.self.repo.ProductRepo;

@Service
@Transactional
public class ProductAttributeService {
    private final ProductAttributeRepo productAttributeRepo;
    private final ProductRepo productRepo;
    private final ProductAttributeMapper productAttributeMapper;
    private final ClassUtilService classUtilService;

    public ProductAttributeService(ProductAttributeRepo productAttributeRepo, ProductRepo productRepo,
            ProductAttributeMapper productAttributeMapper,
            ClassUtilService classUtilService) {
        this.productAttributeRepo = productAttributeRepo;
        this.productRepo = productRepo;
        this.productAttributeMapper = productAttributeMapper;
        this.classUtilService = classUtilService;
    }

    @Caching(evict = {
            @CacheEvict(value = "productAttribute.readAll", allEntries = true),
            @CacheEvict(value = "productAttribute.nameExists", allEntries = true),
            @CacheEvict(value = "product.readAll", allEntries = true),
            @CacheEvict(value = "product.readByUid", key = "#productUid"),
    })
    public ProductAttributeDto create(UUID productUid, String name) {
        ProductEntity product = productRepo.findById(productUid)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with uid: " + productUid));
        ProductAttributeEntity productAttribute = new ProductAttributeEntity();
        productAttribute.setProduct(product);
        productAttribute.setName(name);
        return productAttributeMapper.toDto(productAttributeRepo.save(productAttribute));
    }

    @Cacheable(value = "productAttribute.readByUid", key = "#uid")
    public ProductAttributeDto readByUid(UUID uid) {
        ProductAttributeEntity productAttribute = productAttributeRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product attribute not found with uid: " + uid));
        return productAttributeMapper.toDto(productAttribute);
    }

    @Cacheable(value = "productAttribute.readAll")
    public Page<ProductAttributeDto> readAll(UUID productUid, int page, int size, String sortBy,
            Sort.Direction direction) {
        int safePage = Math.max(page, 0);
        int safeSize = (size <= 0 || size > 100) ? 10 : size;
        if (!classUtilService.isValidField(sortBy, ProductAttributeEntity.class)) {
            sortBy = classUtilService.getFirstFieldName(ProductAttributeEntity.class);
        }
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(safePage, safeSize, sort);
        Specification<ProductAttributeEntity> spec = Specification
                .where(ProductAttributeSpecification.hasProductUid(productUid));

        Page<ProductAttributeEntity> entities = productAttributeRepo.findAll(spec, pageable);
        return entities.map(productAttributeMapper::toDto);
    }

    @Caching(evict = {
            @CacheEvict(value = "productAttribute.readByUid", key = "#uid"),
            @CacheEvict(value = "productAttribute.readAll", allEntries = true),
            @CacheEvict(value = "productAttribute.nameExists", allEntries = true),
            @CacheEvict(value = "product.readAll", allEntries = true),
            @CacheEvict(value = "product.readByUid", allEntries = true),
    })
    public ProductAttributeDto updateNameByUid(UUID uid, String name) {
        ProductAttributeEntity productAttribute = productAttributeRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product attribute not found with uid: " + uid));
        if (!Objects.equals(productAttribute.getName(), name)) {
            productAttribute.setName(name.trim());
        }
        return productAttributeMapper.toDto(productAttributeRepo.save(productAttribute));
    }

    @Caching(evict = {
            @CacheEvict(value = "productAttribute.readByUid", key = "#uid"),
            @CacheEvict(value = "productAttribute.readAll", allEntries = true),
            @CacheEvict(value = "productAttribute.nameExists", allEntries = true),
            @CacheEvict(value = "product.readAll", allEntries = true),
            @CacheEvict(value = "product.readByUid", allEntries = true),
    })
    public ProductAttributeDto updateValueByUid(UUID uid, String value) {
        ProductAttributeEntity productAttribute = productAttributeRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product attribute not found with uid: " + uid));
        if (!Objects.equals(productAttribute.getValue(), value)) {
            productAttribute.setValue(value.trim());
        }
        return productAttributeMapper.toDto(productAttributeRepo.save(productAttribute));
    }

    @Caching(evict = {
            @CacheEvict(value = "productAttribute.readByUid", key = "#uid"),
            @CacheEvict(value = "productAttribute.readAll", allEntries = true),
            @CacheEvict(value = "productAttribute.nameExists", allEntries = true),
            @CacheEvict(value = "product.readAll", allEntries = true),
            @CacheEvict(value = "product.readByUid", allEntries = true),
    })
    public ProductAttributeDto updateGroupNameByUid(UUID uid, String groupName) {
        ProductAttributeEntity productAttribute = productAttributeRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product attribute not found with uid: " + uid));
        if (!Objects.equals(productAttribute.getGroupName(), groupName)) {
            productAttribute.setGroupName(groupName.trim());
        }
        return productAttributeMapper.toDto(productAttributeRepo.save(productAttribute));
    }

    @Caching(evict = {
            @CacheEvict(value = "productAttribute.readByUid", key = "#uid"),
            @CacheEvict(value = "productAttribute.readAll", allEntries = true),
            @CacheEvict(value = "productAttribute.nameExists", allEntries = true),
            @CacheEvict(value = "product.readAll", allEntries = true),
            @CacheEvict(value = "product.readByUid", allEntries = true),
    })
    public ProductAttributeDto deleteByUid(UUID uid) {
        ProductAttributeEntity productAttribute = productAttributeRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product attribute not found with uid: " + uid));
        productAttributeRepo.delete(productAttribute);
        return productAttributeMapper.toDto(productAttribute);
    }

    @Cacheable(value = "productAttribute.nameExists", key = "T(java.util.Objects).hash(#productUid, #name)")
    public boolean nameExists(UUID productUid, String name) {
        ProductEntity product = productRepo.findById(productUid)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with uid: " + productUid));
        return productAttributeRepo.existsByNameAndProduct(name, product);
    }

}
