package com.kevdeto.tiendalibre.domain.dto.request;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PurchaseRequestDTO(
	    List<PurchaseItemRequestDTO> products
){}