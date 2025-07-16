package com.kevdeto.tiendalibre.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import com.kevdeto.tiendalibre.domain.entity.CategoryEntity;
import com.kevdeto.tiendalibre.domain.entity.ProductEntity;

import jakarta.persistence.criteria.Join;

public class ProductSpecification {
	public static Specification<ProductEntity> hasName(String name) {
		return (root, query, cb) -> name == null ? null
				: cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
	}

	public static Specification<ProductEntity> hasBrand(String brand) {
		return (root, query, cb) -> brand == null ? null
				: cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
	}

	public static Specification<ProductEntity> hasIsActive(Boolean isActive) {
		return (root, query, cb) -> isActive == null ? null 
				: cb.equal(root.get("isActive"), isActive);
	}
}
