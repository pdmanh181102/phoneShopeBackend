package tttn_2025.phoneShop.handlers.endPoints.brands.childs.productLines.selft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tttn_2025.phoneShop.handlers.endPoints.brands.childs.productLines.selft.dto.ProductLineDto;
import tttn_2025.phoneShop.handlers.endPoints.brands.childs.productLines.selft.entity.ProductLineEntity;
import tttn_2025.phoneShop.handlers.endPoints.brands.selft.dto.BrandDto;

@Mapper(componentModel = "spring")
public interface ProductLineMapper {
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    ProductLineDto toDto(ProductLineEntity productLine);

    @Mapping(target = "brand", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    ProductLineEntity ProductLineDto(BrandDto productLine);
}
