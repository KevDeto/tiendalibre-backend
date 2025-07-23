package com.kevdeto.tiendalibre.application.adapter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kevdeto.tiendalibre.application.usecase.ProductUseCase;
import com.kevdeto.tiendalibre.domain.dto.request.ProductRequestDTO;
import com.kevdeto.tiendalibre.domain.dto.response.ProductDetailResponseDTO;
import com.kevdeto.tiendalibre.domain.dto.response.ProductSummaryResponseDTO;
import com.kevdeto.tiendalibre.domain.entity.ProductEntity;
import com.kevdeto.tiendalibre.domain.mapper.ProductMapper;
import com.kevdeto.tiendalibre.domain.repository.ProductRepository;
import com.kevdeto.tiendalibre.domain.specification.ProductSpecification;
import com.kevdeto.tiendalibre.shared.exception.BusinessException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductUseCase {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;

	public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
	}

	@Override
	public List<ProductSummaryResponseDTO> getAll(Integer page, Integer size) {
		if(page == null && size == null) {
			throw new BusinessException("Debe proporcionar un page y un size.");
		}
		
		Page<ProductEntity> productPages = productRepository.findAll(PageRequest.of(page, size));
		List<ProductSummaryResponseDTO> listProductResponse = productPages.getContent().stream()
				.map(productMapper::toSummaryResponse).toList();
		return listProductResponse;
	}

	@Override
	public List<ProductSummaryResponseDTO> search(String name, String brand, Boolean active, Integer size) {
		if (name == null && brand == null && active == null && size == null) {
			throw new BusinessException("Debe proporcionar al menos un filtro de busqueda.");
		}
				
		Specification<ProductEntity> productSpec = ProductSpecification.hasName(name)
				.and(ProductSpecification.hasBrand(brand))
				.and(ProductSpecification.hasActive(active));
		
		List<ProductSummaryResponseDTO> listProductSummaryResponse = productRepository
				.findAll(productSpec, PageRequest.of(0, size))
				.getContent()
				.stream()
				.map(productMapper::toSummaryResponse)
				.toList();
		
		return listProductSummaryResponse;
	}

	@Override
	public ProductDetailResponseDTO getById(Long id) {
		ProductEntity product = productRepository.findById(id)
				.orElseThrow(()-> new EntityNotFoundException("Producto no encontrado"));
		ProductDetailResponseDTO productDetailResponse = productMapper.toDetailResponse(product);
		return productDetailResponse;
	}

	@Override
	@Transactional
	public ProductDetailResponseDTO create(ProductRequestDTO productRequest) {
		ProductEntity product = productMapper.toEntity(productRequest);
		ProductEntity productSave = productRepository.save(product);
		ProductDetailResponseDTO productDetailReponse = productMapper.toDetailResponse(productSave);
		return productDetailReponse;
	}

	@Override
	@Transactional
	public ProductDetailResponseDTO update(Long id, ProductRequestDTO requestDTO) {
		ProductEntity product = productRepository.findById(id)
				.orElseThrow(()->new EntityNotFoundException("Producto con ID "+id+" no encontrado."));
		
		productMapper.updateEntityFromDTO(requestDTO, product);
		ProductEntity upgradedProduct = productRepository.save(product);
		ProductDetailResponseDTO productDetailReponse = productMapper.toDetailResponse(upgradedProduct);
		return productDetailReponse;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if(!productRepository.existsById(id)) {
			throw new EntityNotFoundException("Producto con ID "+id+" no encontrado.");
		}
		productRepository.deleteById(id);
	}
}
