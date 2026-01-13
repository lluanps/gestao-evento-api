package com.lluanps.gestaoeventos.despesa.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lluanps.gestaoeventos.despesa.dto.DespesaDTO;
import com.lluanps.gestaoeventos.despesa.model.Despesa;
import com.lluanps.gestaoeventos.despesa.service.DespesaService;

@RestController
@RequestMapping("despesa")
public class DespesaController {

	@Autowired
	private DespesaService service;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Despesa cadastrarDespesa(@RequestBody DespesaDTO dto) {
		return service.cadastrarDespesa(dto);
	}
	
	@GetMapping("/total-despesa/{eventoId}")
	public BigDecimal getTotalDespesaPorEventoId(@PathVariable Integer eventoId) {
		return service.getTotalDespesaPorEventoId(eventoId);
	}
}
