package com.lluanps.gestaoeventos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lluanps.gestaoeventos.evento.model.Evento;
import com.lluanps.gestaoeventos.produto.model.Produto;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    /**
     * Retorna todos os eventos carregando as coleções relacionadas (receitas, despesas, produtos)
     * para evitar LazyInitializationException quando mapeados para DTOs.
     */
    @Query("select distinct e from Evento e " +
           "left join fetch e.receitas r " +
           "left join fetch e.despesas d " +
           "left join fetch e.produtos p")
    List<Evento> findAllWithRelations();

    /**
     * Retorna um evento por id carregando as coleções relacionadas.
     */
    @Query("select e from Evento e " +
           "left join fetch e.receitas r " +
           "left join fetch e.despesas d " +
           "left join fetch e.produtos p " +
           "where e.id = :id")
    Optional<Evento> findByIdWithRelations(@Param("id") Integer id);

    /**
     * Mantive o método existente para recuperar produtos por evento.
     */
    @Query("SELECT p FROM Produto p WHERE p.evento.id = :eventoId")
    List<Produto> getProdutosByEventoId(@Param("eventoId") Integer eventoId);
}