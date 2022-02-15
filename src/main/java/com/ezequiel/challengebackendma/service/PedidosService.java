package com.ezequiel.challengebackendma.service;

import com.ezequiel.challengebackendma.dto.request.DetalleRequest;
import com.ezequiel.challengebackendma.dto.request.PedidoRequest;
import com.ezequiel.challengebackendma.entity.PedidoCabecera;
import com.ezequiel.challengebackendma.entity.PedidoDetalle;
import com.ezequiel.challengebackendma.entity.Producto;
import com.ezequiel.challengebackendma.repository.IPedidoCabeceraRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PedidosService {

  private static final Double DESCUENTO_COMPRA = 0.7;
  private static final Integer TOTAL_PRODUCTOS_DESCUENTO = 3;
  @Autowired
  private IPedidoCabeceraRepository pedidoCabeceraRepository;

  @Autowired
  private ProductoService productoService;


  public List<PedidoCabecera> findAll() {
    return pedidoCabeceraRepository.findAll();
  }
  public List<PedidoCabecera> findByFechaAlta(String fecha) {
    return pedidoCabeceraRepository.findByFechaAlta(fecha);
  }

  @Transactional
  public PedidoCabecera crearPedido(PedidoRequest request) {
    PedidoCabecera pedidoCabecera = new PedidoCabecera(request.getDireccion(), request.getEmail(),
        request.getTelefono(), request.getHorario());

    List<DetalleRequest> productoList = request.getDetalles();
    //Cargo los detalles
    pedidoCabecera = crearListaProductos(pedidoCabecera, productoList);

    Double montoTotal = pedidoCabecera.calcularMontoTotal();

    //Cuento los productos para ver si aplica descuento
    Boolean descuento = false;
    Integer contadorProductos = contadorProductos(pedidoCabecera.getListaDetalles());

    if (contadorProductos > TOTAL_PRODUCTOS_DESCUENTO) {
      montoTotal = montoTotal * DESCUENTO_COMPRA;
      descuento = true;
    }

    pedidoCabecera.setMontoTotal(montoTotal);
    pedidoCabecera.setAplicoDescuento(descuento);
    pedidoCabeceraRepository.save(pedidoCabecera);

    return pedidoCabecera;

  }

  private PedidoCabecera crearListaProductos(PedidoCabecera pedidoCabecera,
      List<DetalleRequest> productoList) {
    //Obtengo la lista de productos
    for (int i = 0; i < productoList.size(); i++) {
      //Busco el producto
      Producto producto = productoService.findByUUID(productoList.get(i).getProducto());
      // Armo el PedidoDetalle
      PedidoDetalle pedidoDetalle = PedidoDetalle.builder()
          .producto(producto)
          .cantidad(productoList.get(i).getCantidad()).build();
      //Calculo el importe del deltalle ( precioProducto * cantidad)
      pedidoDetalle.setImporte(pedidoDetalle.calcularImporte());
      // Lo agrego a la lista de detalles
      pedidoCabecera.addPedidoDetalle(pedidoDetalle);
    }
    return pedidoCabecera;

  }

  private Integer contadorProductos(List<PedidoDetalle> pedidoDetalleList) {
    Integer contadorProductos = 0;
    for (int i = 0; i < pedidoDetalleList.size(); i++) {
      contadorProductos += pedidoDetalleList.get(i).getCantidad();
    }
    return contadorProductos;
  }


}
