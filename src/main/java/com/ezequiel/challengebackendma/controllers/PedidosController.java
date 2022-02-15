package com.ezequiel.challengebackendma.controllers;

import com.ezequiel.challengebackendma.dto.request.PedidoRequest;
import com.ezequiel.challengebackendma.entity.PedidoCabecera;
import com.ezequiel.challengebackendma.service.PedidosService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Pedidos : Clase encargada de manejar los endpoints que llegan a /pedidos , realiza los
 * pedidos y los lista.
 */

@RestController
@RequestMapping("/pedidos")
public class PedidosController {

  @Autowired
  private PedidosService pedidosService;

  /**
   * Lista todos los pedidos creados que existen en la base de datos. Tambien se pueden filtrar por
   * fecha
   *
   * @param fecha Paramentro opcional para filtrar por Fecha los pedidos.
   * @return Listado de pedidos.
   */
  @ApiOperation(value = "Listar todos los pedidos")
  @GetMapping(produces = "application/json")
  public ResponseEntity<List<PedidoCabecera>> getAll(
      @RequestParam(value = "fecha", required = false) String fecha) {
    if (fecha != null) {
      return ResponseEntity.ok(pedidosService.findByFechaAlta(fecha));
    }
    return ResponseEntity.ok(pedidosService.findAll());
  }


  /**
   * Crea un pedido y lo inserta en la base de datos.
   *
   * @param pedidoRequest Informacion del pedido
   * @return Devuelve <code>200</code> si el pedido es creado correctamente; Tambien puede lanzar
   * excepciones en caso de que el request no sea valido, o no see encuentren los productos.
   */
  @ApiOperation(value = "Crear un pedido")
  @PostMapping(produces = "application/json")
  public ResponseEntity<PedidoCabecera> crearPedido(
      @Valid @RequestBody PedidoRequest pedidoRequest) {
    return ResponseEntity.ok(pedidosService.crearPedido(pedidoRequest));
  }

}
