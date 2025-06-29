package tttn_2025.phoneShop.handlers.endPoints.productStatus.selft.dto;

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
public class ProductStatusDto extends AuditDto {
    private UUID uid;
    @NotBlank(message = "name cannot blank")
    private String name;
    private String description;
    private Boolean isDefault;
}
