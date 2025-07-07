package tttn_2025.phoneShop.handlers.endPoints.products.self.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tttn_2025.phoneShop.common.entities.audit.AuditableEntity;
import tttn_2025.phoneShop.handlers.endPoints.brands.self.entity.BrandEntity;
import tttn_2025.phoneShop.handlers.endPoints.productLines.self.entity.ProductLineEntity;
import tttn_2025.phoneShop.handlers.endPoints.productStatus.self.entity.ProductStatusEntity;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.entity.ProductAttributeEntity;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.entity.ProductInventoryEntity;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.entity.ProductPhotoEntity;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class ProductEntity extends AuditableEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "uid", updatable = false, nullable = false)
    private UUID uid;

    @Column(name = "name", unique = true, nullable = false, columnDefinition = "NVARCHAR(50)")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_uid", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCT_BRAND"))
    private BrandEntity brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_uid", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCT_STATUS"))
    private ProductStatusEntity status;

    @ManyToMany
    @JoinTable(name = "product_line_links", joinColumns = @JoinColumn(name = "product_uid"), inverseJoinColumns = @JoinColumn(name = "product_line_uid"))
    private List<ProductLineEntity> productLines;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductPhotoEntity> productPhotos;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductAttributeEntity> productAttributes;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductInventoryEntity productInventory;

}
