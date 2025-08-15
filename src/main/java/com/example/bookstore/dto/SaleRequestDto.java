package com.example.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO used to capture sale inputs from the UI without exposing the entity directly.
 */
@Data
public class SaleRequestDto {

	@NotNull
	private Long bookId;

	@NotNull
	@Min(1)
	private Integer quantity;
}


