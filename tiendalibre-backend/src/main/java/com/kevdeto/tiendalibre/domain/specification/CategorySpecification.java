package com.kevdeto.tiendalibre.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import com.kevdeto.tiendalibre.domain.entity.CategoryEntity;

public class CategorySpecification {
	public static Specification<CategoryEntity> hasName(String name) {
		return (root, query, cb) -> name == null ? null
				: cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
	}

	public static Specification<CategoryEntity> hasSlug(String slug) {
		return (root, query, cb) -> slug == null ? null 
				: cb.equal(cb.lower(root.get("slug")), slug.toLowerCase());
	}
}
