package tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.entity.ProductInventoryEntity;

@Repository
public interface ProductInventoryRepo
                extends JpaRepository<ProductInventoryEntity, UUID>,
                JpaSpecificationExecutor<ProductInventoryEntity> {
        @Query("SELECT pi FROM ProductInventoryEntity pi WHERE pi.product.uid = :uid")
        Optional<ProductInventoryEntity> findByProductUid(@Param("uid") UUID uid);
}
