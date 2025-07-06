package tttn_2025.phoneShop.handlers.endPoints.productLines.self.specification;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import tttn_2025.phoneShop.handlers.endPoints.productLines.self.entity.ProductLineEntity;

public class ProductLineSpecification {
    public static Specification<ProductLineEntity> hasBrandUid(UUID brandUid) {
        return (root, query, cb) -> brandUid == null ? null : cb.equal(root.get("brand").get("uid"), brandUid);
    }

    public static Specification<ProductLineEntity> hasBrandUids(List<UUID> brandUids) {
        return (root, query, cb) -> {
            if (brandUids == null || brandUids.isEmpty()) {
                return null;
            }
            return root.get("brand").get("uid").in(brandUids);
        };
    }
}
