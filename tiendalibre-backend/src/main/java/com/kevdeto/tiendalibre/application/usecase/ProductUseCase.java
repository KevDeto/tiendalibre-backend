package com.kevdeto.tiendalibre.application.usecase;

import java.util.List;

import com.kevdeto.tiendalibre.domain.dto.request.ProductRequestDTO;
import com.kevdeto.tiendalibre.domain.dto.response.ProductDetailResponseDTO;
import com.kevdeto.tiendalibre.domain.dto.response.ProductSummaryResponseDTO;

public interface ProductUseCase {
	List<ProductSummaryResponseDTO> getAll(Integer page, Integer size);
	List<ProductSummaryResponseDTO> search(String name, String brand, Boolean active, Integer size);
	ProductDetailResponseDTO getById(Long id);
	ProductDetailResponseDTO create(ProductRequestDTO productRequest);
	ProductDetailResponseDTO update(Long id, ProductRequestDTO requestDTO);
	void delete(Long id);
}
