package tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tttn_2025.phoneShop.common.entities.audit.AuditableEntity;
import tttn_2025.phoneShop.handlers.endPoints.products.self.entity.ProductEntity;

@Entity
@Table(name = "product_inventories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class ProductInventoryEntity extends AuditableEntity {

    // Khóa chính dạng UUID giống product.uid
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "uid", nullable = false)
    private UUID uid;

    // Ràng buộc OneToOne với Product
    @OneToOne
    @MapsId // Bắt buộc để dùng chung khóa chính
    @JoinColumn(name = "product_uid", unique = true, referencedColumnName = "uid")
    private ProductEntity product;

    @Builder.Default
    @Column(name = "import_price", precision = 19, scale = 4)
    private BigDecimal importPrice = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "sale_price", precision = 19, scale = 4)
    private BigDecimal salePrice = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "total_item")
    private Long totalItem = 0L;

    @Builder.Default
    @Column(name = "left_item")
    private Long leftItem = 0L;
}
