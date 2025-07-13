package com.kevdeto.tiendalibre.domain.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ImageRequestDTO(
		String url,
		boolean isMain,
		Long productId
){}