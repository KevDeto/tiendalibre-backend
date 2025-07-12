package com.kevdeto.tiendalibre.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kevdeto.tiendalibre.domain.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
