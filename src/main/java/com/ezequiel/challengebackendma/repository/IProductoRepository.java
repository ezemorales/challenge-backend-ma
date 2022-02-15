package com.ezequiel.challengebackendma.repository;

import com.ezequiel.challengebackendma.entity.Producto;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends CrudRepository<Producto, String> {

  List<Producto> findAll();
}
