package com.kevdeto.tiendalibre.domain.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.kevdeto.tiendalibre.domain.dto.request.CategoryRequestDTO;
import com.kevdeto.tiendalibre.domain.dto.response.CategoryResponseDTO;
import com.kevdeto.tiendalibre.domain.dto.response.CategoryWithProductResponseDTO;
import com.kevdeto.tiendalibre.domain.dto.response.ProductSummaryResponseDTO;
import com.kevdeto.tiendalibre.domain.entity.CategoryEntity;
import com.kevdeto.tiendalibre.domain.entity.ProductEntity;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    CategoryEntity toEntity(CategoryRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateEntityFromDTO(CategoryRequestDTO dto, @MappingTarget CategoryEntity entity);

    CategoryResponseDTO toResponseDTO(CategoryEntity entity);

    @Mapping(target = "productSummary", source = "products")
    CategoryWithProductResponseDTO toResponseWithProductsDTO(CategoryEntity entity);
}
