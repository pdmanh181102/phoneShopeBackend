package tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tttn_2025.phoneShop.common.dtos.audit.AuditDto;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductInventoryDto extends AuditDto {
    private UUID uid;
    private BigDecimal importPrice;
    private BigDecimal salePrice;
    private Long totalItem;
    private Long leftItem;
}
