package tttn_2025.phoneShop.handlers.endPoints.products.self.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import tttn_2025.phoneShop.handlers.endPoints.products.self.entity.ProductEntity;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity> {
    boolean existsByName(String name);
}
