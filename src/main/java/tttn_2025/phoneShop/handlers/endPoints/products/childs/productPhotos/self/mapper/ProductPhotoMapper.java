package tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.dto.ProductPhotoDto;
import tttn_2025.phoneShop.handlers.endPoints.products.childs.productPhotos.self.entity.ProductPhotoEntity;

@Mapper(componentModel = "spring")
public interface ProductPhotoMapper {
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    ProductPhotoDto toDto(ProductPhotoEntity productPhoto);

    @Mapping(target = "product", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    ProductPhotoEntity toEntity(ProductPhotoDto productPhoto);
}
