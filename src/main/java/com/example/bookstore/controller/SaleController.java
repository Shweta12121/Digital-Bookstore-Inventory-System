package com.example.bookstore.controller;

import com.example.bookstore.dto.SaleRequestDto;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Sale;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Handles recording and viewing sales.
 */
@Controller
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

	private final SaleService saleService;
	private final BookService bookService;

	@GetMapping
	public String list(Model model) {
		List<Sale> sales = saleService.listAll();
		model.addAttribute("sales", sales);
		return "sales/list";
	}

	@GetMapping("/new")
	public String newSaleForm(Model model) {
		model.addAttribute("saleRequest", new SaleRequestDto());
		model.addAttribute("books", bookService.listAll());
		return "sales/form";
	}

	@PostMapping
	public String recordSale(@Valid @ModelAttribute("saleRequest") SaleRequestDto saleRequestDto,
							 BindingResult result,
							 Model model) {
		if (result.hasErrors()) {
			model.addAttribute("books", bookService.listAll());
			return "sales/form";
		}
		try {
			saleService.recordSale(saleRequestDto);
			return "redirect:/sales";
		} catch (IllegalArgumentException | IllegalStateException ex) {
			model.addAttribute("error", ex.getMessage());
			model.addAttribute("books", bookService.listAll());
			return "sales/form";
		}
	}
}


