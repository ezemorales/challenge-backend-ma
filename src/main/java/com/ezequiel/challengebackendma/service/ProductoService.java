package com.ezequiel.challengebackendma.service;

import com.ezequiel.challengebackendma.dto.request.ProductoRequest;
import com.ezequiel.challengebackendma.entity.Producto;
import com.ezequiel.challengebackendma.exception.BadRequestException;
import com.ezequiel.challengebackendma.exception.NotFoundException;
import com.ezequiel.challengebackendma.repository.IProductoRepository;
import com.ezequiel.challengebackendma.utils.PatchHelper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.json.Json;
import javax.json.JsonMergePatch;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class ProductoService {

  private final PatchHelper patchHelper;
  @Autowired
  private final IProductoRepository productoRepository;

  public List<Producto> findAll() {
    return productoRepository.findAll();
  }

  public Producto findByUUID(String uuid) {
    return productoRepository.findById(uuid)
        .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
  }

  public Producto crearProducto(ProductoRequest request) {

    Optional<Producto> optionalProducto = productoRepository.findById(request.getId());
    if (optionalProducto.isPresent()) {
      throw new BadRequestException(
          "El producto con UUID " + request.getId() + " ya existe en la base de datos");
    }
    return productoRepository.save(Producto.builder()
        .id(request.getId())
        .nombre(request.getNombre())
        .descripcionCorta(request.getDescripcionCorta())
        .descripcionLarga(request.getDescripcionLarga())
        .precioUnitario(Double.parseDouble(request.getPrecioUnitario()))
        .build());

  }

  @Transactional
  public void modificarProducto(String uuid, Map<String, Object> differences) {
    //Si no lo encuentra lanza la ex del metodo #FindByUUID
    Producto producto = findByUUID(uuid);
    //Chequeamos las diferencias y actualizamos los campos que se ingresaron en el request
    JsonMergePatch mergePatch = Json
        .createMergePatch(Json.createObjectBuilder(differences).build());
    Producto productoUpdated = patchHelper.mergePatch(mergePatch, producto, Producto.class);
    if (productoUpdated.getNombre() != null) {
      productoUpdated.setNombre(productoUpdated.getNombre());
    }
    if (productoUpdated.getDescripcionCorta() != null) {
      productoUpdated.setDescripcionCorta(productoUpdated.getDescripcionCorta());
    }
    if (productoUpdated.getDescripcionLarga() != null) {
      productoUpdated.setDescripcionLarga(productoUpdated.getDescripcionLarga());
    }
    if (productoUpdated.getPrecioUnitario() != null) {
      productoUpdated.setPrecioUnitario(productoUpdated.getPrecioUnitario());
    }
    productoRepository.save(productoUpdated);
  }

  public void borrarProducto(String uuid) {
    productoRepository.delete(findByUUID(uuid));
  }
}
