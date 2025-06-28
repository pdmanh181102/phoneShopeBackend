package tttn_2025.phoneShop.handlers.endPoints.brands.childs.productLines.selft.entity;

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
import tttn_2025.phoneShop.common.entities.audit.AuditableEntity;
import tttn_2025.phoneShop.handlers.endPoints.brands.selft.entity.BrandEntity;

@Entity
@Table(name = "product_lines")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ProductLineEntity extends AuditableEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "uid", updatable = false, nullable = false)
    private UUID uid;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_uid", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCT_LINE_BRAND"))
    BrandEntity brand;
}
