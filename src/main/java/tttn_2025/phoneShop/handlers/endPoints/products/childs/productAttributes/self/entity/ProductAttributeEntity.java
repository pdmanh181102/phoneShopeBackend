package tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.entity;

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
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tttn_2025.phoneShop.common.entities.audit.AuditableEntity;
import tttn_2025.phoneShop.handlers.endPoints.products.self.entity.ProductEntity;

@Entity
@Table(name = "product_attributes", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "product_uid", "name" })
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class ProductAttributeEntity extends AuditableEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "uid", updatable = false, nullable = false)
    private UUID uid;

    @Column(name = "name", unique = true, nullable = false, columnDefinition = "NVARCHAR(50)")
    private String name;

    @Column(name = "value", nullable = true, columnDefinition = "NVARCHAR(200)")
    private String value;

    @Column(name = "group_name", nullable = true, columnDefinition = "NVARCHAR(200)")
    private String groupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_uid", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCT_ATTRIBUTE"))
    private ProductEntity product;
}
