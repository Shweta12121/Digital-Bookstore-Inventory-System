package com.example.bookstore.repository;

import com.example.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository for CRUD and search operations on books.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByTitleContainingIgnoreCase(String title);

	List<Book> findByAuthorContainingIgnoreCase(String author);

	List<Book> findByCategoryContainingIgnoreCase(String category);

	@Query("select b from Book b where lower(b.title) like lower(concat('%', :keyword, '%')) " +
			"or lower(b.author) like lower(concat('%', :keyword, '%')) " +
			"or lower(b.category) like lower(concat('%', :keyword, '%'))")
	List<Book> search(String keyword);

	List<Book> findByStockQuantityLessThan(int threshold);
}


