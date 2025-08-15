package com.example.bookstore.controller;

import com.example.bookstore.entity.Book;
import com.example.bookstore.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles book CRUD and search.
 */
@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	@GetMapping
	public String list(@RequestParam(value = "q", required = false) String keyword, Model model) {
		List<Book> books = bookService.search(keyword);
		model.addAttribute("books", books);
		model.addAttribute("keyword", keyword);
		return "books/list";
	}

	@GetMapping("/new")
	public String createForm(Model model) {
		model.addAttribute("book", new Book());
		return "books/form";
	}

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		Book book = bookService.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));
		model.addAttribute("book", book);
		return "books/form";
	}

	@PostMapping
	public String save(@Valid @ModelAttribute("book") Book book, BindingResult result) {
		if (result.hasErrors()) {
			return "books/form";
		}
		bookService.save(book);
		return "redirect:/books";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		bookService.deleteById(id);
		return "redirect:/books";
	}
}


