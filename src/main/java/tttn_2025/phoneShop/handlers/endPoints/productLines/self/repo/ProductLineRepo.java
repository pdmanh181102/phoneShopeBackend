package tttn_2025.phoneShop.handlers.endPoints.productLines.self.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tttn_2025.phoneShop.handlers.endPoints.brands.self.entity.BrandEntity;
import tttn_2025.phoneShop.handlers.endPoints.productLines.self.entity.ProductLineEntity;

@Repository
public interface ProductLineRepo
        extends JpaRepository<ProductLineEntity, UUID>, JpaSpecificationExecutor<ProductLineEntity> {
    boolean existsByNameAndBrand(String name, BrandEntity brand);

    // Lấy các product line đã liên kết với product và cùng brand
    @Query("""
                SELECT pl FROM ProductLineEntity pl
                WHERE pl.brand.uid = (
                    SELECT p.brand.uid FROM ProductEntity p WHERE p.uid = :productUid
                )
                AND pl.uid IN (
                    SELECT pl2.uid FROM ProductEntity p
                    JOIN p.productLines pl2
                    WHERE p.uid = :productUid
                )
            """)
    List<ProductLineEntity> findLinkedToProduct(@Param("productUid") UUID productUid);

    // Lấy các product line chưa liên kết với product nhưng cùng brand
    @Query("""
                SELECT pl FROM ProductLineEntity pl
                WHERE pl.brand.uid = (
                    SELECT p.brand.uid FROM ProductEntity p WHERE p.uid = :productUid
                )
                AND pl.uid NOT IN (
                    SELECT pl2.uid FROM ProductEntity p
                    JOIN p.productLines pl2
                    WHERE p.uid = :productUid
                )
            """)
    List<ProductLineEntity> findNotLinkedToProduct(@Param("productUid") UUID productUid);
}
