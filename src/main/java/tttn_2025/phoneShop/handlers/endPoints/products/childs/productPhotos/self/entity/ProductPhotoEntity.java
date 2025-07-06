package tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "product_photos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class ProductPhotoEntity extends AuditableEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "uid", updatable = false, nullable = false)
    private UUID uid;

    @Column(name = "photo_url", nullable = false, columnDefinition = "NVARCHAR(200)")
    private String photoUrl;

    @Builder.Default
    @Column(name = "is_main", nullable = false)
    private Boolean isMain = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_uid", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCT_PHOTO"))
    ProductEntity product;
}
