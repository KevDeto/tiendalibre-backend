package com.kevdeto.tiendalibre.domain.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.kevdeto.tiendalibre.domain.entity.PurchaseEntity;

public class PurchaseSpecification {
	public static Specification<PurchaseEntity> hasUserId(Long userId) {
		return (root, query, cb) -> userId == null ? null 
				: cb.equal(root.get("user").get("id"), userId);
	}

	public static Specification<PurchaseEntity> purchaseAfter(LocalDateTime date) {
		return (root, query, cb) -> date == null ? null 
				: cb.greaterThanOrEqualTo(root.get("purchaseDate"), date);
	}

	public static Specification<PurchaseEntity> purchaseBefore(LocalDateTime date) {
		return (root, query, cb) -> date == null ? null 
				: cb.lessThanOrEqualTo(root.get("purchaseDate"), date);
	}
}
