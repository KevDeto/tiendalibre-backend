package com.kevdeto.tiendalibre.domain.dto.response;

import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CategoryWithProductResponseDTO(
		Long id,
		String name,
		String slug,
		Set<ProductSummaryResponseDTO> productSummary
){}
