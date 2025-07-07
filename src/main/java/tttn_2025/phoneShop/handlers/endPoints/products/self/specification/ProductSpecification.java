package tttn_2025.phoneShop.handlers.endPoints.products.self.specification;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import tttn_2025.phoneShop.handlers.endPoints.productLines.self.entity.ProductLineEntity;
import tttn_2025.phoneShop.handlers.endPoints.products.self.entity.ProductEntity;

public class ProductSpecification {
    public static Specification<ProductEntity> hasBrandUid(UUID brandUid) {
        return (root, query, cb) -> brandUid == null ? null : cb.equal(root.get("brand").get("uid"), brandUid);
    }

    public static Specification<ProductEntity> hasBrandUids(List<UUID> brandUids) {
        return (root, query, cb) -> {
            if (brandUids == null || brandUids.isEmpty()) {
                return null;
            }
            return root.get("brand").get("uid").in(brandUids);
        };
    }

    public static Specification<ProductEntity> hasProductStatusUid(UUID productStatusUid) {
        return (root, query, cb) -> productStatusUid == null ? null
                : cb.equal(root.get("status").get("uid"), productStatusUid);
    }

    public static Specification<ProductEntity> hasProductStatusUids(List<UUID> productStatusUids) {
        return (root, query, cb) -> {
            if (productStatusUids == null || productStatusUids.isEmpty()) {
                return null;
            }
            return root.get("status").get("uid").in(productStatusUids);
        };
    }

    public static Specification<ProductEntity> hasProductLineUids(List<UUID> productLineUids) {
        return (root, query, cb) -> {
            if (productLineUids == null || productLineUids.isEmpty()) {
                return null;
            }
            Join<ProductEntity, ProductLineEntity> productLineJoin = root.join("productLines");
            return productLineJoin.get("uid").in(productLineUids);
        };
    }

    public static Specification<ProductEntity> hasNameLike(String nameLike) {
        return (root, query, cb) -> {
            if (nameLike == null || nameLike.trim().isEmpty()) {
                return null;
            }
            return cb.like(cb.lower(root.get("name")), "%" + nameLike.toLowerCase() + "%");
        };
    }
}
