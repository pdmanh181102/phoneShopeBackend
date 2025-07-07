package tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.dto.ProductInventoryDto;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productInventories.self.entity.ProductInventoryEntity;

@Mapper(componentModel = "spring")
public interface ProductInventoryMapper {
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    ProductInventoryDto toDto(ProductInventoryEntity productInventory);

    @Mapping(target = "product", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    ProductInventoryEntity toEntity(ProductInventoryDto productInventory);
}
