package com.kevdeto.tiendalibre.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kevdeto.tiendalibre.application.adapter.ProductServiceImpl;
import com.kevdeto.tiendalibre.domain.dto.request.ProductRequestDTO;
import com.kevdeto.tiendalibre.domain.dto.response.ProductDetailResponseDTO;
import com.kevdeto.tiendalibre.domain.dto.response.ProductSummaryResponseDTO;
import com.kevdeto.tiendalibre.domain.payload.MessageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/products")
public class ProductController {
	private final ProductServiceImpl productService;

	public ProductController(ProductServiceImpl productService) {
		this.productService = productService;
	}

//	@PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
	@GetMapping
	public ResponseEntity<MessageResponse> getAllProducts(
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size) {
		List<ProductSummaryResponseDTO> response = productService.getAll(page, size);
		return ResponseEntity.ok(new MessageResponse(
				"Productos encontrados",
				response,
				200,
				LocalDateTime.now().toString(),
				null,
				"/api/products"));
	}

//	@PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
	@GetMapping("/search")
	public ResponseEntity<MessageResponse> searchProducts(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String brand,
			@RequestParam(required = false) Boolean active,
			@RequestParam(required = false) Integer size) {
		List<ProductSummaryResponseDTO> response = productService.search(name, brand, active, size);
		return ResponseEntity.ok(new MessageResponse(
				"Resultados de búsqueda",
				response,
				200,
				LocalDateTime.now().toString(),
				null,
				"/api/products/search"));
	}

//	@PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
	@GetMapping("/{id}")
	public ResponseEntity<MessageResponse> getProductById(
			@PathVariable Long id) {
		ProductDetailResponseDTO response = productService.getById(id);
		return ResponseEntity.ok(new MessageResponse(
				"Producto encontrado",
				response,
				200,
				LocalDateTime.now().toString(),
				null,
				"/api/products/" + id));
	}

	@PostMapping
	public ResponseEntity<MessageResponse> createProduct(
			@Valid @RequestBody ProductRequestDTO productRequest) {
		ProductDetailResponseDTO response = productService.create(productRequest);
		return ResponseEntity.ok(new MessageResponse(
				"Producto creado exitosamente",
				response,
				201,
				LocalDateTime.now().toString(),
				null,
				"/api/products"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<MessageResponse> updateProduct(
			@PathVariable Long id,
			@Valid @RequestBody ProductRequestDTO productRequest) {
		ProductDetailResponseDTO response = productService.update(id, productRequest);
		return ResponseEntity.ok(new MessageResponse(
				"Producto actualizado exitosamente",
				response,
				200,
				LocalDateTime.now().toString(),
				null,
				"/api/products/" + id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteProduct(
			@PathVariable Long id) {
		productService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body((new MessageResponse(
						"Producto eliminado correctamente",
						null,
						204,
						LocalDateTime.now().toString(),
						null,
						"/api/products/" + id)));
	}
}
