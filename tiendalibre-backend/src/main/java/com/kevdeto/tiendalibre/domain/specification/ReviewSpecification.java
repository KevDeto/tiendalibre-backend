package com.kevdeto.tiendalibre.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import com.kevdeto.tiendalibre.domain.entity.ReviewEntity;

public class ReviewSpecification {
	public static Specification<ReviewEntity> hasUserId(Long userId) {
		return (root, query, cb) -> userId == null ? null 
				: cb.equal(root.get("user").get("id"), userId);
	}

	public static Specification<ReviewEntity> hasRating(Integer rating) {
		return (root, query, cb) -> rating == null ? null 
				: cb.equal(root.get("rating"), rating);
	}
}
