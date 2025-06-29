package tttn_2025.phoneShop.handlers.endPoints.brands.childs.productLines.selft.specification;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import tttn_2025.phoneShop.handlers.endPoints.brands.childs.productLines.selft.entity.ProductLineEntity;

public class ProductLineSpecification {
    public static Specification<ProductLineEntity> hasBrandUid(UUID brandUid) {
        return (root, query, cb) -> brandUid == null ? null : cb.equal(root.get("brand").get("uid"), brandUid);
    }
}
