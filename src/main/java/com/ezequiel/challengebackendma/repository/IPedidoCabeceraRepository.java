package com.ezequiel.challengebackendma.repository;

import com.ezequiel.challengebackendma.entity.PedidoCabecera;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPedidoCabeceraRepository extends CrudRepository<PedidoCabecera, Long> {

  List<PedidoCabecera> findAll();

  @Query("select p from PedidoCabecera p join fetch p.listaDetalles l join fetch l.producto where p.id=?1")
  PedidoCabecera fetchByIdWithPedidoDetalleWithProducto(Long id);

  List<PedidoCabecera> findByFechaAlta(String fechaAlta);

}
