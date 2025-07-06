package tttn_2025.phoneShop.handlers.endPoints.products.self.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
import tttn_2025.phoneShop.common.services.file.FileUploadService;
import tttn_2025.phoneShop.handlers.endPoints.brands.self.entity.BrandEntity;
import tttn_2025.phoneShop.handlers.endPoints.brands.self.repo.BrandRepo;
import tttn_2025.phoneShop.handlers.endPoints.productLines.self.dto.ProductLineDto;
import tttn_2025.phoneShop.handlers.endPoints.productLines.self.entity.ProductLineEntity;
import tttn_2025.phoneShop.handlers.endPoints.productLines.self.mapper.ProductLineMapper;
import tttn_2025.phoneShop.handlers.endPoints.productLines.self.repo.ProductLineRepo;
import tttn_2025.phoneShop.handlers.endPoints.productStatus.self.entity.ProductStatusEntity;
import tttn_2025.phoneShop.handlers.endPoints.productStatus.self.repo.ProductStatusRepo;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.repo.ProductInventoryRepo;
import tttn_2025.phoneShop.handlers.endPoints.products.self.dto.ProductDto;
import tttn_2025.phoneShop.handlers.endPoints.products.self.entity.ProductEntity;
import tttn_2025.phoneShop.handlers.endPoints.products.self.mapper.ProductMapper;
import tttn_2025.phoneShop.handlers.endPoints.products.self.repo.ProductRepo;
import tttn_2025.phoneShop.handlers.endPoints.products.self.specification.ProductSpecification;

@Service
@Transactional
public class ProductService {
        private final ProductRepo productRepo;
        private final BrandRepo brandRepo;
        private final ProductLineRepo productLineRepo;
        private final ProductStatusRepo productStatusRepo;
        private final ProductMapper productMapper;
        private final ProductLineMapper productLineMapper;
        private final FileUploadService fileUploadService;
        private final ClassUtilService classUtilService;
        private final ProductInventoryRepo productInventoryRepo;

        public ProductService(
                        ProductRepo productRepo,
                        BrandRepo brandRepo,
                        ProductLineRepo productLineRepo,
                        ProductStatusRepo productStatusRepo,
                        ProductMapper productMapper,
                        ProductLineMapper productLineMapper,
                        FileUploadService fileUploadService,
                        ClassUtilService classUtilService,
                        ProductInventoryRepo productInventoryRepo) {
                this.productRepo = productRepo;
                this.brandRepo = brandRepo;
                this.productLineRepo = productLineRepo;
                this.productStatusRepo = productStatusRepo;
                this.productMapper = productMapper;
                this.productLineMapper = productLineMapper;
                this.fileUploadService = fileUploadService;
                this.classUtilService = classUtilService;
                this.productInventoryRepo = productInventoryRepo;
        }

        @CacheEvict(value = { "product.readAll", "product.nameExists" }, allEntries = true)
        public ProductDto create(UUID brandUid, String name) {
                ProductStatusEntity productStatus = productStatusRepo.findByIsDefault(true)
                                .orElseThrow(() -> new EntityNotFoundException("Has no product status default"));
                BrandEntity brand = brandRepo.findById(brandUid)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Brand not found with uid: " + brandUid));
                ProductEntity product = new ProductEntity();
                product.setName(name);
                product.setBrand(brand);
                product.setStatus(productStatus);

                return productMapper.toDto(productRepo.save(product));
        }

        @Cacheable(value = "product.readByUid", key = "#uid")
        public ProductDto readByUid(UUID uid) {
                ProductEntity product = productRepo.findById(uid)
                                .orElseThrow(() -> new EntityNotFoundException("Product not found with uid: " + uid));
                return productMapper.toDto(product);
        }

        @Cacheable(value = "product.readAll")
        public Page<ProductDto> readAll(
                        int page,
                        int size,
                        String sortBy,
                        Sort.Direction direction,
                        List<UUID> brandUids,
                        List<UUID> statusUids,
                        List<UUID> productLineUids,
                        String nameLike) {

                // Validate và thiết lập các tham số an toàn
                int safePage = Math.max(page, 0);
                int safeSize = (size <= 0 || size > 100) ? 10 : size;

                if (!classUtilService.isValidField(sortBy, ProductEntity.class)) {
                        sortBy = classUtilService.getFirstFieldName(ProductEntity.class);
                }

                Sort sort = Sort.by(direction, sortBy);
                Pageable pageable = PageRequest.of(safePage, safeSize, sort);

                // Tạo specification từ các điều kiện filter
                Specification<ProductEntity> spec = Specification.where(null);

                // Áp dụng filter theo brand
                spec = spec.and(ProductSpecification.hasBrandUids(brandUids));

                // Áp dụng filter theo status
                spec = spec.and(ProductSpecification.hasProductStatusUids(statusUids));

                // Áp dụng filter theo product lines
                spec = spec.and(ProductSpecification.hasProductLineUids(productLineUids));

                // Áp dụng filter theo tên (like search)
                spec = spec.and(ProductSpecification.hasNameLike(nameLike));

                // Thực hiện query với specification và pageable
                Page<ProductEntity> productPage = productRepo.findAll(spec, pageable);

                // Convert entity sang DTO và trả về
                return productPage.map(productMapper::toDto);
        }

        @Caching(evict = {
                        @CacheEvict(value = "product.readByUid", key = "#uid"),
                        @CacheEvict(value = "product.readAll", allEntries = true),
                        @CacheEvict(value = "product.nameExists", allEntries = true)
        })
        public ProductDto updateNameByUid(UUID uid, String name) {
                ProductEntity product = productRepo.findById(uid)
                                .orElseThrow(() -> new EntityNotFoundException("Product not found with uid: " + uid));
                if (!Objects.equals(product.getName(), name)) {
                        product.setName(name.trim());
                }
                return productMapper.toDto(productRepo.save(product));
        }

        @Caching(evict = {
                        @CacheEvict(value = "product.readByUid", key = "#uid"),
                        @CacheEvict(value = "product.readAll", allEntries = true)
        })
        public ProductDto updateStatus(UUID uid, UUID statusUid) {
                ProductStatusEntity productStatus = productStatusRepo.findById(statusUid)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Product status not found with uid: " + statusUid));
                ProductEntity product = productRepo.findById(uid)
                                .orElseThrow(() -> new EntityNotFoundException("Product not found with uid: " + uid));
                product.setStatus(productStatus);
                return productMapper.toDto(productRepo.save(product));
        }

        @Caching(evict = {
                        @CacheEvict(value = "product.readByUid", key = "#uid"),
                        @CacheEvict(value = "product.readAll", allEntries = true)
        })
        public ProductDto updateBrand(UUID uid, UUID brandUid) {
                BrandEntity brand = brandRepo.findById(brandUid)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Brand not found with uid: " + brandUid));
                ProductEntity product = productRepo.findById(uid)
                                .orElseThrow(() -> new EntityNotFoundException("Product not found with uid: " + uid));
                product.setBrand(brand);
                return productMapper.toDto(productRepo.save(product));
        }

        @Caching(evict = {
                        @CacheEvict(value = "product.readByUid", key = "#uid"),
                        @CacheEvict(value = "product.readAll", allEntries = true),
                        @CacheEvict(value = "product.nameExists", allEntries = true)
        })
        public ProductDto deleteByUid(UUID uid) {
                ProductEntity brand = productRepo.findById(uid)
                                .orElseThrow(() -> new EntityNotFoundException("Product not found with uid: " + uid));
                productRepo.delete(brand);
                return productMapper.toDto(brand);
        }

        @Cacheable(value = "product.nameExists", key = "#name")
        public boolean nameExists(String name) {
                return productRepo.existsByName(name);
        }

        ///
        /// ORTHER
        ///

        public List<ProductLineDto> readAllProductLines(UUID productUid, boolean linked) {
                if (linked)
                        return productLineRepo.findLinkedToProduct(productUid).stream().map(productLineMapper::toDto)
                                        .collect(Collectors.toList());
                return productLineRepo.findNotLinkedToProduct(productUid).stream().map(productLineMapper::toDto)
                                .collect(Collectors.toList());
        }

        @Caching(evict = {
                        @CacheEvict(value = "product.readByUid", key = "#productUid"),
                        @CacheEvict(value = "product.readAll", allEntries = true)
        })
        public void addProductLines(UUID productUid, List<UUID> productLineUids) {
                ProductEntity product = productRepo.findById(productUid)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                List<ProductLineEntity> productLines = productLineRepo.findAllById(productLineUids);

                // Tránh thêm trùng
                for (ProductLineEntity pl : productLines) {
                        if (!product.getProductLines().contains(pl)) {
                                product.getProductLines().add(pl);
                        }
                }

                productRepo.save(product);
        }

        @Caching(evict = {
                        @CacheEvict(value = "product.readByUid", key = "#productUid"),
                        @CacheEvict(value = "product.readAll", allEntries = true)
        })
        public void removeProductLines(UUID productUid, List<UUID> productLineUids) {
                ProductEntity product = productRepo.findById(productUid)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                List<ProductLineEntity> productLinesToRemove = productLineRepo.findAllById(productLineUids);

                product.getProductLines().removeAll(productLinesToRemove);

                productRepo.save(product);
        }
}
