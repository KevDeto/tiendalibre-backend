package com.kevdeto.tiendalibre.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PurchaseResponseDTO(
	    Long id,
	    LocalDateTime purchaseDate,
	    List<PurchaseItemResponseDTO> products
){}