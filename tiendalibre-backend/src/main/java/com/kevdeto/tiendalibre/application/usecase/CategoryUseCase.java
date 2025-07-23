package com.kevdeto.tiendalibre.application.usecase;

import java.util.List;

import com.kevdeto.tiendalibre.domain.dto.request.CategoryRequestDTO;
import com.kevdeto.tiendalibre.domain.dto.response.CategoryResponseDTO;
import com.kevdeto.tiendalibre.domain.dto.response.CategoryWithProductResponseDTO;

public interface CategoryUseCase {
	List<CategoryResponseDTO> getAll();
	List<CategoryWithProductResponseDTO> search(String name, String slug, Integer size, Integer productLimit);
	CategoryWithProductResponseDTO getById(Long id, Integer page, Integer size);
	CategoryResponseDTO create(CategoryRequestDTO categoryRequest);
	CategoryResponseDTO update(Long id, CategoryRequestDTO categoryRequest);
	void delete(Long id);
	void deleteUnlinkProducts(Long id);
}