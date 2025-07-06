package tttn_2025.phoneShop.handlers.endPoints.brands.self.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tttn_2025.phoneShop.handlers.endPoints.brands.self.dto.BrandDto;
import tttn_2025.phoneShop.handlers.endPoints.brands.self.entity.BrandEntity;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    BrandDto toDto(BrandEntity brand);

    @Mapping(target = "productLines", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    BrandEntity toEntity(BrandDto brand);
}
