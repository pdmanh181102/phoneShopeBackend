package tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.dto.ProductAttributeDto;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productAttributes.self.entity.ProductAttributeEntity;

@Mapper(componentModel = "spring")
public interface ProductAttributeMapper {
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    ProductAttributeDto toDto(ProductAttributeEntity productAttribute);

    @Mapping(target = "product", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    ProductAttributeEntity toEntity(ProductAttributeDto productAttribute);
}
