package tttn_2025.phoneShop.handlers.endPoints.productStatus.selft.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tttn_2025.phoneShop.handlers.endPoints.productStatus.selft.entity.ProductStatusEntity;

@Repository
public interface ProductStatusRepo extends JpaRepository<ProductStatusEntity, UUID> {
    boolean existsByName(String name);

    @Modifying
    @Query("UPDATE ProductStatusEntity p SET p.isDefault = false WHERE p.uid <> :uid")
    void updateAllIsDefaultFalseExcept(@Param("uid") UUID uid);
}
