package tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.specification;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.entity.ProductPhotoEntity;

public class ProductPhotoSpecification {

    public static Specification<ProductPhotoEntity> hasProductUid(UUID productUid) {
        return (root, query, cb) -> productUid == null ? null : cb.equal(root.get("product").get("uid"), productUid);
    }

}
