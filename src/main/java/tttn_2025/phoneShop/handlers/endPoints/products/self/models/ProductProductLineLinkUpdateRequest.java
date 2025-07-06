package tttn_2025.phoneShop.handlers.endPoints.products.self.models;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductProductLineLinkUpdateRequest {
    private UUID productLineUid;
}
