package com.kevdeto.tiendalibre.domain.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DimensionResponseDTO(
		double width,
		double height,
		double depth
){}
