package tttn_2025.phoneShop.handlers.endPoints.brands.selft.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tttn_2025.phoneShop.common.entities.audit.AuditableEntity;
import tttn_2025.phoneShop.handlers.endPoints.brands.childs.productLines.selft.entity.ProductLineEntity;

@Entity
@Table(name = "brands")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class BrandEntity extends AuditableEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "uid", updatable = false, nullable = false)
    private UUID uid;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "photo_url", nullable = true)
    private String photoUrl;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductLineEntity> productLines;

}
