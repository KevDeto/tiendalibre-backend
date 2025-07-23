package com.kevdeto.tiendalibre.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.kevdeto.tiendalibre.domain.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {
	Page<ProductEntity> findByCategoriesId(Long categoryId, Pageable pageable);
	List<ProductEntity> findByCategoriesId(Long categoryId);
}
