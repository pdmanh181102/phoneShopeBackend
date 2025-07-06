package tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.specification;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.entity.ProductAttributeEntity;

public class ProductAttributeSpecification {

    public static Specification<ProductAttributeEntity> hasProductUid(UUID productUid) {
        return (root, query, cb) -> productUid == null ? null : cb.equal(root.get("product").get("uid"), productUid);
    }

}
