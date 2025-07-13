package com.kevdeto.tiendalibre.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.kevdeto.tiendalibre.auth.domain.entity.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "purchase")
public class PurchaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime purchaseDate;

	/*relacion purchases - products y purchases - user*/
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
    @OneToMany(mappedBy = "purchase")
    private List<PurchaseItemEntity> items;
}
