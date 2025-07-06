package tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.service;

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
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import tttn_2025.phoneShop.common.services.classes.ClassUtilService;
import tttn_2025.phoneShop.common.services.file.FileUploadService;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.dto.ProductPhotoDto;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.entity.ProductPhotoEntity;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.mapper.ProductPhotoMapper;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.repo.ProductPhotoRepo;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.specification.ProductPhotoSpecification;
import tttn_2025.phoneShop.handlers.endPoints.products.self.entity.ProductEntity;
import tttn_2025.phoneShop.handlers.endPoints.products.self.repo.ProductRepo;

@Service
@Transactional
public class ProductPhotoService {
    private final ProductPhotoRepo productPhotoRepo;
    private final ProductRepo productRepo;
    private final ProductPhotoMapper productPhotoMapper;
    private final ClassUtilService classUtilService;
    private final FileUploadService fileUploadService;

    public ProductPhotoService(ProductPhotoRepo productPhotoRepo, ProductRepo productRepo,
            ProductPhotoMapper productPhotoMapper,
            ClassUtilService classUtilService,
            FileUploadService fileUploadService) {
        this.productPhotoRepo = productPhotoRepo;
        this.productRepo = productRepo;
        this.productPhotoMapper = productPhotoMapper;
        this.classUtilService = classUtilService;
        this.fileUploadService = fileUploadService;
    }

    @Caching(evict = {
            @CacheEvict(value = "productPhoto.readAll", allEntries = true),
            @CacheEvict(value = "product.readAll", allEntries = true),
            @CacheEvict(value = "product.readByUid", key = "#productUid"),
    })
    public ProductPhotoDto create(UUID productUid, MultipartFile photo) {
        ProductEntity product = productRepo.findById(productUid)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with uid: " + productUid));

        // upload file
        try {
            String photoUrl = fileUploadService.uploadImage(photo);
            ProductPhotoEntity productPhoto = new ProductPhotoEntity();
            productPhoto.setProduct(product);
            productPhoto.setPhotoUrl(photoUrl);
            return productPhotoMapper.toDto(productPhotoRepo.save(productPhoto));
        } catch (Exception e) {
            throw new RuntimeException("file upload failed!");
        }
    }

    @Cacheable(value = "productPhoto.readByUid", key = "#uid")
    public ProductPhotoDto readByUid(UUID uid) {
        ProductPhotoEntity productPhoto = productPhotoRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product photo not found with uid: " + uid));
        return productPhotoMapper.toDto(productPhoto);
    }

    @Cacheable(value = "productPhoto.readAll")
    public Page<ProductPhotoDto> readAll(UUID productUid, int page, int size, String sortBy,
            Sort.Direction direction) {
        int safePage = Math.max(page, 0);
        int safeSize = (size <= 0 || size > 100) ? 10 : size;
        if (!classUtilService.isValidField(sortBy, ProductPhotoEntity.class)) {
            sortBy = classUtilService.getFirstFieldName(ProductPhotoEntity.class);
        }
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(safePage, safeSize, sort);
        Specification<ProductPhotoEntity> spec = Specification
                .where(ProductPhotoSpecification.hasProductUid(productUid));

        Page<ProductPhotoEntity> entities = productPhotoRepo.findAll(spec, pageable);
        return entities.map(productPhotoMapper::toDto);
    }

    @Caching(evict = {
            @CacheEvict(value = "productPhoto.readByUid", key = "#uid"),
            @CacheEvict(value = "productPhoto.readAll", allEntries = true),
            @CacheEvict(value = "product.readAll", allEntries = true),
            @CacheEvict(value = "product.readByUid", allEntries = true),
    })
    public ProductPhotoDto updateIsMain(UUID uid) {
        ProductPhotoEntity productPhoto = productPhotoRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product photo not found with uid: " + uid));
        if (productPhoto.getIsMain() == true) {
            return productPhotoMapper.toDto(productPhoto);
        }
        productPhotoRepo.updateAllIsIsMainFalseExcept(uid, productPhoto.getProduct().getUid());
        productPhoto.setIsMain(true);
        return productPhotoMapper.toDto(productPhotoRepo.save(productPhoto));
    }

    @Caching(evict = {
            @CacheEvict(value = "productPhoto.readByUid", key = "#uid"),
            @CacheEvict(value = "productPhoto.readAll", allEntries = true),
            @CacheEvict(value = "product.readAll", allEntries = true),
            @CacheEvict(value = "product.readByUid", allEntries = true),
    })
    public ProductPhotoDto deleteByUid(UUID uid) {
        ProductPhotoEntity productPhoto = productPhotoRepo.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Product photo not found with uid: " + uid));
        productPhotoRepo.delete(productPhoto);
        return productPhotoMapper.toDto(productPhoto);
    }

}
