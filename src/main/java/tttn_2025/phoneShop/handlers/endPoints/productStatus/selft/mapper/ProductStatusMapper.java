package tttn_2025.phoneShop.handlers.endPoints.productStatus.selft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tttn_2025.phoneShop.handlers.endPoints.productStatus.selft.dto.ProductStatusDto;
import tttn_2025.phoneShop.handlers.endPoints.productStatus.selft.entity.ProductStatusEntity;

@Mapper(componentModel = "spring")
public interface ProductStatusMapper {
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    ProductStatusDto toDto(ProductStatusEntity productStatus);

    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    ProductStatusEntity toEntity(ProductStatusDto productStatus);
}
