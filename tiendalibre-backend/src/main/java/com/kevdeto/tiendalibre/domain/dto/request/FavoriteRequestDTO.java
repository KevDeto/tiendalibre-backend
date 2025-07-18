package com.kevdeto.tiendalibre.domain.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FavoriteRequestDTO(
		Long userId,/*una vez tenga JWT podria obtener el user desde el token JWT*/
		Long productId
){}
