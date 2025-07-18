package com.kevdeto.tiendalibre.domain.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.kevdeto.tiendalibre.domain.dto.request.ProductRequestDTO;
import com.kevdeto.tiendalibre.domain.dto.response.ProductDetailResponseDTO;
import com.kevdeto.tiendalibre.domain.dto.response.ProductSummaryResponseDTO;
import com.kevdeto.tiendalibre.domain.entity.CategoryEntity;
import com.kevdeto.tiendalibre.domain.entity.ProductEntity;
import com.kevdeto.tiendalibre.domain.entity.ReviewEntity;

@Mapper(componentModel = "spring", uses = ReviewMapper.class)
public interface ProductMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "images", ignore = true)
	@Mapping(target = "reviews", ignore = true)
	@Mapping(target = "categories", source = "categoryIds")
	@Mapping(target = "dimension", source = "dimension")
	ProductEntity toEntity(ProductRequestDTO dto);

	@Mapping(target = "dimension", source = "dimension")
	@Mapping(target = "categories", source = "categories")
	@Mapping(target = "images", source = "images")
	@Mapping(target = "reviews", source = "reviews")
	ProductDetailResponseDTO toDetailResponse(ProductEntity entity);

    @Mapping(target = "mainImageUrl", source = "mainImageUrl")
	ProductSummaryResponseDTO toSummaryResponse(ProductEntity entity);

	// Metodo para convertir IDs a entidades
	default Set<CategoryEntity> mapCategoryIdsToEntities(Set<Long> categoryIds) {
		if (categoryIds == null)
			return null;
		return categoryIds.stream().map(id -> CategoryEntity.builder().id(id).build()).collect(Collectors.toSet());
	}

	default Set<ReviewEntity> mapReviewIdsToEntities(Set<Long> reviewIds) {
		if (reviewIds == null)
			return null;
		return reviewIds.stream().map(id -> ReviewEntity.builder().id(id).build()).collect(Collectors.toSet());
	}
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "images", ignore = true)
	@Mapping(target = "reviews", ignore = true)
	@Mapping(target = "categories", source = "categoryIds")
	@Mapping(target = "dimension", source = "dimension")
	void updateEntityFromDTO(ProductRequestDTO dto, @MappingTarget ProductEntity entity);
}
