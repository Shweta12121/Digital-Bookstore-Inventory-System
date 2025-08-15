package com.example.bookstore.controller;

import com.example.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Provides the home page with quick low-stock overview.
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

	private final BookService bookService;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("lowStockBooks", bookService.findLowStock(5));
		return "home";
	}
}


