package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<ReportDTO> getReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate parsedMinDate;
		LocalDate parsedMaxDate;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			if (minDate.equals("")) {
				parsedMinDate = today.minusYears(1L);
			} else{
			parsedMinDate = LocalDate.parse(minDate, formatter);
			}			
			
			if (maxDate.equals("")) {
				parsedMaxDate = today;
			} else {
			parsedMaxDate = LocalDate.parse(maxDate, formatter);
			}
		
		Page<Sale> report = repository.getReport(parsedMinDate, parsedMaxDate, name, pageable);
		return report.map(x -> new ReportDTO(x));
	}

	public Page<SummaryDTO> getSummary(String minDate, String maxDate, Pageable pageble) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate parsedMinDate;
		LocalDate parsedMaxDate;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			if (minDate.equals("")) {
				parsedMinDate = today.minusYears(1L);
			} else{
			parsedMinDate = LocalDate.parse(minDate, formatter);
			}			
			
			if (maxDate.equals("")) {
				parsedMaxDate = today;
			} else {
			parsedMaxDate = LocalDate.parse(maxDate, formatter);
			}
		
		Page<SummaryDTO> summary = repository.getSummary(parsedMinDate, parsedMaxDate, pageble);
		return summary;
	}

}
