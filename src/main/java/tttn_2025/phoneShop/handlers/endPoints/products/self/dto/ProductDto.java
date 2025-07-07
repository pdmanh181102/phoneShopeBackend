package tttn_2025.phoneShop.handlers.endPoints.products.self.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tttn_2025.phoneShop.common.dtos.audit.AuditDto;
import tttn_2025.phoneShop.handlers.endPoints.brands.self.dto.BrandDto;
import tttn_2025.phoneShop.handlers.endPoints.productLines.self.dto.ProductLineDto;
import tttn_2025.phoneShop.handlers.endPoints.productStatus.self.dto.ProductStatusDto;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.dto.ProductAttributeDto;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.dto.ProductInventoryDto;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.dto.ProductPhotoDto;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductDto extends AuditDto {
    private UUID uid;
    @NotBlank(message = "name cannot blank")
    private String name;
    private String photoUrl;
    private BrandDto brand;
    private ProductStatusDto status;
    private List<ProductLineDto> productLines;
    private List<ProductPhotoDto> productPhotos;
    private List<ProductAttributeDto> productAttributes;
    private ProductInventoryDto productInventory;
}
