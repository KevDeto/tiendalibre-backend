package com.kevdeto.tiendalibre.domain.payload;

public record MessageResponse (
		String message,
		Object data,
		int status,
		String timeStamp,
		String error,
		String path
){}