package tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.dto.ProductInventoryDto;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.entity.ProductInventoryEntity;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.mapper.ProductInventoryMapper;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.repo.ProductInventoryRepo;
import tttn_2025.phoneShop.handlers.endPoints.products.self.entity.ProductEntity;
import tttn_2025.phoneShop.handlers.endPoints.products.self.repo.ProductRepo;

@Service
@Transactional
public class ProductInventoryService {
    private final ProductInventoryRepo productInventoryRepo;
    private final ProductRepo productRepo;
    private final ProductInventoryMapper productInventoryMapper;

    public ProductInventoryService(ProductInventoryRepo productInventoryRepo,
            ProductRepo productRepo,
            ProductInventoryMapper productInventoryMapper) {
        this.productInventoryRepo = productInventoryRepo;
        this.productRepo = productRepo;
        this.productInventoryMapper = productInventoryMapper;
    }

    @Cacheable(value = "ProductInventory.readByProductUid", key = "#uid")
    public ProductInventoryDto readByProductUid(UUID uid) {
        ProductInventoryEntity productInventory = productInventoryRepo.findByProductUid(uid)
                .orElse(null);

        if (productInventory != null) {
            return productInventoryMapper.toDto(productInventory);
        }

        ProductEntity product = productRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with uid: " + uid));
        ProductInventoryEntity pi = new ProductInventoryEntity();
        pi.setProduct(product);
        productInventoryRepo.save(pi);
        return productInventoryMapper.toDto(pi);

    }

    @Caching(evict = {
            @CacheEvict(value = "ProductInventory.readByProductUid", key = "#uid"),
            @CacheEvict(value = "product.readByUid", key = "#uid"),
            @CacheEvict(value = "product.readAll", allEntries = true)
    })
    public ProductInventoryDto updateAddQuantity(UUID uid, Long number) {
        if (number <= 0) {
            throw new RuntimeException("Cannot add number <= 0");
        }
        ProductInventoryEntity productInventory = productInventoryRepo.findByProductUid(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product inventory not found with uid: " + uid));
        Long newTotalItem = productInventory.getTotalItem() + number;
        Long newLeftItem = productInventory.getLeftItem() + number;
        productInventory.setTotalItem(newTotalItem);
        productInventory.setLeftItem(newLeftItem);
        return productInventoryMapper.toDto(productInventory);
    }

    @Caching(evict = {
            @CacheEvict(value = "ProductInventory.readByProductUid", key = "#uid"),
            @CacheEvict(value = "product.readByUid", key = "#uid"),
            @CacheEvict(value = "product.readAll", allEntries = true)
    })
    public ProductInventoryDto updateMinusQuantity(UUID uid, Long number) {
        if (number <= 0) {
            throw new RuntimeException("Cannot sub number >= 0");
        }
        ProductInventoryEntity productInventory = productInventoryRepo.findByProductUid(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product inventory not found with uid: " + uid));
        if (productInventory.getLeftItem() - number < 0) {
            throw new RuntimeException("cannot sub item grethan left item");
        }
        Long newLeftItem = productInventory.getLeftItem() - number;
        productInventory.setLeftItem(newLeftItem);
        return productInventoryMapper.toDto(productInventory);
    }

    @Caching(evict = {
            @CacheEvict(value = "ProductInventory.readByProductUid", key = "#uid"),
            @CacheEvict(value = "product.readByUid", key = "#uid"),
            @CacheEvict(value = "product.readAll", allEntries = true)
    })
    public ProductInventoryDto updateImportPrice(UUID uid, BigDecimal newPrice) {

        if (newPrice.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new RuntimeException("Import price cannot be grethan 0");
        }
        ProductInventoryEntity productInventory = productInventoryRepo.findByProductUid(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product inventory not found with uid: " + uid));
        productInventory.setImportPrice(newPrice);
        return productInventoryMapper.toDto(productInventory);
    }

    @Caching(evict = {
            @CacheEvict(value = "ProductInventory.readByProductUid", key = "#uid"),
            @CacheEvict(value = "product.readByUid", key = "#uid"),
            @CacheEvict(value = "product.readAll", allEntries = true)
    })
    public ProductInventoryDto updateSalePrice(UUID uid, BigDecimal newPrice) {

        if (newPrice.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new RuntimeException("Import price cannot be grethan 0");
        }
        ProductInventoryEntity productInventory = productInventoryRepo.findByProductUid(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product inventory not found with uid: " + uid));
        productInventory.setSalePrice(newPrice);
        return productInventoryMapper.toDto(productInventory);
    }

}
