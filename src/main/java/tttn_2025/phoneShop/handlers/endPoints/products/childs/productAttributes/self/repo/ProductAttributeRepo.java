package tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.entity.ProductAttributeEntity;
import tttn_2025.phoneShop.handlers.endPoints.products.self.entity.ProductEntity;

@Repository
public interface ProductAttributeRepo
        extends JpaRepository<ProductAttributeEntity, UUID>, JpaSpecificationExecutor<ProductAttributeEntity> {
    boolean existsByNameAndProduct(String name, ProductEntity product);
}
