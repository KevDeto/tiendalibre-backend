package com.kevdeto.tiendalibre.domain.dto.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductSummaryResponseDTO(
		Long id,
		String name,
		BigDecimal price,
		String brand,
		String mainImageUrl,
		boolean active
){}
