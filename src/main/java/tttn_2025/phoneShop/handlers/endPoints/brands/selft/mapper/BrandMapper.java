package tttn_2025.phoneShop.handlers.endPoints.brands.selft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tttn_2025.phoneShop.handlers.endPoints.brands.selft.dto.BrandDto;
import tttn_2025.phoneShop.handlers.endPoints.brands.selft.entity.BrandEntity;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandDto toDto(BrandEntity brand);

    @Mapping(target = "productLines", ignore = true)
    BrandEntity toEntity(BrandDto brand);
}
