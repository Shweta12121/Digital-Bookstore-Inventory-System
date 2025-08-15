package com.example.bookstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Sale entity capturing sales transactions.
 */
@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;

	@NotNull
	@Min(1)
	@Column(nullable = false)
	private Integer quantity;

	@NotNull
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal totalAmount;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime saleDateTime;
}


