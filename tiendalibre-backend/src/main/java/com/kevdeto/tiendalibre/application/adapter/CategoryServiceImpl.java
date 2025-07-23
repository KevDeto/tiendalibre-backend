package com.kevdeto.tiendalibre.application.adapter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kevdeto.tiendalibre.application.usecase.CategoryUseCase;
import com.kevdeto.tiendalibre.domain.dto.request.CategoryRequestDTO;
import com.kevdeto.tiendalibre.domain.dto.response.CategoryResponseDTO;
import com.kevdeto.tiendalibre.domain.dto.response.CategoryWithProductResponseDTO;
import com.kevdeto.tiendalibre.domain.dto.response.ProductSummaryResponseDTO;
import com.kevdeto.tiendalibre.domain.entity.CategoryEntity;
import com.kevdeto.tiendalibre.domain.entity.ProductEntity;
import com.kevdeto.tiendalibre.domain.mapper.CategoryMapper;
import com.kevdeto.tiendalibre.domain.mapper.ProductMapper;
import com.kevdeto.tiendalibre.domain.repository.CategoryRepository;
import com.kevdeto.tiendalibre.domain.repository.ProductRepository;
import com.kevdeto.tiendalibre.domain.specification.CategorySpecification;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryUseCase {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	private final ProductMapper productMapper;

	public CategoryServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepositoy,
			CategoryMapper categoryMapper, ProductMapper productMapper) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepositoy;
		this.categoryMapper = categoryMapper;
		this.productMapper = productMapper;
	}

	@Override
	public List<CategoryResponseDTO> getAll() {
		List<CategoryResponseDTO> listCategoriesResponse = categoryRepository.findAll().stream()
				.map(categoryMapper::toResponseDTO).toList();
		return listCategoriesResponse;
	}

	@Override
	public List<CategoryWithProductResponseDTO> search(String name, String slug, Integer size, Integer productLimit) {
		List<CategoryEntity> results = categoryRepository
				.findAll(CategorySpecification.hasName(name).and(CategorySpecification.hasSlug(slug)));

		if (size != null && size > 0 && results.size() > size) {
			results = results.subList(0, size);
		}

		return results.stream().map(category -> {
			CategoryWithProductResponseDTO dto = categoryMapper.toResponseWithProductsDTO(category);
			Set<ProductSummaryResponseDTO> limitedProducts = dto.productSummary();
			if (productLimit != null && productLimit > 0 && dto.productSummary().size() > productLimit) {
				limitedProducts = limitedProducts.stream().limit(productLimit).collect(Collectors.toSet());
			}
			return new CategoryWithProductResponseDTO(dto.id(), dto.name(), dto.slug(), limitedProducts);
		}).collect(Collectors.toList());
	}

	@Override
	public CategoryWithProductResponseDTO getById(Long id, Integer page, Integer size) {
		CategoryEntity entity = categoryRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + id));

		Pageable pageable = PageRequest.of(page, size);
		Page<ProductEntity> pagedProducts = productRepository.findByCategoriesId(id, pageable);

		Set<ProductSummaryResponseDTO> limitedProducts = pagedProducts.getContent().stream()
				.map(productMapper::toSummaryResponse).collect(Collectors.toSet());

		return new CategoryWithProductResponseDTO(entity.getId(), entity.getName(), entity.getSlug(), limitedProducts);
	}

	@Override
	@Transactional
	public CategoryResponseDTO create(CategoryRequestDTO categoryRequest) {
		CategoryEntity entity = categoryMapper.toEntity(categoryRequest);
		return categoryMapper.toResponseDTO(categoryRepository.save(entity));
	}

	@Override
	@Transactional
	public CategoryResponseDTO update(Long id, CategoryRequestDTO categoryRequest) {
		CategoryEntity entity = categoryRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + id));

		categoryMapper.updateEntityFromDTO(categoryRequest, entity);
		return categoryMapper.toResponseDTO(categoryRepository.save(entity));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!categoryRepository.existsById(id)) {
			throw new EntityNotFoundException("Categoría no encontrada con id: " + id);
		}

		List<ProductEntity> productos = productRepository.findByCategoriesId(id);
		if (!productos.isEmpty()) {
			throw new IllegalStateException("No se puede eliminar la categoría. Hay productos asociados.");
		}

		categoryRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void deleteUnlinkProducts(Long id) {
		if (!categoryRepository.existsById(id)) {
			throw new EntityNotFoundException("Categoría no encontrada con id: " + id);
		}

		List<ProductEntity> productos = productRepository.findByCategoriesId(id);
		for (ProductEntity producto : productos) {
			producto.getCategories().removeIf(cat -> cat.getId().equals(id));
		}

		productRepository.saveAll(productos);

		categoryRepository.deleteById(id);
	}
}
