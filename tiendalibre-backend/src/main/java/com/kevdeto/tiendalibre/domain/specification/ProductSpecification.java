package com.kevdeto.tiendalibre.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import com.kevdeto.tiendalibre.domain.entity.ProductEntity;

public class ProductSpecification {
	public static Specification<ProductEntity> hasName(String name) {
		return (root, query, cb) -> name == null ? null
				: cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
	}

	public static Specification<ProductEntity> hasBrand(String brand) {
		return (root, query, cb) -> brand == null ? null
				: cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
	}

	public static Specification<ProductEntity> hasActive(Boolean active) {
		return (root, query, cb) -> active == null ? null 
				: cb.equal(root.get("active"), active);
	}
}
