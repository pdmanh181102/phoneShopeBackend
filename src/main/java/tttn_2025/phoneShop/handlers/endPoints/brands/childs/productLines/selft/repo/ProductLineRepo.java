package tttn_2025.phoneShop.handlers.endPoints.brands.childs.productLines.selft.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import tttn_2025.phoneShop.handlers.endPoints.brands.childs.productLines.selft.entity.ProductLineEntity;

@Repository
public interface ProductLineRepo
        extends JpaRepository<ProductLineEntity, UUID>, JpaSpecificationExecutor<ProductLineEntity> {
    boolean existsByName(String name);
}
