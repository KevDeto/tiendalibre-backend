package com.kevdeto.tiendalibre.domain.dto.request;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductRequestDTO(
		String name,
		BigDecimal price,
		String brand,
		String description,
		String mainImageUrl,
		Integer stock,
		Integer discountPercentage,
		Integer weight,
		DimensionRequestDTO dimension,
		boolean active,
		Map<String, String> specs,
		Set<String> tags,
		Set<Long> categoryIds
){}
