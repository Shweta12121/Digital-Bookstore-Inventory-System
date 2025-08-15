package com.example.bookstore.service;

import com.example.bookstore.entity.Book;
import com.example.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Business logic for managing books.
 */
@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;

	public List<Book> listAll() {
		return bookRepository.findAll();
	}

	public Optional<Book> findById(Long id) {
		return bookRepository.findById(id);
	}

	@Transactional
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	@Transactional
	public void deleteById(Long id) {
		bookRepository.deleteById(id);
	}

	public List<Book> search(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			return listAll();
		}
		return bookRepository.search(keyword);
	}

	public List<Book> findLowStock(int threshold) {
		return bookRepository.findByStockQuantityLessThan(threshold);
	}
}


