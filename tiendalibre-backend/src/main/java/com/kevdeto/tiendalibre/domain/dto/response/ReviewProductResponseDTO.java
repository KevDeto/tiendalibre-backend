package com.kevdeto.tiendalibre.domain.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ReviewProductResponseDTO(
	    Long id,
	    Integer rating,
	    String comment,
	    String username,
	    LocalDateTime date
){}