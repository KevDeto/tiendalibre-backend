package com.kevdeto.tiendalibre.auth.domain.entity;

import java.util.Set;

import com.kevdeto.tiendalibre.domain.entity.PurchaseEntity;
import com.kevdeto.tiendalibre.domain.entity.ReviewEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String username;
	private String password;
	
	/*relacion entre user - reviews y user - purchases*/
	@OneToMany(mappedBy = "user")
	private Set<ReviewEntity> reviews;
	
	@OneToMany(mappedBy = "user")
	private Set<PurchaseEntity> purchases;
}
