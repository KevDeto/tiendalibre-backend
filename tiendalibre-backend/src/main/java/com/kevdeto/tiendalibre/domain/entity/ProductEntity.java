package com.kevdeto.tiendalibre.domain.entity;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "product")
public class ProductEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private BigDecimal price;
	private String brand;
	private String description;
	private String mainImageUrl;
	@Embedded
	private Dimension dimension;
	private Integer discountPercentage;
	private Integer stock;
	private Integer weight;
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private Map<String, String> specs;
	@ElementCollection
	@CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
	@Column(name = "tag")
	private Set<String> tags;
	private boolean active;
	
	/*relaciones entre entidades*/
	@ManyToMany(cascade = {CascadeType.MERGE})/*debo arreglar bug: detalles en exceptionhandler*/
	@JoinTable(
			name = "product_category",
			joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	private Set<CategoryEntity> categories;
	
	@OneToMany(mappedBy = "product",
			cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
	private Set<ImageEntity> images;
	
	@OneToMany(mappedBy = "product",
			cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<ReviewEntity> reviews;
}
