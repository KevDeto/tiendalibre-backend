package com.kevdeto.tiendalibre.domain.entity;

import java.time.LocalDateTime;
import java.util.Set;

import com.kevdeto.tiendalibre.auth.domain.entity.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PurchaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime purchaseDate;

	/*relacion purchases - products y purchases - user*/
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	@ManyToMany
	@JoinTable(
			name = "purchase_product",
			joinColumns = @JoinColumn(name = "purchase_id"),
			inverseJoinColumns = @JoinColumn(name = "product_id"))
	private Set<ProductEntity> products;
}
