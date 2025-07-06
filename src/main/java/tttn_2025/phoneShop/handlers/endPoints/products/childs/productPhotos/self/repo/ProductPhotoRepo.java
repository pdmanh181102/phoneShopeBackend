package tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.entity.ProductPhotoEntity;

@Repository
public interface ProductPhotoRepo
        extends JpaRepository<ProductPhotoEntity, UUID>, JpaSpecificationExecutor<ProductPhotoEntity> {

    @Modifying
    @Query("UPDATE ProductPhotoEntity p SET p.isMain = false WHERE p.uid <> :uid AND p.product.uid = :productUid")
    void updateAllIsIsMainFalseExcept(@Param("uid") UUID uid, @Param("productUid") UUID productUid);
}
