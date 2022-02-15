package com.ezequiel.challengebackendma.controllers;

import com.ezequiel.challengebackendma.dto.request.ProductoRequest;
import com.ezequiel.challengebackendma.entity.Producto;
import com.ezequiel.challengebackendma.service.ProductoService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controladora del CRUD de productos
 */
@RestController
@RequestMapping("/productos")
public class ProductoController {

  @Autowired
  private ProductoService productoService;

  /**
   * Listar productos
   * @return Lista de productos
   */
  @ApiOperation(value = "Listar todos los productos")
  @GetMapping(produces = "application/json")
  public ResponseEntity<List<Producto>> getAll() {
    return ResponseEntity.ok(productoService.findAll());
  }

  /**
   * Consultar un producto por UUID
   * @param uuid UUID del producto
   * @return Producto buscado o NotFoundException en caso de no haberlo encontrado.
   */
  @ApiOperation(value = "Consultar un producto por UUID")
  @GetMapping(value = "/{uuid}", produces = "application/json")
  public ResponseEntity<Producto> consultarProducto(
      @PathVariable() String uuid) {
    return ResponseEntity.ok(productoService.findByUUID(uuid));
  }

  /**
   * Alta de un producto
   * @param productoRequest Request del producto a crear
   * @return Retorna el producto creado, y en caso de que ya exista NotFoundException.
   */
  @ApiOperation(value = "Crear producto")
  @PostMapping(produces = "application/json")
  public ResponseEntity<Producto> crearProducto(
      @Valid @RequestBody ProductoRequest productoRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(productoService.crearProducto(productoRequest));
  }

  /**
   * Elimina un producto
   * @param uuid UUID del producto a eliminar.
   * Devuelve una NotFoundException en caso de no encontrar el producto a eliminar.
   */
  @ApiOperation(value = "Eliminar producto")
  @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void eliminarProducto(
      @PathVariable String uuid) {
    productoService.borrarProducto(uuid);
  }

  /**
   * Modifica un producto
   * @param uuid UUID del producto a modificar.
   * @param differences JSON con los campos a modificar.
   */
  @ApiOperation(value = "Modificar producto")
  @PutMapping(value = "/{uuid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void modificarProducto(@PathVariable String uuid,
      @RequestBody Map<String, Object> differences) {
    productoService.modificarProducto(uuid, differences);
  }
}