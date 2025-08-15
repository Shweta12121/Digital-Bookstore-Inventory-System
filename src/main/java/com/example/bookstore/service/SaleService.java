package com.example.bookstore.service;

import com.example.bookstore.dto.SaleRequestDto;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Sale;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Business logic for recording sales and producing summaries.
 */
@Service
@RequiredArgsConstructor
public class SaleService {

	private final SaleRepository saleRepository;
	private final BookRepository bookRepository;

	@Transactional
	public Sale recordSale(SaleRequestDto request) {
		Book book = bookRepository.findById(request.getBookId())
				.orElseThrow(() -> new IllegalArgumentException("Book not found"));

		int quantity = request.getQuantity();
		if (book.getStockQuantity() < quantity) {
			throw new IllegalStateException("Insufficient stock");
		}

		BigDecimal total = book.getPrice().multiply(BigDecimal.valueOf(quantity));

		book.setStockQuantity(book.getStockQuantity() - quantity);
		bookRepository.save(book);

		Sale sale = Sale.builder()
				.book(book)
				.quantity(quantity)
				.totalAmount(total)
				.saleDateTime(LocalDateTime.now())
				.build();
		return saleRepository.save(sale);
	}

	public List<Sale> listAll() {
		return saleRepository.findAll();
	}

	public List<Sale> findByDate(LocalDate date) {
		LocalDateTime start = date.atStartOfDay();
		LocalDateTime end = date.atTime(LocalTime.MAX);
		return saleRepository.findBySaleDateTimeBetween(start, end);
	}

	public BigDecimal totalByDate(LocalDate date) {
		LocalDateTime start = date.atStartOfDay();
		LocalDateTime end = date.atTime(LocalTime.MAX);
		return saleRepository.sumTotalBetween(start, end);
	}

	public List<Sale> findByYearMonth(int year, int month) {
		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
		return saleRepository.findBySaleDateTimeBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
	}

	public BigDecimal totalByYearMonth(int year, int month) {
		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
		return saleRepository.sumTotalBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
	}

	public BigDecimal totalAll() {
		return saleRepository.sumTotalAll();
	}
}


