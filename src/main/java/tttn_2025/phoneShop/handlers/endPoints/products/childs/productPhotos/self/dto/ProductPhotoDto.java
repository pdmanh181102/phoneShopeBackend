package tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
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
public class ProductPhotoDto extends AuditDto {
    private UUID uid;
    @NotBlank(message = "photo url cannot blank")
    private String photoUrl;
    private Boolean isMain;
}
