package com.example.bookstore.controller;

import com.example.bookstore.entity.Sale;
import com.example.bookstore.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

/**
 * Provides daily and monthly sales reports and CSV export.
 */
@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

	private final SaleService saleService;

	@GetMapping
	public String reports(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer month,
			Model model) {

		LocalDate effectiveDate = date != null ? date : LocalDate.now();
		List<Sale> daily = saleService.findByDate(effectiveDate);
		BigDecimal dailyTotal = saleService.totalByDate(effectiveDate);

		LocalDate now = LocalDate.now();
		int effectiveYear = year != null ? year : now.getYear();
		int effectiveMonth = month != null ? month : now.getMonthValue();
		List<Sale> monthly = saleService.findByYearMonth(effectiveYear, effectiveMonth);
		BigDecimal monthlyTotal = saleService.totalByYearMonth(effectiveYear, effectiveMonth);
		BigDecimal totalAll = saleService.totalAll();

		model.addAttribute("date", effectiveDate);
		model.addAttribute("year", effectiveYear);
		model.addAttribute("month", effectiveMonth);
		model.addAttribute("daily", daily);
		model.addAttribute("dailyTotal", dailyTotal);
		model.addAttribute("monthly", monthly);
		model.addAttribute("monthlyTotal", monthlyTotal);
		model.addAttribute("totalAll", totalAll);
		return "reports/index";
	}

	@GetMapping("/export")
	public ResponseEntity<byte[]> exportCsv(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer month) {

		LocalDate effectiveDate = date != null ? date : LocalDate.now();
		List<Sale> daily = saleService.findByDate(effectiveDate);

		LocalDate now = LocalDate.now();
		int effectiveYear = year != null ? year : now.getYear();
		int effectiveMonth = month != null ? month : now.getMonthValue();
		List<Sale> monthly = saleService.findByYearMonth(effectiveYear, effectiveMonth);

		StringJoiner joiner = new StringJoiner("\n");
		joiner.add("Type,Date,Book,Quantity,Total");
		daily.forEach(s -> joiner.add(String.format("Daily,%s,%s,%d,%s",
				effectiveDate,
				s.getBook().getTitle().replace(",", " "),
				s.getQuantity(),
				formatMoney(s.getTotalAmount()))));
		monthly.forEach(s -> joiner.add(String.format("Monthly,%d-%02d,%s,%d,%s",
				effectiveYear, effectiveMonth,
				s.getBook().getTitle().replace(",", " "),
				s.getQuantity(),
				formatMoney(s.getTotalAmount()))));

		byte[] bytes = joiner.toString().getBytes(StandardCharsets.UTF_8);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv")
				.contentType(new MediaType("text", "csv"))
				.body(bytes);
	}

	private String formatMoney(BigDecimal amount) {
		if (amount == null) {
			return "0.00";
		}
		return amount.setScale(2, RoundingMode.HALF_UP).toPlainString();
	}
}


