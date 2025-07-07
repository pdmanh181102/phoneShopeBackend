package tttn_2025.phoneShop.handlers.endPoints.products.self.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tttn_2025.phoneShop.handlers.endPoints.products.self.dto.ProductDto;
import tttn_2025.phoneShop.handlers.endPoints.products.self.entity.ProductEntity;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    ProductDto toDto(ProductEntity product);

    @Mapping(target = "productLines", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    ProductEntity toEntity(ProductDto product);
}
