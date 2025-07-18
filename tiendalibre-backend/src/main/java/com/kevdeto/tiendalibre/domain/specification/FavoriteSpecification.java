package com.kevdeto.tiendalibre.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import com.kevdeto.tiendalibre.domain.entity.FavoriteEntity;

public class FavoriteSpecification {
	public static Specification<FavoriteEntity> hasUserId(Long userId) {
		return (root, query, cb) -> userId == null ? null 
				: cb.equal(root.get("user").get("id"), userId);
	}

	public static Specification<FavoriteEntity> hasProductId(Long productId) {
		return (root, query, cb) -> productId == null ? null 
				: cb.equal(root.get("product").get("id"), productId);
	}
}
