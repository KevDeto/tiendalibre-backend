package com.kevdeto.tiendalibre.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kevdeto.tiendalibre.application.adapter.CategoryServiceImpl;
import com.kevdeto.tiendalibre.domain.dto.request.CategoryRequestDTO;
import com.kevdeto.tiendalibre.domain.dto.request.ProductRequestDTO;
import com.kevdeto.tiendalibre.domain.dto.response.CategoryResponseDTO;
import com.kevdeto.tiendalibre.domain.dto.response.CategoryWithProductResponseDTO;
import com.kevdeto.tiendalibre.domain.dto.response.ProductDetailResponseDTO;
import com.kevdeto.tiendalibre.domain.payload.MessageResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
	
	private final CategoryServiceImpl categoryService;
	
	public CategoryController(CategoryServiceImpl categoryService) {
		this.categoryService = categoryService;
	}
	
	@Operation(summary = "Traer todas las categorias")
	@GetMapping
	public ResponseEntity<MessageResponse> getAllCategories(){
		List<CategoryResponseDTO> response = categoryService.getAll();
		return ResponseEntity.ok(new MessageResponse(
				"Categorias encontradas",
				response,
				200,
				LocalDateTime.now().toString(),
				null,
				"/api/categories"));
	}
	
	@Operation(summary = "Filtrar categoria por nombre, slug, tamaño de paginacion o limite de productos por categoria")
	@GetMapping("/search")
	public ResponseEntity<MessageResponse> searchCategory(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String slug,
			@RequestParam(required = false) Integer size,
			@RequestParam(required = false) Integer limitProduct){
		List<CategoryWithProductResponseDTO> response = categoryService.search(name, slug, size, limitProduct);
		return ResponseEntity.ok(new MessageResponse(
				"Resultados de búsqueda",
				response,
				200,
				LocalDateTime.now().toString(),
				null,
				"/api/categories/search"));
	}
	
	@Operation(summary = "Traer categoría con productos (cantidad de productos limitados por paginacion)")
	@GetMapping("/{id}")
	public ResponseEntity<MessageResponse> getCategoryById(
			@PathVariable Long id,
	        @RequestParam(defaultValue = "0") Integer page,
	        @RequestParam(defaultValue = "10") Integer size) {
		CategoryWithProductResponseDTO response = categoryService.getById(id, page, size);
		return ResponseEntity.ok(new MessageResponse(
				"Categoria encontrada",
				response,
				200,
				LocalDateTime.now().toString(),
				null,
				"/api/products/" + id));
	}
	
	@Operation(summary = "Crear categoría")
	@PostMapping
	public ResponseEntity<MessageResponse> createCategory(
			@Valid @RequestBody CategoryRequestDTO categoryRequest) {
		CategoryResponseDTO response = categoryService.create(categoryRequest);
		return ResponseEntity.ok(new MessageResponse(
				"Categoria creada exitosamente",
				response,
				201,
				LocalDateTime.now().toString(),
				null,
				"/api/categories"));
	}

	@Operation(summary = "Actualizar categoría")
	@PutMapping("/{id}")
	public ResponseEntity<MessageResponse> updateCategory(
			@PathVariable Long id,
			@Valid @RequestBody CategoryRequestDTO categoryRequest) {
		CategoryResponseDTO response = categoryService.update(id, categoryRequest);
		return ResponseEntity.ok(new MessageResponse(
				"Categoria actualizada exitosamente",
				response,
				200,
				LocalDateTime.now().toString(),
				null,
				"/api/categories/" + id));
	}

	@Operation(summary = "Eliminar categoría (si no tiene productos relacionados)")
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteCategory(
			@PathVariable Long id) {
		categoryService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body((new MessageResponse(
						"Categoria eliminada correctamente",
						null,
						204,
						LocalDateTime.now().toString(),
						null,
						"/api/categories/" + id)));
	}
	
	@Operation(summary = "Eliminar categoría y desasociar productos")
	@DeleteMapping("/{id}/force")
	public ResponseEntity<MessageResponse> forceDeleteCategory(
			@PathVariable Long id) {
		categoryService.deleteUnlinkProducts(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body((new MessageResponse(
						"Categoria desasociada y eliminada correctamente",
						null,
						204,
						LocalDateTime.now().toString(),
						null,
						"/api/categories/" + id + "/force")));
	}
}
