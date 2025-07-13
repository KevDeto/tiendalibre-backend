package com.kevdeto.tiendalibre.domain.dto.request;

public record FavoriteRequestDTO(
		Long userId,/*una vez tenga JWT podria obtener el user desde el token JWT*/
		Long productId
){}
