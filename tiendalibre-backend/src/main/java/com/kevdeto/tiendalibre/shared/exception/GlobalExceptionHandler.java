package com.kevdeto.tiendalibre.shared.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kevdeto.tiendalibre.domain.payload.MessageResponse;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public MessageResponse handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
		return new MessageResponse("Recurso no encontrado",
				null,
				404,
				LocalDateTime.now().toString(),
				ex.getMessage(),
				request.getRequestURI()
		);
	}
	
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public MessageResponse handleCustomBusinessException(BusinessException ex, HttpServletRequest request) {
	    return new MessageResponse(
	        "Error de negocio",
	        null,
	        400,
	        LocalDateTime.now().toString(),
	        ex.getMessage(),
	        request.getRequestURI()
	    );
	}
	
	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public MessageResponse handleInvalidDataAccessApiUsage(InvalidDataAccessApiUsageException ex, HttpServletRequest request) {
	    
	    log.error("Uso incorrecto de Spring Data: {}", ex.getMessage());
	    
	    return new MessageResponse(
	        "Error en los parámetros de acceso a datos",
	        null,
	        400,
	        LocalDateTime.now().toString(),
	        ex.getMostSpecificCause().getMessage(),
	        request.getRequestURI()
	    );
	}
	//error en category, cuando actualizo solo coloco el id y lo demas se llena con null
	
	//error en POST por el persist en category 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        return new MessageResponse(
                "Error de validación en los campos",
                null,
                400,
                LocalDateTime.now().toString(),
                errores.toString(),
                request.getRequestURI()
        );
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        return new MessageResponse(
                "Argumento no válido",
                null,
                400,
                LocalDateTime.now().toString(),
                ex.getMessage(),
                request.getRequestURI()
        );
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handleMalformedJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return new MessageResponse(
                "Cuerpo de la solicitud mal formado",
                null,
                400,
                LocalDateTime.now().toString(),
                ex.getMostSpecificCause().getMessage(),
                request.getRequestURI()
        );
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public MessageResponse handleConstraintViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.error("Violación de integridad de datos: {}", ex.getMessage());
        return new MessageResponse(
                "Violación de integridad de datos",
                null,
                409,
                LocalDateTime.now().toString(),
                "Ya existe un registro con esa información o relación inválida",
                request.getRequestURI()
        );
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MessageResponse handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        return new MessageResponse(
                "Acceso denegado",
                null,
                403,
                LocalDateTime.now().toString(),
                ex.getMessage(),
                request.getRequestURI()
        );
    }
    
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageResponse handleDataAccessException(DataAccessException ex, HttpServletRequest request) {
        log.error("Error de acceso a datos", ex);
        return new MessageResponse(
        		"Error en la capa de datos",
        		null,
        		500,
        		LocalDateTime.now().toString(),
        		"Ocurrió un error al acceder a los datos",
        		request.getRequestURI()
        		);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageResponse handleGeneralException(Exception ex, HttpServletRequest request) {
        log.error("Error inesperado: ", ex);
        return new MessageResponse(
                "Ocurrió un error interno en el servidor",
                null,
                500,
                LocalDateTime.now().toString(),
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}
