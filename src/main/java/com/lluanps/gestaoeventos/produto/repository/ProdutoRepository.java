package com.lluanps.gestaoeventos.produto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lluanps.gestaoeventos.produto.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	
}
