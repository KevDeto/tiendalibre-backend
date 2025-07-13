package com.kevdeto.tiendalibre.domain.dto.response;

public record FavoriteResponseDTO(
		Long favoriteId,
		ProductSummaryResponseDTO productSummary
){}
