package com.kevdeto.tiendalibre.domain.dto.response;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductDetailResponseDTO(
		Long id,
		String name,
		String brand,
		BigDecimal price,
		String description,
		String mainImageUrl,
		Set<String> tags,
		Map<String, String> specs,
		Integer stock,
		DimensionResponseDTO dimension,
		Integer discountPercentage,
		Integer weight,
		boolean active,
		Set<CategoryResponseDTO> categories,
		Set<ImageResponseDTO> images,
		Set<ReviewProductResponseDTO> reviews
){}
